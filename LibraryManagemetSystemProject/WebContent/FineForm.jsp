<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Check Fine</title>

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

<h1><ins>FINE CHECK FORM</ins></h1>
<form name="FineForm" action=Fine_Process method="post">
<pre>
<b>ENTER THE FIRST NAME OF BORROWER       : <input type="text" name="first_name" size="40"></b><br>
<b>ENTER THE LAST NAME OF BORROWER        : <input type="text" name="last_name" size="40"></b><br>
<b>ENTER THE CARD NUMBER                  : <input type="text" name="card_no" size="40"></b><br>
</pre>
<pre><input type="submit" name ="fine" value="PAID"   style="height:36px; width:140px; position: relative; left: 380px; top: -28px; font-size:110%;color:black;"></pre><br>
<pre><input type="submit" name ="fine" value="UNPAID" style="height:36px; width:160px; position: absolute; left: 570px; top: 268px; font-size:110%;color:black;"></pre><br>
<pre><input type="submit" name ="fine" value="All"    style="height:36px; width:140px; position: absolute; left: 740px; top: 268px; font-size:110%;color:black;"></pre><br>
</form>

<form name="FineRefresh" action=Fine_Refresh method="post">

<pre><input type="submit" name ="fine" value="Refresh_Fine" style="height:36px; width:210px; position: absolute; left: 900px; top: 268px; font-size:110%;color:black;"></pre><br>
</form>

<a href=default.html style="font-size:190%; position:absolute; left:1150px; top:5px"><ins>HomePage</ins></a>
</body>
</html>