package com.cantfindinthestreet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.nashorn.internal.scripts.JD;

import javax.xml.transform.Result;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class XiuGaiTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String getid = request.getParameter("id");
        String contextPath = request.getContextPath();
        int i=0;
        out.print("        <!DOCTYPE html>");
        out.print("<head>");
        out.print("    <meta charset='UTF-8'>");
        out.print("    <title>修改页面</title>");
        out.print("</head>");
        out.print("<body>");
        out.print("    <h1>修改页面</h1>");
        out.print("    <hr>");

        try {
            conn=JDBCTest.getConnection();
            String sql="select id,name,age,tel from people where id=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,getid);
            rs= ps.executeQuery();
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String age = rs.getString("age");
                String tel = rs.getString("tel");

                out.print("<form action='"+contextPath+"/update' method='post'>");
                out.print("        <input type='text' name='id' value='"+id+"'/ readonly><br>");
                out.print("        <input type='text' name='name' value='"+name+"'/><br>");
                out.print("        <input type='text' name='age' value='"+age+"'/><br>");
                out.print("        <input type='text' name='tel' value='"+tel+"'/><br>");
                out.print("        <br>");
                out.print("        <input type='submit' value='提交' />");
                out.print("</form>");

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCTest.close(conn,ps,rs);
        }
        out.print("        </body>");
        out.print("</html>");
    }
}
