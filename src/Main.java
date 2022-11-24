// Purpose: Java Database Application
// Academic Year: 2021-2022
// Author: Victor Zubatyy - D21125389 - DT211/C
// Date: April 2022

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException{
//       Instantiated object conn is used to set and retrieve the database connection values
        EstablishConnection conn = new EstablishConnection("jdbc:mysql://localhost/database","root", "Chelseano.1");
//        Instantiated object est_conn allows for the querying and interaction with the database
        Connection est_conn = conn.Connection(conn.get_Url(), conn.get_User(), conn.get_Password());
//        Instantiated object gui is used to open the GUI using the CreateGui function
        GUI gui = new GUI(est_conn);
        gui.CreateGui("Attractions in Ireland");
    }
}


