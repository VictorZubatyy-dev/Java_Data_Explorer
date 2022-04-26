package com.dataexplorer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EstablishConnection {
    private String url;
    private String user;
    private String password;
    private Connection est_conn;


//    constructor
    public EstablishConnection(String url, String user, String password){
        set_Url(url);
        set_User(user);
        set_Password(password);
        get_User();
    }

    //    establishing connection to database
    public Connection Connection(String url, String user, String password) throws SQLException {
        Connection est_conn = DriverManager.getConnection(url, user, password);
        System.out.println("Successful connection");
        return est_conn;
    }
//    public Connection NewConnect(String url, String user, String password, String con) throws SQLException {
//
//        return new_conn;
//    }

//     getters and setters
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
