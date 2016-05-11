package com.misys.edu;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Statement;
import com.mysql.jdbc.exceptions.jdbc4.*;

@WebServlet(urlPatterns = "/Add_Borrower")
public class Add_Borrower extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public Add_Borrower() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processBorrower(request, response);
	}

	private void processBorrower(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Connection conn = null;
		SQLConnections SQLConn = new SQLConnections();

		String new_id = new String();
		String SSN = request.getParameter("Ssn");
		String fname = request.getParameter("First_name");
		String lname = request.getParameter("last_name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");

		try {
			conn = SQLConn.getSQLConnection();
			Statement statement = (Statement) conn.createStatement();
			String query = "Select max(CARD_NO) as A from borrower;";
			ResultSet rs10 = statement.executeQuery(query);
			while (rs10 != null) {
				if (rs10.next()) {
					Integer res = Integer.parseInt(rs10.getString("A")
							.substring(2));
					System.out.println("max card number" + res);
					res++;
					new_id = "ID00" + res.toString();
				}

				PreparedStatement pst = (PreparedStatement) conn
						.prepareStatement("insert into borrower(CARD_NO,Ssn,First_name,last_name,address,phone) values(?,?,?,?,?,?);");

				if (new_id.isEmpty()) {
					pst.setString(1, null);
				} else {
					pst.setString(1, new_id);
				}

				if (SSN.isEmpty()) {
					pst.setString(2, null);
				} else {
					pst.setString(2, SSN);
				}

				if (fname.isEmpty()) {
					pst.setString(3, null);
				} else {
					pst.setString(3, fname);
				}

				if (lname.isEmpty()) {
					pst.setString(4, null);
				} else {
					pst.setString(4, lname);
				}

				if (address.isEmpty()) {
					pst.setString(5, null);
				} else {
					pst.setString(5, address);
				}

				if (phone.isEmpty()) {
					pst.setString(6, null);
				} else {
					pst.setString(6, phone);
				}

				int i = pst.executeUpdate();

				String msg = " ";

				if (i != 0) {
					msg = " BORROWER DATA HAS BEEN ADDED TO MYSQL DATABASE ";
					pw.println("<font size='6' color=blue>" + msg + "</font>");

					response.setContentType("text/html");

					pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
				} else {
					msg = " Borrower Insertion Failed ";
					pw.println("<font size='6' color=red>" + msg + "</font>");

					response.setContentType("text/html");
					pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
				}
				pst.close();
			}
		} catch (MySQLIntegrityConstraintViolationException e) {
			String msg1 = " Operation Add Has Been Failed ";
			String msg2 = " FName,LName and Address of the Borrower Cannot be Same ";
			String msg4 = " FName, LName, Address Of the Borrower Should NOT Be Empty Fields";

			if (fname.isEmpty() || lname.isEmpty() || address.isEmpty()) {
				pw.println("<body bgcolor=\"lightblue\">");
				pw.println("<font size='6' color=red>" + msg1 + "</font>");
				pw.println("<br>");
				pw.println("<font size='6' color=red>" + msg4 + "</font>");
				pw.println("<br>");
			} else {
				pw.println("<body bgcolor=\"lightblue\">");
				pw.println("<font size='6' color=red>" + msg1 + "</font>");
				pw.println("<br>");
				pw.println("<font size='6' color=red>" + msg2 + "</font>");
				pw.println("<br>");
			}

			response.setContentType("text/html");
			pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
		} catch (Exception e) {
			pw.println(e);
		}
	}

}
