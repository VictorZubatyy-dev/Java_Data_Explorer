package com.dataexplorer;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    public Connection(String url, String user, String password) throws SQLException {
        Connection connection = DriverManager.getConnection();

    }

}
