package com.cantfindinthestreet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewsTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;


        out.print("<!DOCTYPE html>");
        out.print("<html>");
	    out.print("<head>");
		out.print("<meta charset='utf-8'>");
		out.print("<title>News</title>");
	    out.print("</head>");
	    out.print("<body>");
		out.print("<h1>个人信息详情</h1>");
		out.print("<hr>");
        out.print("<table border='1px' width='60%' align='center'>");
        out.print("	<tr>");
        out.print("		<th>序号</th>");
        out.print("			<th>姓名</th>");
        out.print("		<th>年龄</th>");
        out.print("			<th>电话</th>");
        out.print("		</tr>");

        try {
            conn=JDBCTest.getConnection();
            String getId=request.getParameter("id");
            //String sql="select id,name,age,tel from people where id="+getId;
            String sql="select id,name,age,tel from people where id=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,getId);
            rs=ps.executeQuery();
            while(rs.next()){
                String id=rs.getString("id");
                String name=rs.getString("name");
                String age=rs.getString("age");
                String tel=rs.getString("tel");

                out.print("<tr>");
                out.print("	<td>"+id+"</td>");
                out.print("	<td>"+name+"</td>");
                out.print("	<td>"+age+"</td>");
                out.print("	<td>"+tel+"</td>");
                out.print("</tr>");

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCTest.close(conn,ps,rs);
        }

        out.print("</table>");
        out.print("<hr>");
		out.print("<input type='button' value='后退' onclick='window.history.back()' />");
	    out.print("</body>");
        out.print("</html>");

    }
}
