<%@ page import="java.util.*" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
    </head>
    <body>
        <table width="1080px" align="center"
               style="border:1px solid #000000;">
            <tr>
                <td colspan=5 align="center"
                    style="background-color:teal">
                    <b>Book Search Results</b></td>
            </tr>
            <tr style="background-color:lightgrey;">
                <td><b>Book ID</b></td>
                <td><b>Title</b></td>
                <td><b>Author</b></td>
                <td><b>Branch ID</b></td>
                <td><b>Copies</b></td>                
            </tr>
            <%
                int count = 0;
                String color = "#F9EBB3";
                if (request.getAttribute("search_list") != null) 
                {
                    ArrayList s_list = (ArrayList) request.getAttribute("search_list");
                    Iterator itr = s_list.iterator();
                    while (itr.hasNext())
                    {                        
                        count++;
                        ArrayList tuple = (ArrayList) itr.next();
            %>
            <tr style="background-color:<%=color%>;">
                <td><%=tuple.get(0)%></td>
                <td><%=tuple.get(1)%></td>
                <td><%=tuple.get(2)%></td>
                <td><%=tuple.get(3)%></td>
                <td><%=tuple.get(4)%></td>               
            </tr>
            <%
                    }
                }
                if (count == 0) 
                {
            %>
            <tr>
                <td colspan=4 align="center"
                    style="background-color:#eeffee"><b>No Book Found..</b></td>
            </tr>
            <%  }
            %>
        </table>
        
        <a href=default.html style="font-size:190%; position:absolute; left:1200px; top:5px"><ins>Home</ins></a>
    </body>
</html>