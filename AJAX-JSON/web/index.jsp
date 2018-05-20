<%-- 
    Document   : index
    Created on : 27-apr-2015, 13.25.36
    Author     : salvagno.carlo
--%>

<%@page import="it.csal.database.DBConnect"%>
<%@page import="it.csal.entity.Genere"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.csal.database.LibrerieDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
		<script type="text/javascript" src="js/ajaxFunz.js"></script>
	</head>
    <body>
		<select name="codice" onchange="cambia(this.value)">
			<%
				out.println("<option value=' '>scegli un genere</option>");
				ArrayList<Genere> ar = LibrerieDAO.loadKind();
				for (Genere g : ar) {
					out.println("<option value='" + g.getCodice() + "'>");
					out.print(g.getDescrizione());
					out.print("</option>");
				}
				DBConnect.closeConnection();
			%>


		</select>
		<div id="xmldata">
			<table id="tblLibri" />       
		</div>
    </body>
</html>
