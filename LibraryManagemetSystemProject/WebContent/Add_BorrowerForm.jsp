<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Borrower</title>

<style>
pre {
    color:black;
    font-size:170%;
}
h1 {
    color:black;
    text-align:center;
    font-size:200%;
}  
</style>
</head>

<body style="background-color:yellow ">
<body>

<h1><ins>BORROWER MANAGEMENT FORM</ins></h1>
<form name="AddBorrowerform" action=Add_Borrower method="post">
<pre>
<b>ENTER THE  SSN             :  <input type="text" name="Ssn" size="40"></b><br>
<b>ENTER THE FIRST NAME       :  <input type="text" name="First_name" size="40"></b><br>
<b>ENTER THE LAST NAME        :  <input type="text" name="last_name" size="40"></b><br>
<b>ENTER THE ADDRESS          :  <input type="text" name="address" size="40"></b><br>
<b>ENTER THE PHONE NUMBER     :  <input type="text" name="phone" size="40"></b><br>
</pre>
<pre><input type="submit" value="ADD_BORROWER_DETAILS" style="height:36px; width:450px; position: relative; left: 300px; top: -28px; font-size:110%;color:black;"></pre><br>
</form>

<a href=default.html style="font-size:190%; position:absolute; left:1150px; top:5px"><ins>HomePage</ins></a>
</body>
</html>