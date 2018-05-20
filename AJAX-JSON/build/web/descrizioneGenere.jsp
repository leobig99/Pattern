<%@page import="it.csal.database.LibrerieDAO"%>
<%@page import="it.csal.entity.Genere"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<% 
       String strCodice = request.getParameter("codice");
       Genere gen = LibrerieDAO.genereLibro(strCodice);
       String ris = gen.getDescrizione();
       out.println(ris);
%>