package com.vrc.logline;

import com.google.gson.Gson;
import com.vrc.logline.config.SqlConfig;
import org.junit.Test;

public class ConfigTest {

    @Test
    public void shouldGenerateConfigJson(){
        Gson gson = new Gson();
        SqlConfig sqlConfig = new SqlConfig("a", "b", "c");
        String s = gson.toJson(sqlConfig);
        System.out.println(s);
        System.out.println(gson.fromJson(s, SqlConfig.class).getThreshold());



    }
}
