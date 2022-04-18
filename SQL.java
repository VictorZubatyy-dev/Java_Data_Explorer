package com.dataexplorer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {
//    private variables used to manipulate database
    private String table_name;
    private String[][] column_row_values;
    private Connection est_conn;

//    intilisation of class constructor, data, connection and table name are passed into the constructor to set and get values
    public SQL(String[][] column_row_values, Connection est_conn, String table_name) {
        get_column_row_values();
        get_Connection();
        get_Table_Name();
        set_column_row_value(column_row_values);
        set_Connection(est_conn);
        set_Table_Name(table_name);

    }

    private void set_Connection(Connection est_conn) {
        this.est_conn = est_conn;
    }

    protected void set_Table_Name(String table_name) {
        this.table_name = table_name;
    }

    protected void set_column_row_value(String[][] column_row_values) {
        this.column_row_values = column_row_values;
    }

    protected String get_Table_Name() {
        return table_name;
    }

    protected String[][] get_column_row_values() {
        return column_row_values;
    }

    protected Connection get_Connection() {
        return est_conn;
    }

//CRUD
    public void CreateTable() throws SQLException {

        String createTable = "create table " + table_name + " (";
        System.out.println(createTable);

//        CREATE TABLE Persons (
//    PersonID int,
//    LastName varchar(255),
//    FirstName varchar(255),
//    Address varchar(255),
//    City varchar(255)
//);

            for (int v = 0; v < column_row_values[0].length; v++){
                if (v == column_row_values[0].length - 1) {
                    createTable += column_row_values[0][v] + " varchar(255)";
                }

                else{
                    createTable += column_row_values[0][v] + " varchar(255),";
                }

//                System.out.println(createTable);

            }

            createTable += ")";

//        createTable = createTable + "(" + column_row_values[0][0] + " int(4), " +
//                column_row_values[0][1] + " varchar(10), " + column_row_values[0][2] + " varchar(3)," +
//                column_row_values[0][3] + " varchar(5), " + column_row_values[0][4] + " int(10))";
//        System.out.println(createTable);

        try (Statement stmt = est_conn.createStatement()) {
            stmt.executeUpdate(createTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateTable() throws SQLException {
        Statement stmt = est_conn.createStatement();
        System.out.println(column_row_values.length);

        String insert_into = "insert into " + table_name + "(";
        String values = ") values (";

        for (int i = 0; i < column_row_values.length - 1; i++){
            for (int v = 0; v < column_row_values[i + 1].length; v++){
                if (v == column_row_values[i + 1].length - 1){
                    insert_into += column_row_values[0][v];
                    values += "'" + column_row_values[i + 1][v] + "'";
                }

                else{
                    insert_into += column_row_values[0][v] + ",";
                    if (column_row_values[i + 1][v].contains("'")){
                        column_row_values[i + 1][v] = column_row_values[i + 1][v].replace("'", " ");
                        values += "'" + column_row_values[i + 1][v] + "'" + ",";
                    }

                    else {
                        values += "'" + column_row_values[i + 1][v] + "'" + ",";
                    }
                }

            }

//            System.out.println(insert_into);
            insert_into += values + ")";
            System.out.println(insert_into);
            stmt.executeUpdate(insert_into);
            insert_into = " ";
            values = " ";
            insert_into = "insert into " + table_name + "(";
            values = ") values (";

        }
//        INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country)
//        VALUES (value1, value2, value3, ...);

    }

    public void DropTable() throws SQLException {
        Statement stmt = est_conn.createStatement();
//        implement
        String dropTable = "drop table " + table_name;
        stmt.executeUpdate(dropTable);
    }


}
