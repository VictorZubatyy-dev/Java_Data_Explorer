package com.dataexplorer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class GUI extends JFrame implements ActionListener{
    private ArrayList<String> county_array;
    private ArrayList<String> attraction_name;
    private String[] counties;
    private String county;
    private JFrame frame;
    private JPanel panel;
    private JComboBox combobox;
    private JLabel county_label;
    private JButton display_button;
    private JTextArea attraction_names;
    private JScrollPane scroll_pane;
    private SQL sql;

    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;

//    GUI constructor
    public GUI(String title, String[] counties, SQL sql) throws SQLException {
        set_SQL(sql);
        get_SQL();
//        create frame
        frame = new JFrame();

//        create label for counties
        county_label = new JLabel();
//        create combobox with counties within them using an array
        combobox = new JComboBox(counties);
        combobox.addActionListener(this);

        display_button = new JButton();
        display_button.setSize(10,10);
        display_button.addActionListener(this);


        attraction_names = new JTextArea();
        attraction_names.setLineWrap(true);

        scroll_pane = new JScrollPane(attraction_names);
//        scroll_pane.setPreferredSize(new Dimension( 100));




        panel = new JPanel();
//        panel.setBorder(BorderFactory.createEmptyBorder(110,30,110,30));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        panel.add(county_label);
        county_label.setText("County:");
        panel.add(combobox);
        panel.add(display_button);
        panel.add(scroll_pane);

        display_button.setText("Display");
        frame.add(panel);
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(title);

        frame.setVisible(true);
    }

    protected SQL get_SQL() {
        return sql;
    }

    protected void set_SQL(SQL sql) {
        this.sql = sql;
    }

    //    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==combobox){
            county = (String)combobox.getSelectedItem();
        }

        else if (e.getSource()==display_button){
            if(county == null){
                System.out.println("Choose a county");
            }
            else{
                try {

                    attraction_name = sql.Attractions(county);

                    System.out.println(attraction_name.size());
                    for (int i = 0; i < attraction_name.size(); i++){
//                        for (int x = 0; x < attra)
                        attraction_names.append(attraction_name.get(i) + "," + " ");


                        System.out.println(attraction_name.get(i));
                    }

//                    for (String name : attraction_name){
//                        attraction_names.insert();
//                        attraction_names.append(name + " ");
//                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
            }
}
