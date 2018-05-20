package it.csal.database;

import it.csal.entity.Autore;
import it.csal.entity.Libro;
import it.csal.entity.Genere;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlo
 */
public class LibrerieDAO {
	public static ArrayList<Autore> loadAutoriLibro(int idLibro) {
		ArrayList app = new ArrayList();
		try {
			app = OperazioniDB.getResult("Select tblAutore.* FROM tblAutore inner join tbrLibroAutore on "
					+ " tblAutore.CodiceAutore = tbrLibroAutore.CodiceAutore WHERE tbrLibroAutore.CodiceLibro = " + idLibro);

		} catch (SQLException e) {
			Logger.getLogger(LibrerieDAO.class.getName()).log(Level.SEVERE, null, e);
		}
		ArrayList<Autore> authors = new ArrayList<Autore>();
		for (int i = 0; i < app.size(); i++) {
			ArrayList elem = (ArrayList) app.get(i);
			Autore a = new Autore();
			a.setByDB(elem);
			authors.add(a);

		}
		return authors;
	}

	public static Genere genereLibro(String idLibro) {
		ArrayList app = new ArrayList();
		Genere ris = new Genere();
		try {
			app = OperazioniDB.getResult("SELECT tblGenere.CodiceGenere, Descrizione FROM tblLibro inner join tblGenere on"
					+ " tblLibro.CodiceGenere = tblGenere.CodiceGenere where tblLibro.CodiceLibro = " + idLibro);
			ris.setByDB((ArrayList) app.get(0));
		} catch (SQLException e) {
			Logger.getLogger(LibrerieDAO.class.getName()).log(Level.SEVERE, null, e);
		}
		return ris;
	}

	public static ArrayList<Libro> loadBook() {
		try {
			String sql = "SELECT CodiceLibro, Titolo FROM tblLibro";
			ArrayList<Libro> lib = new ArrayList<Libro>();

			Statement st = DBConnect.getConnection().createStatement();
			ResultSet rst = st.executeQuery(sql);
			while (rst.next()) {
				Libro l = new Libro(rst.getInt("CodiceLibro"), rst.getString("Titolo"));
				lib.add(l);
			};
			st.close();
			return lib;
		} catch (Exception ex) {
			Logger.getLogger(LibrerieDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public static ArrayList<Libro> getBookOfKind(int idGenere) {
		try {
			String sql = "select CodiceLibro, Titolo from tblLibro where CodiceGenere = ?";

			PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
			pstmt.setInt(1, idGenere);

			ArrayList<Libro> lib = new ArrayList<Libro>();
			ResultSet rst = pstmt.executeQuery();
			while (rst.next()) {
				Libro l = new Libro(rst.getInt("CodiceLibro"), rst.getString("Titolo"));
				lib.add(l);
			};
			pstmt.close();
			return lib;
		} catch (Exception ex) {
			Logger.getLogger(LibrerieDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public static ArrayList<Genere> loadKind() {
		try {
			String sql = "select CodiceGenere, Descrizione  from tblGenere order by Descrizione";
			ArrayList<Genere> gen = new ArrayList<Genere>();

			Statement st = DBConnect.getConnection().createStatement();
			ResultSet rst = st.executeQuery(sql);
			while (rst.next()) {
				Genere g = new Genere(rst.getInt("CodiceGenere"), rst.getString("Descrizione"));
				gen.add(g);
			};
			st.close();
			return gen;
		} catch (Exception ex) {
			Logger.getLogger(LibrerieDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

}
