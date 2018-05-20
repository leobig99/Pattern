<%-- 
    Document   : libriXGenere
    Created on : 10-mag-2015, 7.40.17
    Author     : carlo
--%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="it.csal.entity.Libro"%>
<%@page import="it.csal.entity.Genere"%>
<%@page import="it.csal.database.DBConnect"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.csal.database.LibrerieDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%
        JSONArray lst = new JSONArray();
        JSONObject obj = new JSONObject();
        String strCodice = request.getParameter("codice");
        int idGenere = Integer.parseInt(strCodice.trim());
        ArrayList<Libro> ar = LibrerieDAO.getBookOfKind(idGenere);
        if (ar.size() > 0) {
            for (Libro l : ar) {
                obj = new JSONObject();
                obj.put("id", l.getID());
                obj.put("titolo", l.getTitle());
                lst.add(obj);
            }
        }
        obj = new JSONObject();
        obj.put("libri", lst);

        out.println(obj.toJSONString());
        DBConnect.closeConnection();
%>
