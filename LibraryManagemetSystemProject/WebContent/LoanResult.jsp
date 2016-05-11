<%@ page import="java.util.*" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
    </head>
    <body>
    <form name="CheckinForm" action=Update_CheckIn method="post">
        <table width="1080px" align="center"
               style="border:1px solid #000000;">
            <tr>
                <td colspan=8 align="center"
                    style="background-color:teal">
                    <b>Book Loan Results</b></td>
            </tr>
            <tr style="background-color:lightgrey;">
                <td><b>Loan ID</b></td>
                <td><b>Book ID</b></td>
                <td><b>Branch ID</b></td>
                <td><b>Card Number</b></td>
                <td><b>Date Out</b></td>
                <td><b>Due Date</b></td>
                <td><b>Date In</b></td>  
                <td><b>Select</b></td>              
            </tr>
            <%
                int count = 0;
                String color = "#F9EBB3";
                if (request.getAttribute("loan_list") != null) 
                {
                	ArrayList l_list = (ArrayList) request.getAttribute("loan_list");
                    Iterator<ArrayList<String>> itr = l_list.iterator();
                    while (itr.hasNext())
                    {                        
                        count++;
                        ArrayList<String> tuple = (ArrayList<String>) itr.next();
                        String val = tuple.get(0).toString();
            %>
            <tr style="background-color:<%=color%>;">
                <td><%=tuple.get(0)%></td>
                <td><%=tuple.get(1)%></td>
                <td><%=tuple.get(2)%></td>
                <td><%=tuple.get(3)%></td>
                <td><%=tuple.get(4)%></td>
                <td><%=tuple.get(5)%></td>               
                <td><input type="text" name="dateinValue" value=""></td>                
                <td><input type="checkbox" name="loan" value="<%=tuple.get(0)%>"/>&nbsp;</td>               
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
        <input type="submit" value="CheckIn" style="height:36px; width:80px; position: absolute; left: 1210px; top: 50px; font-size:110%;color:magenta;"><br>
        </form>
        <a href=default.html style="font-size:190%; position:absolute; left:1210px; top:5px"><ins>Home</ins></a>
    </body>
</html>