package com.misys.edu;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Pay_Fine")
public class Pay_Fine extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Pay_Fine() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Connection conn = null;
		SQLConnections SQLConn = new SQLConnections();
		String loanid = "";

		int i = 0;
		String msg = "";

		String selected[] = request.getParameterValues("fine");

		if (selected == null) {
			msg = " KINDLY ENTER THE DATA FOR FEW MORE FIELDS FINE PAYMENT FAILURE ";
			pw.println("<font size='6' color=red>" + msg + "</font>");
			return;
		}

		if (selected.length > 1) {
			msg = "KINDLY PAY THE AMOUNT FOR ONE CARD FIRST.FAILURE!!";
			pw.println("<font size='6' color=red>" + msg + "</font>");
			return;
		}

		try {
			conn = SQLConn.getSQLConnection();

			PreparedStatement pst = (PreparedStatement) conn
					.prepareStatement("SELECT loan_id from BOOK_LOANS WHERE card_no = '"
							+ selected[0]
							+ "' AND date_in IS NOT NULL AND date_in > due_date");

			ResultSet rs = pst.executeQuery();

			if (!rs.next()) {
				msg = "BOOK NOT AT ALL CHECKED IN SO FAILURE IN FINE PAYMENT";
				pw.println("<font size='6' color=red>" + msg + "</font>");

				response.setContentType("text/html");

				pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
				return;
			}
			do {
				loanid = rs.getString(1);

				pst = (PreparedStatement) conn
						.prepareStatement("UPDATE FINES SET paid = '1' where loan_id = "
								+ loanid + " AND paid IS NULL");

				i = pst.executeUpdate();

				if (i != 0) {
					msg = "FINE HAS BEEN PAID AND ENTRY FOR THAT LOANID HAS BEEN UPDATED "
							+ loanid;
					pw.println("<font size='6' color=blue>" + msg + "</font>");

					response.setContentType("text/html");

					pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
				} else {
					msg = "FINE PAYMENT NOT SUCCESSFUL FOR THE LOAN ID "
							+ loanid + "....FINE HAS BEEN ALREADY PAID ";
					pw.println("<font size='6' color=red>" + msg + "</font>");

					response.setContentType("text/html");
					pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
				}
			} while (rs.next());
			pst.close();
		}

		catch (Exception e) {
			pw.println(e);
		}
	}
}
