package com.dataexplorer;

import javax.swing.plaf.nimbus.State;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public Main(){

    }

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        String url = "jdbc:mysql://localhost/database";
        String user = "root";
        String password = "Chelseano.1";

//
        EstablishConnection conn = new EstablishConnection(url, user, password);

//        using getters from EstablishConnection class to then input them into the connect function and return a connection (object) to the database
        url = conn.get_Url();
        user = conn.get_User();
        password = conn.get_Password();

//        setters can be used within the EstablishConnection class to set the database, user and password to different values
//        conn.set_Url("jdbc:mysql://localhost/database/hi");
//        System.out.println(conn.get_Url());

        Connection est_conn = conn.Connect(url, user, password);

//        file object instantiated
        File file = new File("Attractions.csv");
        FileProcessor file_name = new FileProcessor(file);
        file_name.ReadFile(file);
        String [][] column_row_values = file_name.InsertData();

        String table_name = "";

//        sql object instantiated
        SQL sql = new SQL(column_row_values, est_conn, table_name);
        sql.set_Table_Name("Attractions");

//        Used to create table
//        sql.CreateTable();
//        Used to drop table
//        sql.DropTable();

//        Update table
        sql.UpdateTable();


    }
}


