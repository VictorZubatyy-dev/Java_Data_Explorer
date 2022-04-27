// Purpose: Java Database Application
// Academic Year: 2021-2022
// Author: Victor Zubatyy - D21125389 - DT211/C
// Date: April 2022

package com.dataexplorer;

import java.io.*;
import java.util.ArrayList;

public class FileProcessor {
//    instance variables
    private File file;
    private final ArrayList<String[]> records_array = new ArrayList<String[]>();

//    Constructor
    public FileProcessor(File file) {
        set_File_Name(file);
    }

//    setter
    protected void set_File_Name(File file) {
        this.file = file;
    }

    public void ReadFile() {
        String line;

        try {
            BufferedReader read = new BufferedReader(new FileReader(file));
            try {

                String line1 = read.readLine();
                if (line1 == null){
                    System.out.println("Incorrect file");
                    System.exit(0);
                }
                System.out.println(line1);
//                custom regex delimter allows for values with commas to be ignored and therefore, any CSV file can be parsed dynamically
//                reference: Stack overflow : https://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes
                String[] line1_array = line1.strip().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                records_array.add(line1_array);

                String[] line_array;

                while ((line = read.readLine()) != null) {
                    line_array = line.strip().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                    records_array.add(line_array);
//                    System.out.println(records_array);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //    inserting records_array columns and values into a multidimensional array
    public String[][] InsertData() {
//        insert the CSV values into a two dimensional array
        String[] columns = records_array.get(0);
        int no_of_columns = columns.length;
        int no_of_rows = records_array.size();
        String[][] column_row_values = new String[no_of_rows][no_of_columns];
        String[] values = {};


        for (int x = 0; x < columns.length; x++) {
            column_row_values[0][x] = columns[x];
        }

//        -1 because we are getting the next array element within the list
//            get no of records so 545
//            insert into the record the values from values array
//            return the columns array and the multidimensional array

        for (int k = 0; k < records_array.size() - 1; k++) {
            values = records_array.get(k + 1);
            for (int j = 0; j < values.length; j++) {
                column_row_values[k + 1][j] = values[j];
            }
        }
//  test
//        for (int k = 0; k < records_array.size(); k++){
//            System.out.println(column_row_values[k][4]);
//        }
        return column_row_values;
    }
}
