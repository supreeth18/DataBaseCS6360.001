package com.misys.edu;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Fine_Process")
public class Fine_Process extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Fine_Process() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		handleFine(request, response);
	}

	private void handleFine(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Connection conn = null;
		SQLConnections SQLConn = new SQLConnections();
		String msg1 = "";
		String msg2 = "";

		String fname = request.getParameter("first_name");
		String lname = request.getParameter("last_name");
		String cardno = request.getParameter("card_no");
		String Action = request.getParameter("fine");

		String query = "";

		Statement st;
		try {
			conn = SQLConn.getSQLConnection();
			ResultSet rs;
			ArrayList<String> fine_tuple = null;
			ArrayList<ArrayList<String>> fine_list = new ArrayList<ArrayList<String>>();

			if (!cardno.isEmpty()) {
				query = "select count(*) from borrower where card_no =  '"
						+ cardno + "' ";
				st = conn.createStatement();
				rs = st.executeQuery(query);
				if (rs.next()) {
					if (Integer.parseInt(rs.getString(1)) == 0) {
						msg1 = "Fine process failed!!!!!!!!!!!!!";
						msg2 = "Invalid card number";

						pw.println("<body bgcolor=\"lightblue\">");
						pw.println("<font size='6' color=red>" + msg1
								+ "</font>");
						pw.println("<br>");
						pw.println("<font size='6' color=red>" + msg2
								+ "</font>");
						pw.println("<br>");

						pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
						return;
					}
				}
			}

			query = "Select B.first_name, B.last_name, B.card_no, SUM(F.fine_amt), F.paid from BOOK_LOANS L, BORROWER B, FINES F where L.card_no = B.card_no AND F.loan_id = L.loan_id";

			if (!(fname.isEmpty())) {
				fname = "%" + fname + "%";
				query = query.concat(" AND B.first_name LIKE '" + fname + "'");
			}

			if (!(lname.isEmpty())) {
				lname = "%" + lname + "%";
				query = query.concat(" AND B.last_name LIKE '" + lname + "'");
			}

			if (!(cardno.isEmpty())) {
				query = query.concat(" AND L.card_no = '" + cardno + "' ");
			}

			// THIS IS THE DETAILS ABOUT ALL THE PAID FINE RECORDS
			if (Action.equals("Paid")) {
				query = query.concat(" AND F.paid IS NOT NULL");
			}
			// THIS IS THE DETAILS ABOUT ALL THE UNPAID FINE RECORDS
			else if (Action.equals("UnPaid")) {
				query = query.concat(" AND F.paid IS NULL");
			}
			// THIS WILL SHOW YOU BOTH PAID AND UNPAID FINE RECORDS
			else if (Action.equals("All")) {

			}

			query = query.concat(" GROUP BY L.card_no");
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				fine_tuple = new ArrayList<String>();

				fine_tuple.add(rs.getString(1));
				fine_tuple.add(rs.getString(2));
				fine_tuple.add(rs.getString(3));
				fine_tuple.add(rs.getString(4));
				fine_tuple.add(rs.getString(5));
				fine_list.add(fine_tuple);
			}
			request.setAttribute("fine_list", fine_list);
			RequestDispatcher view = request
					.getRequestDispatcher("/FineResult.jsp");
			view.forward(request, response);
			conn.close();
		} catch (Exception e) {
			pw.println(e);
		}
	}

}
