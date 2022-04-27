// Purpose: Java Database Application
// Academic Year: 2021-2022
// Author: Victor Zubatyy - D21125389 - DT211/C
// Date: April 2022

package com.dataexplorer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQL{
    //    instance variables
    private String table_name;
    private String[][] column_row_values;
    private final ArrayList<String> county_array = new ArrayList<String>();
    private final ArrayList<String> attraction_name = new ArrayList<>();

//constructor
    public SQL(String [][] column_row_values) {
        set_column_row_value(column_row_values);
    }

//    getters and setters
    protected void set_Table_Name(String table_name) {
        this.table_name = table_name;
    }

    protected void set_column_row_value(String[][] column_row_values) {
        this.column_row_values = column_row_values;
    }

    protected String get_Table_Name() {
        return table_name;
    }

    //CRUD
    public void CreateTable(Connection est_conn) throws SQLException {
//        creating the table using concatenation for the columns and values within the loop
//        all the value types are varchar with 255 max
        String createTable = "create table " + table_name + " (";

        for (int v = 0; v < column_row_values[0].length; v++){
            if (v == column_row_values[0].length - 1) {
                createTable += column_row_values[0][v] + " varchar(255)";
            }
            else{
                for (int c = 0; c < column_row_values[0][v].length(); c++){
//                  syntax for inserting values needs to be percise, therefore, I ensured all values got be inserted
//                  without errors using these checks I implemented below
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
//            String[][] updated_column_value_rows = UpdateTable(est_conn);
//            set_column_row_value(updated_column_value_rows);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateTable(Connection est_conn) throws SQLException {
//        this function inserts the mutlidemensional array values into the table created
        Statement stmt = est_conn.createStatement();
        for (int v = 0; v < column_row_values[0].length; v++){
            System.out.println(this.column_row_values[0][v]);
        }

        String insert_into = "insert into " + table_name + "(";
        String values = ") values (";

//       for each column of the table insert a value
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
//        updated the array
        set_column_row_value(column_row_values);
    }

//    this function drops the table
    public void DropTable(Connection est_conn) throws SQLException {
        Statement stmt = est_conn.createStatement();
//        implement
        String dropTable = "drop table " + table_name;
        stmt.executeUpdate(dropTable);
            System.out.println("You have successfully dropped table " + table_name);
    }

//    this function is specific to the Attractions csv data as it is only used if the actualy file is parsed and the table
//    is created (Attraction table for demonstration purposes)
    public void Country_Query(Connection est_conn) throws SQLException {
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

//    All the attractions of the county the user has selected are returned and put into the JTextArea
    public ArrayList<String> Attractions(String county, Connection est_conn) throws SQLException {
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


//    This function checks to see whether the user after inputting a CSV file has choosen a valid table name,
//    meaning a table that does not exist within the database
//    only applicable if user was able to keep loading into csv files in the application or closed the application
//    without clicking the 'Reset table and close' buttion
    public boolean Show_Tables(String table_name, Connection est_conn) throws SQLException {
        Statement stmt = est_conn.createStatement();
        ResultSet rs = stmt.executeQuery("show tables like" + "'" + table_name + "'");

        if (rs.next() == false) {
            return false;
        }
        return true;
    }
}