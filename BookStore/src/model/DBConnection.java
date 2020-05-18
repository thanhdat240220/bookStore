/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tvcpr
 */
public class DBConnection {

    private Connection connection;
    private String driver;
    private String connectionString;
    private String dbUserName;
    private String dbPassWord;
    private String port;

    public DBConnection() {
        port = "1433";
        driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        connectionString = "jdbc:sqlserver://127.0.0.1:" + port + ";databaseName=book_store";
        dbUserName = "sa";
        dbPassWord = "123456";
    }

    public DBConnection(String userName, String passWord, String dbString) {
        driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        connectionString = "jdbc:sqlserver://127.0.0.1:" + port + ";databaseName=" + dbString;
        dbUserName = userName;
        dbPassWord = passWord;
    }

    public Connection Connecting() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(connectionString, dbUserName, dbPassWord);
            System.out.println("Connected successfully!");
        } catch (Exception ex) {
            System.out.println("Connected failed!");
        }
        return connection;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        connection = null;

    }

}
