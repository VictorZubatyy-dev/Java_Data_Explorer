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
    private JLabel county_label, reset_label;
    private JButton display_button, reset_button;
    private JTextArea attraction_names;
    private JScrollPane scroll_pane;
    private SQL sql;

    public GUI(){
//        null constructor
    }
    //    GUI constructor
    public GUI(String[] counties, SQL sql) throws SQLException {
        set_SQL(sql);
        set_Counties(counties);
        get_SQL();
    }


    private void set_Counties(String[] counties) {
        this.counties = counties;
    }

    public void CreateGui(String title){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(title);
        panel = new JPanel();
        frame.add(panel);
        Components(panel);
        frame.setSize(500,500);
        frame.setVisible(true);
    }

    public void Components(JPanel panel){

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(3,3,3,3);
//      create label for counties

        county_label = new JLabel("County");
        c.gridx = 0;
        c.gridy = 0;
        panel.add(county_label, c);

        combobox = new JComboBox(counties);
        combobox.addActionListener(this);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(combobox, c);

        display_button = new JButton("Display");
        c.gridx = 2;
        c.gridy = 0;
        display_button.addActionListener(this);
        panel.add(display_button, c);

        attraction_names = new JTextArea();
        c.ipady = 50;
        c.ipadx = 5;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        attraction_names.setLineWrap(true);
        attraction_names.setEditable(false);
        panel.add(attraction_names, c);

        scroll_pane = new JScrollPane(attraction_names);
//        scroll_pane.add(scrollBar);
        panel.add(scroll_pane, c);

        reset_button = new JButton("Reset");
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        c.ipady = 0;
        reset_button.addActionListener(this);
        panel.add(reset_button, c);

        reset_label = new JLabel("To display new attractions, please press the reset button");
        reset_label.setForeground(Color.red);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.ipady = 0;
        reset_label.setVisible(false);
        panel.add(reset_label, c);
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
                if (attraction_names.getText().isBlank()){
                    try {

                        attraction_name = sql.Attractions(county);

                        System.out.println(attraction_name.size());
                        for (int i = 0; i < attraction_name.size(); i++){

                            attraction_names.append(attraction_name.get(i));
                            attraction_names.append("\n");
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
                else{
                    reset_label.setVisible(true);
                    display_button.setToolTipText("Click the reset button to display new results.");
                }
            }
        }

        else if (e.getSource()==reset_button){
            attraction_names.setText("");
            attraction_name.clear();
            reset_label.setVisible(false);
        }
    }


}