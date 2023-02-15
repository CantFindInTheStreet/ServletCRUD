package com.cantfindinthestreet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class UseTest extends HttpServlet {
    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }*/

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String contextPath = request.getContextPath();

        out.print("<!DOCTYPE html>");
        out.print("<html>");
        out.print("<head>");
		out.print("<meta charset='utf-8'>");
        out.print("<title>Use</title>");
        out.print(" </head>");
        out.print(" <body>");

        out.print("<script type='text/javascript'> ");
        out.print("        function del(id){");
        out.print("           if(window.confirm('确定要删除吗？')){");
        out.print("                 document.location.href = '"+contextPath+"/delete?id=' + id;");
        out.print("            }");
        out.print("         }");
		out.print("</script>");

        out.print("<table border='1px' width='60%' align='center'>");
		out.print("	<tr>");
		out.print("		<th>序号</th>");
		out.print("			<th>姓名</th>");
		out.print("		<th>年龄</th>");
		out.print("			<th>电话</th>");
		out.print("		<th>操作</th>");
		out.print("		</tr>");

        try {
            conn=JDBCTest.getConnection();
            String sql="select id,name,age,tel from people";
            ps=conn.prepareStatement(sql);
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
				out.print("	<td>");
				out.print("	<a href='"+contextPath+"/xiugai?id="+id+"'>修改</a>");
				out.print("<a href='javascript:void(0)' onclick='del("+id+")' >删除</a>");
				//out.print("	<a href='javascript:void(0)' onclick='del("+id+")'>删除</a>");
				//out.print("		<a href='/servlets/news'>详情</a>");
                //out.print("		<a href='"+contextPath+"/news'>详情</a>");
				out.print("   <a href='"+contextPath+"/news?id="+id+"'>详情</a>");
				out.print("	</td>");
				out.print("</tr>");


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCTest.close(conn,ps,rs);
        }

        out.print("</table>");
        //out.print("   <a href='"+contextPath+"/news?id="+id+"'>详情</a>");
        out.print("<a href='"+contextPath+"/add.html'>新增</a>");
        out.print("</body>");
        out.print("</html>");

    }
}
