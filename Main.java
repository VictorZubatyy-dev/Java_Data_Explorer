package com.dataexplorer;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static SQL sql;

    //    null constructor
    public Main() {

    }

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        String url = "jdbc:mysql://localhost/database";
        String user = "root";
        String password = "Chelseano.1";

        EstablishConnection conn = new EstablishConnection(url, user, password);

//      using getters from EstablishConnection class to then input them into the connect function and return a connection (object) to the database
        url = conn.get_Url();
        user = conn.get_User();
        password = conn.get_Password();

//      setters can be used within the EstablishConnection class to set the database, user and password to different values
//      conn.set_Url("jdbc:mysql://localhost/database/hi");
//      System.out.println(conn.get_Url());

        Connection est_conn = conn.Connection(url, user, password);

        String table_name = "";

        Scanner myScanner = new Scanner(System.in);
        System.out.println("Do you want to insert a CSV file?");
        String yes_or_no = myScanner.nextLine();

        if (Objects.equals(yes_or_no, "Yes")) {
            System.out.println("Please enter your CSV file name:");
            String csv_file_name = myScanner.nextLine();
            File file = new File(csv_file_name);
            FileProcessor file_name = new FileProcessor(file);
            file_name.ReadFile(file);
            String[][] column_row_values = file_name.InsertData();

            sql = new SQL(column_row_values, est_conn, table_name);

            System.out.println("Do you want to create or delete a table, or view?");
            String create_or_drop = myScanner.nextLine();

            if (Objects.equals(create_or_drop, "create") | Objects.equals(create_or_drop, "Create")) {
                System.out.println("Enter your table name:");
                table_name = myScanner.nextLine();

                if (sql.Show_Tables(table_name) == false) {
                    sql.set_Table_Name(table_name);
                    sql.CreateTable();
                } else {
                    System.out.println("Table already exists.");
                    System.out.println("Exiting program.");
                    System.exit(0);
                }
            } else if (Objects.equals(create_or_drop, "delete") | Objects.equals(create_or_drop, "Delete")) {
                System.out.println("Enter your table name:");
                table_name = myScanner.nextLine();

                if (sql.Show_Tables(table_name) == false) {
                    System.out.println("Table does not exist.");
                } else {
                    sql.set_Table_Name(table_name);
                    sql.DropTable();
                }
            }
            else if (Objects.equals(create_or_drop, "view") | Objects.equals(create_or_drop, "View")){
                sql.Country_Query();
                String[] counties = sql.Combo_Values();
                GUI gui = new GUI(counties, sql);
                gui.CreateGui("Attractions in Ireland");
            }
            else {
                System.out.println("You have entered the incorrect keyword.");
                System.out.println("Exiting program.");
                System.exit(0);
            }
        }

        else if (Objects.equals(yes_or_no, "No")) {
            System.exit(0);
        }

//        String[][] update_column_row_values = sql.get_column_row_values();
    }
}


