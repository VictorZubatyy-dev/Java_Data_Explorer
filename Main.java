package com.dataexplorer;

import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1443/dbo","VictorZubatyy","Chelseano.1");
        Statement statement = connection.createStatement();
    }
}


