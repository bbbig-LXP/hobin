package com.lxp.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {

    private static final String URL = requireEnv("LXP_DB_URL");
    private static final String USER = requireEnv("LXP_DB_USER");
    private static final String PASSWORD = requireEnv("LXP_DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static String requireEnv(String key) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("환경 변수가 누락됨" + key);
        }
        return value;
    }

}