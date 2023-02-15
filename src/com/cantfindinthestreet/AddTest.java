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

public class AddTest extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String contextPath = request.getContextPath();
		String name=request.getParameter("name");
		String age=request.getParameter("age");
		String tel=request.getParameter("tel");
		int i=0;
		try {
			conn=JDBCTest.getConnection();
			conn.setAutoCommit(false);
			String sql="insert into people (name,age,tel) values(?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1,name);
			ps.setString(2,age);
			ps.setString(3,tel);
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
			//request.getRequestDispatcher("back.html").forward(request,response);
		}
	}
}
