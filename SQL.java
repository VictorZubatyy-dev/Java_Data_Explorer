package com.dataexplorer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQL{
//    private variables used to manipulate database
    private String table_name;
    private String[][] column_row_values;
    private Connection est_conn;
    private final ArrayList<String> county_array = new ArrayList<String>();
    private final ArrayList<String> attraction_name = new ArrayList<>();

    public SQL(Connection est_conn){
        set_Connection_Object(est_conn);

    }
    //    intilisation of class constructor, data, connection and table name are passed into the constructor to set and get values
    public SQL(String[][] column_row_values, Connection est_conn, String table_name) {
        get_column_row_values();
        get_Table_Name();
        set_column_row_value(column_row_values);
        set_Table_Name(table_name);
        set_Connection_Object(est_conn);
        get_Connection_Object();
    }

    protected Connection get_Connection_Object() {
        return est_conn;
    }

    public SQL() {

    }

    protected void set_Connection_Object(Connection est_conn) {
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

    //CRUD
    public void CreateTable() throws SQLException {

        String createTable = "create table " + table_name + " (";

        for (int v = 0; v < column_row_values[0].length; v++){
            if (v == column_row_values[0].length - 1) {
                createTable += column_row_values[0][v] + " varchar(255)";
            }

            else{
                for (int c = 0; c < column_row_values[0][v].length(); c++){
                    boolean is_white_space = Character.isWhitespace(column_row_values[0][v].charAt(c));
                    if (is_white_space == true){
                        column_row_values[0][v] = column_row_values[0][v].replace(column_row_values[0][v].charAt(c), '_');
                    }
                }
                createTable += column_row_values[0][v] + " varchar(255),";
            }

            System.out.println(createTable);

        }

        createTable += ")";

        try (Statement stmt = est_conn.createStatement()) {
            stmt.executeUpdate(createTable);
            System.out.println("You have successfully created table " + table_name);
            String[][] updated_column_value_rows = UpdateTable(column_row_values);
            set_column_row_value(updated_column_value_rows);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[][] UpdateTable(String[][] column_row_values) throws SQLException {
        Statement stmt = est_conn.createStatement();
        for (int v = 0; v < column_row_values[0].length; v++){
            System.out.println(this.column_row_values[0][v]);
        }

        String insert_into = "insert into " + table_name + "(";
        String values = ") values (";

        for (int i = 0; i < column_row_values.length - 1; i++){
            for (int v = 0; v < column_row_values[i + 1].length; v++){
                if (v == column_row_values[i + 1].length - 1){
                    insert_into += column_row_values[0][v];
                    if (column_row_values[i + 1][v].contains("'")) {
                        column_row_values[i + 1][v] = column_row_values[i + 1][v].replace("'", " ");
                        values += "'" + column_row_values[i + 1][v] + "'";
                    }

                    else {
                        values += "'" + column_row_values[i + 1][v] + "'";
                    }
                }

                else{
//                  have to replace the commas within the values so that they are not recognised as separate values when inserting them into their relevant columns
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


        System.out.println("Values have been successfully inserted into " + table_name);
        return column_row_values;
//        INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country)
//        VALUES (value1, value2, value3, ...);

    }

    public void DropTable() throws SQLException {
        Statement stmt = est_conn.createStatement();
//        implement
        String dropTable = "drop table " + table_name;
        stmt.executeUpdate(dropTable);
        System.out.println("You have successfully dropped table " + table_name);
    }

    public void Country_Query() throws SQLException {
        Statement stmt = est_conn.createStatement();
        String county = " ";
//        String select_country = "select "
        ResultSet rs = stmt.executeQuery("select distinct AddressRegion from Attractions where AddressRegion is not null");
        while(rs.next()) {
            int i = 0;
            county = rs.getString("AddressRegion");
            if (county.isBlank()) {
                System.out.println("Null value not added");
            } else {
                county_array.add(county);
            }
        }
//        System.out.println(county_array);
//        System.out.println(county_array.size());
    }

    //    insert the array list counties into an array for the combobox
    public String[] Combo_Values(){
        String[] counties;
        int no_of_counties = county_array.size();
        counties = new String[no_of_counties];

        for (int i = 0; i < counties.length; i++){
            counties[i] = county_array.get(i);
        }
        return counties;
    }

    public ArrayList<String> Attractions(String county) throws SQLException {
        Statement stmt = est_conn.createStatement();
        System.out.println("select Name from Attractions where AddressRegion=" + county);
        ResultSet rs = stmt.executeQuery("select Name from Attractions where AddressRegion=" + "'" + county + "'");
        while(rs.next()) {
            int i = 0;
            county = rs.getString("Name");
            if (county.isBlank()) {
                System.out.println("Null value not added");
            } else {
                attraction_name.add(county);
            }
        }
        return attraction_name;
    }

    public boolean Show_Tables(String table_name) throws SQLException {
        Statement stmt = est_conn.createStatement();
        ResultSet rs = stmt.executeQuery("show tables like" + "'" + table_name + "'");

        if (rs.next() == false){
            return false;
        }
        return true;
    }



    public ArrayList<String> getCounty_array(){
        return county_array;
    }

}