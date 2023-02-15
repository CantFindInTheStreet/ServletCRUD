package com.cantfindinthestreet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String contextPath = request.getContextPath();
        String id=request.getParameter("id");
        int i=0;
        try {
            conn=JDBCTest.getConnection();
            conn.setAutoCommit(false);//关闭自动提交
            String sql="delete from people where id=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,id);
            i=ps.executeUpdate();
            conn.commit();
        } catch (SQLException throwables) {
            if(conn!=null){
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            throwables.printStackTrace();
        }finally {
            JDBCTest.close(conn,ps,rs);
        }
        if(i==1){
            response.sendRedirect(contextPath+"/use");
            //request.getRequestDispatcher("/use").forward(request,response);
        }else{
            response.sendRedirect(contextPath+"/back.html");
            //request.getRequestDispatcher("/back.html").forward(request,response);
        }
    }
}
