package com.dataexplorer;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;

public class Main {

    public Main(){

    }

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        String url = "jdbc:mysql://localhost:3306";
        String user = "root";
        String password = "Chelseano.1";


        EstablishConnection conn = new EstablishConnection(url, user, password);

//        using getters to get the
        url = conn.get_Url();
        user = conn.get_User();
        password = conn.get_Password();

        Connection est_conn = conn.Connect(url, user, password);

        File file = new File("Attractions.csv");
        FileProcessor file_name = new FileProcessor(file);
        file_name.set_FileName("Attractions.csv");
        file_name.ReadFile(file);
//        SQL sql_handler = new SQL(data, est_conn);


    }
}


