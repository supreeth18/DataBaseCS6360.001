package com.misys.edu;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/Book_Search")
public class Book_Search extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Book_Search() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		bookSearch(request, response);
	}

	private void bookSearch(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Connection conn = null;
		SQLConnections SQLConn = new SQLConnections();

		String bookid = request.getParameter("isbn10");
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String titlearray[];
		String authorarray[];

		String query = "Select B.isbn10,title, B.author,branch_id,no_of_copies,A.author,A.isbn10 from BOOKS B, BOOK_AUTHORS A, BOOK_COPIES C where B.isbn10 = A.isbn10 AND A.isbn10 = book_id";

		if (bookid.isEmpty() && title.isEmpty() && author.isEmpty()) {
			String msg1 = "Failure In the Search";
			String msg2 = "Kindly Enter Few More Details To search";

			pw.println("<body bgcolor=\"lightblue\">");
			pw.println("<font size='6' color=red>" + msg1 + "</font>");
			pw.println("<br>");
			pw.println("<font size='6' color=red>" + msg2 + "</font>");
			pw.println("<br>");
			pw.println("<br><a href=\"default.html\" style=\"font-size: 30px; text-decoration: none; position:absolute; left:1150px;top:5px;\"><ins>Home</ins></a>");
			return;
		}

		if (!(bookid.isEmpty())) {
			bookid = "%" + bookid + "%";
			query = query.concat(" AND B.isbn10 LIKE '" + bookid + "'");
		}
		if (!(title.isEmpty())) {

			titlearray = title.split(" ");
			title = "";
			for (int i = 0; i < titlearray.length; i++) {
				title += "%" + titlearray[i];
			}
			title += "%";
			query = query.concat(" AND B.title LIKE '" + title + "'");
		}
		if (!(author.isEmpty())) {

			authorarray = author.split(" ");
			author = "";
			for (int i = 0; i < authorarray.length; i++) {
				author += "%" + authorarray[i];
			}
			author += "%";
			query = query.concat(" AND A.author LIKE '" + author + "'");
		}

		try {
			conn = SQLConn.getSQLConnection();

			ArrayList<String> search_tuple = null;
			ArrayList<ArrayList<String>> search_list = new ArrayList<ArrayList<String>>();

			PreparedStatement pst = (PreparedStatement) conn
					.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				search_tuple = new ArrayList<String>();

				search_tuple.add(rs.getString(1));
				search_tuple.add(rs.getString(2));
				search_tuple.add(rs.getString(3));
				search_tuple.add(rs.getString(4));
				search_tuple.add(rs.getString(5));
				search_list.add(search_tuple);
			}
			request.setAttribute("search_list", search_list);

			RequestDispatcher view = request
					.getRequestDispatcher("/SearchResult.jsp");
			view.forward(request, response);
			conn.close();
		} catch (Exception e) {
			pw.println(e);
		}
	}
}
