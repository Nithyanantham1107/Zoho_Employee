package com.dbconnection;

import com.exception.DBConnectionException;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static HikariDataSource dataSource;

    public static void setDataSource(HikariDataSource source) {

        dataSource = source;
        System.out.println("Here the source is " + source);
    }


    public static Connection getConnection() throws DBConnectionException {
        try {

            return dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("Connection failed , Error accured while Making connection");
            throw new DBConnectionException(e);


        }


    }


//    public static Connection getConnection() throws DBConnectionException {
//        try {
//            Class.forName("org.postgresql.Driver");
//            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
//            System.out.println("Connected to database");
//            return conn;
//        } catch (SQLException | ClassNotFoundException e) {
//            System.out.println("Connection failed , Error accured while Making connection");
//            throw new DBConnectionException(e);
//
//
//        }
//
//
//    }
}
