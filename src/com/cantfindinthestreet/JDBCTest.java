package com.cantfindinthestreet;

import java.sql.*;
import java.util.ResourceBundle;

public class JDBCTest {
    private static ResourceBundle rb=ResourceBundle.getBundle("file/peizhi");
    private static String driver=rb.getString("driver");
    private static String url=rb.getString("url");
    private static String root=rb.getString("user");;
    private static String password=rb.getString("password");;
    static {
        try {
            Class.forName(driver);//注册驱动
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
        Connection conn= DriverManager.getConnection(url,root,password);
        return conn;
    }
    public static void close(Connection conn, Statement st, ResultSet rs){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(st!=null){
            try {
                st.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
