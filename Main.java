package com.dataexplorer;

import java.io.File;
import java.sql.*;
import java.util.Properties;

public class Main extends FileProcessor{
    public Main(String file){
        super(file);
    }

    public static void main(String[] args) throws SQLException {
//        start connection
        String url, user, password;
        Connection conn = new Connection();
        FileProcessor file = new FileProcessor("csv/Attractions.csv");
    }
}


