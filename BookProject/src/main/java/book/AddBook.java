package book;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addlink")
public class AddBook extends HttpServlet{
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
		//fetch the value from html
		String name=req.getParameter("name");
		String auther=req.getParameter("auther");
		String stock=req.getParameter("stock");
		String price=req.getParameter("price");
		
		//parse value
		int stock1=Integer.parseInt(stock);
		double price1=Double.parseDouble(price);
		
		//declare resources
		PreparedStatement pstmt;
		
		//Create query
		String query="insert into book_info(name,auther,stock,price)values(?,?,?,?)";
		
		//set the value
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, auther);
			pstmt.setInt(3, stock1);
			pstmt.setDouble(4, price1);
			int count=pstmt.executeUpdate();
			PrintWriter pw=resp.getWriter();
			pw.print("<h1>"+count+"Record Added Successfully</h1>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
