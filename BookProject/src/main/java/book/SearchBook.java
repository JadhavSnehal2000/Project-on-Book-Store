package book;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/searchbook")
public class SearchBook extends HttpServlet {
	Connection con;
	@Override
		public void init() throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try {
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/1ejm9?user=root&password=sql@123");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//fetch the value from user
		String name=req.getParameter("name");
		

		//declare resources
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		PrintWriter pw=resp.getWriter();
		//create query
		String query="select * from book_info where name=?";
		
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, name);
			 rs=pstmt.executeQuery();
				if(rs.next())
				{
				    pw.print("<table border='2'>");
					pw.print("<tr>");
					pw.print("<th>BOOK ID</th>");
					pw.print("<th>BOOK NAME</th>");
					pw.print("<th>BOOK AUTHOR</th>");
					pw.print("<th>BOOK STOCK</th>");
				    pw.print("<th>BOOK PRICE</th>");
					pw.print("</tr>");

					pw.print("<tr>");
					pw.print("<td>"+rs.getInt(1)+"</td>");
					pw.print("<td>"+rs.getString(2)+"</td>");
					pw.print("<td>"+rs.getString(3)+"</td>");
					pw.print("<td>"+rs.getInt(4)+"</td>");
					pw.print("<td>"+rs.getDouble(5)+"</td>");
					pw.print("</tr>");
					
					pw.print("</table>");
				}
				else
				{
					pw.print("<h1>BOOK NOT FOUND</h1>");
				}
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
