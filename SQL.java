package com.dataexplorer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQL {

    public SQL(ArrayList<String> data, Connection est_conn){
        get_Data(data);
        get_Connection(est_conn);
    }

    private Connection get_Connection(Connection est_conn) {
        return est_conn;
    }


    public void InsertData(ArrayList<String> data, Connection est_conn) throws SQLException {
        Statement statement = est_conn.createStatement();
//        statement.
    }

    private ArrayList<String> get_Data(ArrayList<String> data) {
        return data;
    }
}
