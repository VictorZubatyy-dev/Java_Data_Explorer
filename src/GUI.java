// Purpose: Java Database Application
// Academic Year: 2021-2022
// Author: Victor Zubatyy - D21125389 - DT211/C
// Date: April 2022

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


public class GUI extends JFrame implements ActionListener{
    //    instance variables
    private ArrayList<String> attraction_name;
    private String[] counties;
    private String county;
    private JFrame frame;
    private JPanel panel;
    private JComboBox combobox;
    private JLabel county_label, reset_label;
    private JButton display_button, reset_button, exit_button;
    private JTextArea attraction_names;
    private JScrollPane scroll_pane;
    private SQL sql;
    private Connection est_conn;
    private JFileChooser jc;
    private GridBagConstraints c;

    //    Constructor
    public GUI(Connection est_conn){
        set_Connection(est_conn);
    }

//    getters and setters
    protected void set_Connection(Connection est_conn) {
        this.est_conn = est_conn;
    }

    public void CreateGui(String title) throws SQLException {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(title);
        panel = new JPanel();
        frame.add(panel);
        Components(panel);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

//    GridBagLayout
//    REFERENCE: ORACLE JDBC https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
    public void Components(JPanel panel) throws SQLException {

        panel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(3,3,3,3);

        reset_label = new JLabel("To display new attractions, please press the reset button");
        reset_label.setForeground(Color.red);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.ipady = 0;
        reset_label.setVisible(false);
        panel.add(reset_label, c);

        jc = new JFileChooser();
        jc.setSize(new Dimension(500,500));
        c.gridx = 0;
        c.gridy = 1;
        jc.addActionListener(this);
        panel.add(jc, c);

        county_label = new JLabel("County");
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        panel.add(county_label, c);

        combobox = new JComboBox();
        combobox.addActionListener(this);
        c.gridx = 0;
        c.gridy = 3;
        panel.add(combobox, c);

        display_button = new JButton("Display");
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        display_button.addActionListener(this);
        panel.add(display_button, c);

        attraction_names = new JTextArea();
        c.ipady = 50;
        c.ipadx = 5;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        attraction_names.setLineWrap(true);
        attraction_names.setEditable(false);
        panel.add(attraction_names, c);

        scroll_pane = new JScrollPane(attraction_names);
//        scroll_pane.add(scrollBar);
        panel.add(scroll_pane, c);

        reset_button = new JButton("Reset");
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        c.ipady = 0;
        reset_button.addActionListener(this);
        panel.add(reset_button, c);

        exit_button = new JButton("Clear table and close.");
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        c.ipady = 0;
        exit_button.addActionListener(this);
        exit_button.setEnabled(false);
        panel.add(exit_button, c);
    }

    //    @Override
    public void actionPerformed(ActionEvent e) {
//        File chooser reference: https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
        if (e.getSource() == jc){
            int returnVal = jc.showOpenDialog(GUI.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = jc.getSelectedFile();
                //This is where a real application would open the file.
                System.out.println("Opening: " + file.getName() + "." + "\n");
                    FileProcessor file_name = new FileProcessor(file);
                    file_name.ReadFile();
                    String [][] column_row_values = file_name.InsertData();

//                    sql object is used to access all the functions within the SQL class, mainly for querying
                    sql = new SQL(column_row_values);
                try {
                    sql.set_Table_Name("Attractions");
                    if (sql.Show_Tables(sql.get_Table_Name(), est_conn)) {
                        System.out.println("Table exists, choose another name.");
                        System.exit(0);
                    }

                    else {
                        sql.CreateTable(est_conn);
                        sql.UpdateTable(est_conn);
                        display_button.setEnabled(false);
                        reset_button.setEnabled(false);
                        jc.setEnabled(false);
                        jc.setVisible(false);
                        exit_button.setEnabled(true);

//                        this applications GUI is built for this particular CSV file
//                        the GUI is not fully dynamic display interesting facts multiple CSV files
                        if (file.getName().equals("Attractions.csv")) {
                            display_button.setEnabled(true);
                            reset_button.setEnabled(true);
                            counties = Display_Counties();
                            combobox.setModel(new DefaultComboBoxModel(counties));
                            exit_button.setEnabled(true);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
//county is used to display the relevant attractions within it
        else if (e.getSource()==combobox){
                county = (String) combobox.getSelectedItem();
        }

        else if (e.getSource()==display_button){
            if(county == null){
                System.out.println("Choose a county");
            }
            else{
                if (attraction_names.getText().isBlank()){
                    try {
                        attraction_name = sql.Attractions(county, est_conn);

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
                if (attraction_name != null) {
                    attraction_name.clear();
                }
                reset_label.setVisible(false);
        }

        else if(e.getSource()==exit_button){
            try {
                sql.DropTable(est_conn);
                System.exit(0);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

//    function loads in the counties
    public String[] Display_Counties() throws SQLException {
        sql.Country_Query(est_conn);
        counties = sql.Combo_Values();
        return counties;
    }


}