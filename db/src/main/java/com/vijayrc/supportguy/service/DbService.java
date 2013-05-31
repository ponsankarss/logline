package com.vijayrc.supportguy.service;

import com.vijayrc.supportguy.domain.*;
import com.vijayrc.supportguy.repository.AllQueries;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.sql.*;

@Service
@Log4j
public class DbService {

    private AllQueries allQueries;
    private MyClassLoader loader;

    @Autowired
    public DbService(AllQueries allQueries) throws Exception {
        URL urls[] = {};
        this.allQueries = allQueries;
        this.loader = new MyClassLoader(urls);
    }

    public QueryHit process(String name, String environment) throws Exception {
        Connection connection = null;
        try {
            Query query = allQueries.findByName(name, environment);
            log.info("execute: " + query.getSql());

            Database database = query.getDatabase();
            connection = connectTo(database);
            Statement statement = connection.createStatement();
            schemaSetup(database, statement);
            ResultSet resultSet = statement.executeQuery(query.getSql());

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int c = 1; c <= columnCount; c++) {
                log.info(metaData.getColumnClassName(c) + "|"
                        + metaData.getColumnLabel(c) + "|"
                        + metaData.getColumnName(c) + "|"
                        + metaData.getColumnTypeName(c));
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
        } finally {
            if (connection != null) connection.close();
        }
        return null;
    }

    private void schemaSetup(Database database, Statement statement) throws SQLException {
        String schemaQuery = database.schemaQuery();
        if(StringUtils.isNotBlank(schemaQuery))
            statement.execute(schemaQuery);
    }

    private Connection connectTo(Database database) throws Exception {
        Driver driver = (Driver) Class.forName(database.getDriver(), true, loader).newInstance();
        DriverManager.registerDriver(new MyDriver(driver));
        Connection connection = DriverManager.getConnection(database.getUrl(), database.getUser(), database.getPassword());
        return connection;
    }

}

