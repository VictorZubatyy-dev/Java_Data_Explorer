package com.dataexplorer;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FileProcessor {
    private String file;
    private final ArrayList<String[]> records_array = new ArrayList<String[]>();

    public FileProcessor(File File) {
        set_File_Name(file);
    }

    private void set_File_Name(String file) {
        this.file = file;
    }

    public void ReadFile(File file){
        String[] array = {};
        String line;


        try {
            BufferedReader read = new BufferedReader(new FileReader(file));

            try {
                String line1 = read.readLine();

                System.out.println(line1);
                String [] line1_array = line1.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                records_array.add(line1_array);

                String [] line_array;
                final int length = line1_array.length;


//                records_array.add(line1_array);

//                split, then check, if the length is greater than 9, then get first two elements, remove second one and put into first
                while ((line = read.readLine()) != null) {
                    line_array = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                    records_array.add(line_array);
//                    System.out.println(records_array);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

//    inserting records_array columns and values into a hashmap
    public String [][] InsertData(){
        String [] columns = records_array.get(0);
        int no_of_columns = columns.length;
        int no_of_rows = records_array.size();
        String [][] column_row_values = new String[no_of_rows][no_of_columns];
        String[] values = {};

        for (int x = 0; x < columns.length; x++){
            column_row_values[0][x] = columns[x];
        }

//        -1 because we are getting the next array element within the list
//            get no of records so 545
//            insert into the record the values from values array
//            return the columns array and the multidimensional array

        for (int k = 0; k < records_array.size() - 1; k++){
            values = records_array.get(k + 1);
                for (int j = 0; j < values.length; j++){
                    column_row_values[k + 1][j] = values[j];
                }
        }

        for (int k = 0; k < records_array.size(); k++){
            System.out.println(column_row_values[k][4]);
        }
        return column_row_values;
    }

//    getters and setters
    protected String getFile() {
        return file;
    }
}
