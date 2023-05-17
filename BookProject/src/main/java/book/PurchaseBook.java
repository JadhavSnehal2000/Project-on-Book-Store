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
@WebServlet("/purchasebook")
public class PurchaseBook extends HttpServlet{
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
		String id=req.getParameter("id");
		String qty=req.getParameter("qty");
		
		int id1=Integer.parseInt(id);
		int itemqty=Integer.parseInt(qty);
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		PrintWriter pw=resp.getWriter();
		
		String query="select stock,price from book_info where id=?";
		String query1="update book_info set stock=? where id=?";
		
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, id1);
			rs=pstmt.executeQuery();
			if(rs.next())
			{
				int dbstock=rs.getInt(1);
				double dbprice=rs.getDouble(2);
				if(itemqty<=dbstock)
				{
					double total=itemqty*dbprice;
					pw.print("<h1>TOTAL AMOUNT"+total+"</h1>");
					pstmt=con.prepareStatement(query1);
					pstmt.setInt(1, dbstock-itemqty);
					pstmt.setInt(2, id1);
					pstmt.executeUpdate();
				}
				else 
				{
					pw.print("<h1>BOOK OUT OF STOCK</h1>");
				}
			}
				else
				{
					pw.print("<h1>BOOK NOT FOUNT</h1>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
