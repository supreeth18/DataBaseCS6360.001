package com.misys.edu;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/Update_CheckIn")
public class Update_CheckIn extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Update_CheckIn() {
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
		String bookid = "";
		String branchid = "";
		String cardnum = "";
		String duedate = "";
		String checkin = "";
		String loanid = "";
		String diff = "";
		String strcheckin = "(";
		int i = 0;
		String query = "";
		Statement st = null;
		ResultSet rs = null;
		String msg = "";
		String msg2 = "";
		String datediff = "";
		String datediff2 = "";

		String selected[] = request.getParameterValues("loan");
		String datein[] = request.getParameterValues("dateinValue");
		String dateinVal[] = new String[10];
		int count = 0;
		boolean empty = true;

		if (selected == null) {
			msg = "CHECK_IN FAILURE......PLEASE SELECT FEW BOOKS TO CHECK IN..";
			pw.println("<font size='6' color=red>" + msg + "</font>");

			response.setContentType("text/html");
			pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
			return;
		}

		for (int k = 0; k < datein.length; ++k) {
			if (datein[k].length() > 0) {
				dateinVal[count] = datein[k].toString();

				if (!(dateinVal[count].matches("\\d{4}-\\d{2}-\\d{2}"))) {
					msg = "Check in failed......Invalid date!!!! Format is YYYY-MM-DD";
					pw.println("<font size='6' color=red>" + msg + "</font>");

					response.setContentType("text/html");
					pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
					return;
				}
				count++;
				empty = false;
			}
		}

		if (count > 0 && count < selected.length) {
			msg = "CHECK IN FAILURE...KINDLY INSERT THE DATE INTO THAT FIELD..";
			pw.println("<font size='6' color=red>" + msg + "</font>");

			response.setContentType("text/html");
			pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
			return;
		}

		if (count > 0 && count > selected.length) {
			msg = "CHECKIN FAILURE....Date in HAS BEEN ENTERED FOR UNSELECTED BOOK..";
			pw.println("<font size='6' color=red>" + msg + "</font>");

			response.setContentType("text/html");
			pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
			return;
		}

		for (i = 0; i < (selected.length - 1); i++) {
			strcheckin = strcheckin.concat(selected[i] + ",");
		}
		strcheckin = strcheckin.concat(selected[i] + ")");

		try {
			conn = SQLConn.getSQLConnection();
			PreparedStatement pst = null;

			if (!empty) {
				for (int k = 0; k < selected.length; ++k) {
					query = "Select DATEDIFF(date_out,'" + dateinVal[k]
							+ "'), DATEDIFF(current_date(),'" + dateinVal[k]
							+ "') from BOOK_LOANS L where loan_id = "
							+ selected[k];
					st = conn.createStatement();
					rs = st.executeQuery(query);
					if (rs.next()) {
						datediff = rs.getString(1);
						datediff2 = rs.getString(2);
						int idatediff = Integer.parseInt(datediff);
						int idatediff2 = Integer.parseInt(datediff2);

						if (idatediff > 0) {
							msg = "CHECK IN FAILURE......DATE IN MUST BE AFTER DATE OUT..";
							pw.println("<font size='6' color=red>" + msg
									+ "</font>");

							response.setContentType("text/html");
							pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
							return;
						}

						if (idatediff2 < 0) {
							msg = "CHECK IN FAILURE......DATE IN CANNOT BE A FUTURE DATE..";
							pw.println("<font size='6' color=red>" + msg
									+ "</font>");

							response.setContentType("text/html");
							pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
							return;
						}
					}
				}
			}

			if (empty) {
				pst = (PreparedStatement) conn
						.prepareStatement("UPDATE BOOK_LOANS SET date_in = current_date() where date_in IS NULL AND loan_id IN "
								+ strcheckin);
				i = pst.executeUpdate();

				if (i == 0) {
					msg = "FAILURE IN CHECKING IN BOOKS..BOOK HAS BEEN ALREADY CHECKED IN";
					pw.println("<font size='6' color=red>" + msg + "</font>");

					response.setContentType("text/html");
					pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
					return;
				}
			} else {
				for (int m = 0; m < selected.length; ++m) {
					pst = (PreparedStatement) conn
							.prepareStatement("UPDATE BOOK_LOANS SET date_in = '"
									+ dateinVal[m]
									+ "' where date_in IS NULL AND loan_id = "
									+ selected[m]);
					i = pst.executeUpdate();

					if (i == 0) {
						msg = "Failed to check in the book(s)..Book might have already checked in";
						pw.println("<font size='6' color=red>" + msg
								+ "</font>");

						response.setContentType("text/html");
						pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
					}
				}
			}

			query = "Select isbn10, branch_id, card_no, due_date, date_in, loan_id, DATEDIFF(date_in, due_date) from BOOK_LOANS L where loan_id IN "
					+ strcheckin;
			st = conn.createStatement();
			rs = st.executeQuery(query);
			boolean fineTable = false;
			double fine_val = 0;
			String fine_amt = "";
			while (rs.next()) {
				bookid = rs.getString(1);
				branchid = rs.getString(2);
				cardnum = rs.getString(3);
				duedate = rs.getString(4);
				checkin = rs.getString(5);
				loanid = rs.getString(6);
				diff = rs.getString(7);

				int iDiff = Integer.parseInt(diff);

				query = "SELECT COUNT(*), fine_amt FROM FINES WHERE loan_id ="
						+ loanid;
				st = conn.createStatement();
				ResultSet rs2 = st.executeQuery(query);
				if (rs2.next()) {
					if (Integer.parseInt(rs2.getString(1)) > 0) {
						fineTable = true;
						fine_amt = rs2.getString(2);
						fine_val = Double.parseDouble(fine_amt);
					}
				}

				if (iDiff > 0) {

					double fine = iDiff * (float) 0.25;
					String fineStr = String.valueOf(fine);
					msg2 = "Penalty of " + fineStr
							+ "$ has been added for late check in!!!!!!";

					if (fineTable) {
						if ((fine != fine_val)) {
							pst = (PreparedStatement) conn
									.prepareStatement("UPDATE FINES SET fine_amt ="
											+ fine
											+ " WHERE loan_id ="
											+ loanid
											+ " AND fine_amt != "
											+ fine + " AND paid IS NULL");
							int j = pst.executeUpdate();

							if (j != 0) {

							} else {
								msg = "Failed to update fine table";
								pw.println("<font size='6' color=red>" + msg
										+ "</font>");

								response.setContentType("text/html");
								pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
							}
						}
					} else {
						pst = (PreparedStatement) conn
								.prepareStatement("INSERT INTO FINES(loan_id, fine_amt) Values("
										+ loanid + "," + fine + ")");
						int j = pst.executeUpdate();

						if (j != 0) {

						} else {
							msg = "Failed to insert fines table";
							pw.println("<font size='6' color=red>" + msg
									+ "</font>");

							response.setContentType("text/html");
							pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
						}
					}
				}

				if (i != 0) {
					msg = "The book with book id " + bookid
							+ " checked in successfully at the branch id "
							+ branchid;
					pw.println("<font size='6' color=blue>" + msg + "</font>");
					pw.println("<br><font size='6' color=blue>" + msg2
							+ "</font>");

					response.setContentType("text/html");

					pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
				}
				fineTable = false;
			}
			pst.close();
		}

		catch (Exception e) {
			pw.println(e);
		}
	}
}
