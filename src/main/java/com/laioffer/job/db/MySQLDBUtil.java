package com.laioffer.job.db;

public class MySQLDBUtil {
    private static final String INSTANCE = "[My db instance endpoint]";
    private static final String PORT_NUM = "3306";
    public static final String DB_NAME = "[db name]";
    private static final String USERNAME = "[username]";
    private static final String PASSWORD = "[password]";
    public static final String URL = "jdbc:mysql://"
            + INSTANCE + ":" + PORT_NUM + "/" + DB_NAME
            + "?user=" + USERNAME + "&password=" + PASSWORD
            + "&autoReconnect=true&serverTimezone=UTC";
}
