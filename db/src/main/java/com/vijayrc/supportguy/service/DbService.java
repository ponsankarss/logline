package com.vijayrc.supportguy.service;

import com.vijayrc.supportguy.domain.*;
import com.vijayrc.supportguy.repository.AllQueries;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

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
            Database database = query.getDatabase();
            connection = connectTo(database);

            log.info("executing: "+query.getSql());
            ResultSet resultSet = connection.createStatement().executeQuery(query.getSql());

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int c = 0; c < columnCount; c++) {
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

    private Connection connectTo(Database database) throws Exception {
        Driver driver = (Driver) Class.forName(database.getDriver(), true, loader).newInstance();
        DriverManager.registerDriver(new MyDriver(driver));
        return DriverManager.getConnection(database.getUrl(), database.getUser(), database.getPassword());
    }

}

