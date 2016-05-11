package com.misys.edu;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.PreparedStatement;

@WebServlet("/Book_Loan")
public class Book_Loan extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Book_Loan() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		handleCheckOut(request, response);
	}

	private void handleCheckOut(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Connection conn = null;
		SQLConnections SQLConn = new SQLConnections();

		String bookid = request.getParameter("book_id");
		String branchid = request.getParameter("branch_id");
		String cardnum = request.getParameter("card_no");
		String dateout = " ";
		String duedate = " ";

		String msg1 = "";
		String msg2 = "";
		String query = "";

		if (bookid.isEmpty() || branchid.isEmpty() || cardnum.isEmpty()) {
			msg1 = "Failure of Check_Out";
			msg2 = "All the Fileds Are Compulsory.Kindly Fill In all the Details";

			pw.println("<body bgcolor=\"lightblue\">");
			pw.println("<font size='6' color=red>" + msg1 + "</font>");
			pw.println("<br>");
			pw.println("<font size='6' color=red>" + msg2 + "</font>");
			pw.println("<br>");
			pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
			return;
		}

		Statement st;
		try {
			conn = SQLConn.getSQLConnection();
			ResultSet rs;

			query = ("select count(*) from borrower where card_no ='" + cardnum + "'");
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				if (Integer.parseInt(rs.getString(1)) == 0) {
					msg1 = "Failed Check_out";
					msg2 = "Wrong Card Number";

					pw.println("<body bgcolor=\"lightblue\">");
					pw.println("<font size='6' color=red>" + msg1 + "</font>");
					pw.println("<br>");
					pw.println("<font size='6' color=red>" + msg2 + "</font>");
					pw.println("<br>");

					pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
					return;
				}
			}

			query = ("select count(*) from library_branch where branch_id =" + branchid);
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				if (Integer.parseInt(rs.getString(1)) == 0) {
					msg1 = "Failed Check_Out";
					msg2 = "Wrong Branch_Id";

					pw.println("<body bgcolor=\"lightblue\">");
					pw.println("<font size='6' color=red>" + msg1 + "</font>");
					pw.println("<br>");
					pw.println("<font size='6' color=red>" + msg2 + "</font>");
					pw.println("<br>");

					pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
					return;
				}
			}

			query = "Select count(card_no) from BOOK_LOANS L where L.card_no = '"
					+ cardnum + "' AND ISNULL(L.date_in) GROUP BY card_no";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			int Borrower_Count = 0;
			if (rs.next()) {
				Borrower_Count = Integer.parseInt(rs.getString(1));
			}

			if (Borrower_Count >= 3) {
				msg1 = "Failed Check_Out";
				msg2 = "Maximum LIMIT OF 3 CHECK_OUTS HAS BEEN REACHED BY THE BORROWER";

				pw.println("<body bgcolor=\"lightblue\">");
				pw.println("<font size='6' color=red>" + msg1 + "</font>");
				pw.println("<br>");
				pw.println("<font size='6' color=red>" + msg2 + "</font>");
				pw.println("<br>");

				pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
				return;
			}

			query = ("SELECT no_of_copies from BOOK_COPIES C where C.book_id = '"
					+ bookid + "'  AND C.branch_id = " + branchid);
			st = conn.createStatement();
			rs = st.executeQuery(query);
			int Book_Copies = 0;
			if (rs.next()) {
				Book_Copies = Integer.parseInt(rs.getString(1));
			}

			query = "Select count(ISBN10) from BOOK_LOANS L where L.ISBN10 = '"
					+ bookid + "' AND L.branch_id = " + branchid
					+ " AND ISNULL(L.date_in) " + "GROUP BY isbn10, branch_id";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			int Book_Loan_Count = 0;
			if (rs.next()) {
				Book_Loan_Count = Integer.parseInt(rs.getString(1));
			}

			if (Book_Copies == Book_Loan_Count) {
				msg1 = "Check_Out Has Been Failed";
				if (Book_Copies == 0)
					msg2 = "This Particular Branch Does not Contain This Book";
				else
					msg2 = "Please try Again After Some Time->All the copies of this book are checked out.";

				pw.println("<body bgcolor=\"lightblue\">");
				pw.println("<font size='6' color=red>" + msg1 + "</font>");
				pw.println("<br>");
				pw.println("<font size='6' color=red>" + msg2 + "</font>");
				pw.println("<br>");

				pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
				return;
			}

			query = "select current_date()";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				dateout = rs.getString(1);
			}

			query = "select ADDDATE(current_date(), 14)";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				duedate = rs.getString(1);
			}

			query = "INSERT INTO BOOK_LOANS(ISBN10, branch_id, card_no, date_out, due_date, date_in) Values(?, ?, ?, ?, ? ,?)";
			PreparedStatement pst = conn.prepareStatement(query);

			pst.setString(1, bookid);
			pst.setString(2, branchid);
			pst.setString(3, cardnum);
			pst.setString(4, dateout);
			pst.setString(5, duedate);
			pst.setString(6, null);

			int i = pst.executeUpdate();

			if (i != 0) {
				msg1 = "Book checked out.Success :)";
				pw.println("<font size='6' color=blue>" + msg1 + "</font>");

				response.setContentType("text/html");

				pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
			}
			pst.close();
		} catch (Exception e) {
			pw.println(e);
		}
	}
}
