package com.acc.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getDBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/smart_attendance",
                "root",
                "#Hindavi_07"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
