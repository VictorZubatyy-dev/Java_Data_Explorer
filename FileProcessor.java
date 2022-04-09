package com.dataexplorer;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FileProcessor {
    private String file;

    public FileProcessor(File file){
        set_FileName(file.getName());
    }

    public void ReadFile(File file){
        String[] array = {};
        String line;
        HashMap<String, String> hash_records = new HashMap<String, String>();
        ArrayList<String[]> records_array = new ArrayList<>();

        try {
            BufferedReader read = new BufferedReader(new FileReader(file));

            try {
                String line1 = read.readLine();
                System.out.println(line1);
                String [] line1_array = line1.split(",");
                final int length = line1_array.length;
                records_array.add(line1_array);

                while ((line = read.readLine()) != null) {
                    array = line.split(",", length);
                    records_array.add(array);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        System.out.println(Arrays.toString(records_array.get(1)));
        for (int k = 0; k < records_array.size() - 1; k++){
            String [] columns = records_array.get(0);
            String [] values = records_array.get(k + 1);

            int c = 0;
                for (String value : values) {
                    hash_records.put(columns[c], value);
                    System.out.println(columns[c]);
                    System.out.println(hash_records.get(columns[c]));
                    c++;
                }
            }
    }

    protected void set_FileName(String file) {
        this.file = file;
    }
}
