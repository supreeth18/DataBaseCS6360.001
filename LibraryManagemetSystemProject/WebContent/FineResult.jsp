<%@ page import="java.util.*" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
    </head>
    <body>
    <form name="FineCheckForm" action=Pay_Fine method="post">    
        <table width="1080px" align="center"
               style="border:1px solid #000000;">
            <tr>
                <td colspan=6 align="center"
                    style="background-color:teal">
                    <b>Fine Table Results</b></td>
            </tr>
            <tr style="background-color:lightgrey;"> 
                <td><b>Borrower first name</b></td>
                <td><b>Borrower last name</b></td>             
                <td><b>Card Number</b></td>
                <td><b>Fine amount</b></td>                
                <td><b>Paid Status</b></td>
                <td><b>Select</b></td>                             
            </tr>
            <%
                int count = 0;
                String color = "#F9EBB3";
                if (request.getAttribute("fine_list") != null) 
                {
                	ArrayList f_list = (ArrayList) request.getAttribute("fine_list");
                    Iterator<ArrayList<String>> itr = f_list.iterator();
                    while (itr.hasNext())
                    {                        
                        count++;
                        ArrayList<String> tuple = (ArrayList<String>) itr.next();                        
            %>
            <tr style="background-color:<%=color%>;">
                <td><%=tuple.get(0)%></td>
                <td><%=tuple.get(1)%></td>
                <td><%=tuple.get(2)%></td>
                <td><%=tuple.get(3)%></td> 
                <td><%=tuple.get(4)%></td>      
                <td><input type="checkbox" name="fine" value="<%=tuple.get(2)%>"/>&nbsp;</td>                               
            </tr>
            <%
                    }
                }
                if (count == 0) 
                {
            %>
            <tr>
                <td colspan=4 align="center"
                    style="background-color:#eeffee"><b>No Fines Found...</b></td>
            </tr>
            <%  }
            %>
        </table>
        <input type="submit" value="Pay Fine" style="height:36px; width:80px; position: absolute; left: 1200px; top: 50px; font-size:110%;color:magenta;"><br>
        </form>       
 
        <a href=default.html style="font-size:150%; position:absolute; left:1200px; top:5px"><ins>Home</ins></a>
    </body>
</html>