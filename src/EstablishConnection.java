// Purpose: Java Database Application
// Academic Year: 2021-2022
// Author: Victor Zubatyy - D21125389 - DT211/C
// Date: April 2022

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EstablishConnection {
    //    instance variables
    private String url;
    private String user;
    private String password;

    public EstablishConnection(String url, String user, String password){
        set_Url(url);
        set_User(user);
        set_Password(password);
    }

    //    establishing connection to database, returns the connection object
    public Connection Connection(String url, String user, String password) throws SQLException {
        Connection est_conn = DriverManager.getConnection(url, user, password);
        System.out.println("Successful connection");
        return est_conn;
    }

//    getters and setters
    protected void set_Url(String url) {
        this.url = url;
    }

    protected String get_Url(){
        return url;
    }

    protected void set_User(String user) {
        this.user = user;
    }

    protected String get_User(){
        return user;
    }

    protected void set_Password(String password) {
        this.password = password;
    }

    protected String get_Password(){
        return password;
    }
}
