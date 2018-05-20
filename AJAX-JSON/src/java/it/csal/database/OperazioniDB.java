/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.csal.database;

/**
 * @(#)OperazioniDB.java
 * Operazioni di utilitÃ  per applicazioni DB
 *
 * @author Carlo
 */
import java.io.File;
import java.sql.*;
import java.util.*;

public class OperazioniDB {

    public static int query(String query) throws SQLException {
		Statement stm = DBConnect.getConnection().createStatement();
		ResultSet rs = stm.executeQuery(query);
		rs.close();

		int count = 0;
		while (rs.next()) {
			count++;
		}
		return count;
	}    
    
    
    /**
     * Recupera il tipo di un campo di una Tabella
     * @param nomeTalella il nome della tabella o query
     * @param nomeAttributo il nome dell'attributo
     * @return l'intero che codifica il tipo
     */
    public static int getTipoAttributo(String nomeTabella, String nomeAttributo)
            throws  SQLException {
        Connection conn = null;
        DatabaseMetaData mtd = null;
        ResultSet rstCol = null;
        int ris = 0;

        conn = DBConnect.getConnection();
        mtd = conn.getMetaData();
        rstCol = mtd.getColumns(null, null, nomeTabella, nomeAttributo);

        if (rstCol.next()) {
            ris = rstCol.getInt("DATA_TYPE");
        } else {
            ris = 0;
        }
        /*
        System.out.println("\nTable: ["+nomeTabella+"]");
        // rstCol = mtd.getColumns(null, null, nomeTabella, null);
        while (rstCol.next()) {
        String name = rstCol.getString("COLUMN_NAME");
        String type = rstCol.getString("TYPE_NAME");
        int size = rstCol.getInt("COLUMN_SIZE");

        System.out.println("Column name: [" + name + "]; type: [" + type
        + "]; size: [" + size + "]");
        }
         * */

        rstCol.close();


        return ris;
    }

    /**
     * Verifica se un campo di una Tabella Ã¨ numerico
     * @param nomeTabella il nome della tabella o query
     * @param nomeAttributo il nome dell'attributo
     * @return true se il campo Ã¨ di un tipo numerico
     */
    public static boolean isNumerico(String nomeTabella, String nomeAttributo)
            throws SQLException {
        int tipo = getTipoAttributo(nomeTabella, nomeAttributo);

        return ((tipo == Types.INTEGER) || (tipo == Types.DECIMAL)
                || (tipo == Types.FLOAT) || (tipo == Types.DOUBLE)
                || (tipo == Types.BINARY) || (tipo == Types.BIGINT)
                || (tipo == Types.NUMERIC) || (tipo == Types.REAL)
                || (tipo == Types.SMALLINT) || (tipo == Types.TINYINT));
    }

    /**
     * Verifica se un campo di una Tabella Ã¨ Date, Time o TIMESTAMP
     * @param nomeTabella il nome della tabella o query
     * @param nomeAttributo il nome dell'attributo
     * @return true se il tipo  Ã¨ Date, Time o TIMESTAMP
     */
    public static boolean isDateOTime(String nomeTabella, String nomeAttributo)
            throws SQLException {
        int tipo = getTipoAttributo(nomeTabella, nomeAttributo);
        

        return ((tipo == Types.DATE) || (tipo == Types.TIMESTAMP)
                || (tipo == Types.TIME));
    }


    /**
     * Recupera i valori di una riga
     * @param nomeTabella il nome della tabella o query
     * @param nomeKey il nome del campo chiave
     * @param elencoNomiCampi, ArrayList contenente i nomi dei campi da estrarre
     * @return ArrayList i cui elementi sono ArrayList delle tplue dei campi 
     * specificati
     */
    public static ArrayList getRow(String nomeTabella, String nomeKey, 
            Object valKey, String[] elencoNomiCampi) 
            throws  SQLException {
        Connection conn = null;
        Statement stm = null;
        ResultSet rst = null;
        ArrayList elencoTuple = new ArrayList();
        ArrayList elencoValori = null;
        String strQuery;
        boolean estraiTutti = true;
        ResultSetMetaData rsmd;
        int numberOfColumns;

        if ((elencoNomiCampi == null) || (elencoNomiCampi.length == 0)) {
            strQuery = "Select * ";
        } else {
            estraiTutti = false;
            strQuery = "Select ";
            for (Object x : elencoNomiCampi) {
                strQuery += x + ",";
            }
            strQuery = strQuery.substring(0, strQuery.length() - 1);
        }
        strQuery += " FROM " + nomeTabella;
        if ((nomeKey != null) && (!nomeKey.equals("")) && (valKey != null)) {
            strQuery += " WHERE " + nomeKey + "=";
            if (isNumerico(nomeTabella, nomeKey)) {
                strQuery += valKey;
            } else {
                strQuery += "'" + valKey + "'";
            }
        }
        /*
        System.out.println(strQuery);
         */

        conn = DBConnect.getConnection();
        stm = conn.createStatement();
        rst = stm.executeQuery(strQuery);
        if (estraiTutti) {
            rsmd = rst.getMetaData();
            numberOfColumns = rsmd.getColumnCount();
        } else {
            numberOfColumns = elencoNomiCampi.length;
        }
        /*
        System.out.println("query eseguita");
         */
        while (rst.next()) {
            elencoValori = new ArrayList(numberOfColumns);
            if (estraiTutti) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    elencoValori.add(rst.getObject(i));
                }
            } else {
                for (int i = 0; i < numberOfColumns; i++) {
                    elencoValori.add(rst.getObject((String)elencoNomiCampi[i]));
                }
            }
            elencoTuple.add(elencoValori);
        }

        rst.close();
        stm.close();

        return elencoTuple;
    }

    public static ArrayList getResult (String query) throws SQLException {
		if (query(query) < 0) {
			return null;
		}
		ArrayList row;
		ArrayList rows = new ArrayList();

		Statement stm = DBConnect.getConnection().createStatement();
		ResultSet rs = stm.executeQuery(query);

		ResultSetMetaData rsmd = rs.getMetaData();
		int numberOfColumns = rsmd.getColumnCount();

		while (rs.next()) {
			row = new ArrayList(numberOfColumns);

			for (int i = 1; i <= numberOfColumns; i++) {
				row.add(rs.getObject(i));
			}

			rows.add(row);
		}

		stm.close();
		return rows;
    }
    
    
    /**
     * Recupera i valori di una colonna
     * @param nomeTabella il nome della tabella o query
     * @param nomeAttributo il nome della colonna
     * @param nomeKey il nome del campo chiave
     * @param elencoNomiCampi, ArrayList contenente i nomi dei campi da estrarre
     * @return ArrayList contenente i valori della colonna richiesta
     */
    public static ArrayList getCols(String nomeTabella, String nomeAttributo,
            String nomeKey, Object valKey) throws SQLException {
        Connection conn = null;
        Statement stm = null;
        ResultSet rst = null;
        Exception esito = null;
        ArrayList elencoValori = new ArrayList();
        String strQuery;

        strQuery = "Select " + nomeAttributo + " FROM " + nomeTabella;
        if ((nomeKey != null) && (!nomeKey.equals(""))) {
            strQuery += " WHERE " + nomeKey + "=";
            if (isNumerico(nomeTabella, nomeKey)) {
                strQuery += valKey;
            } else {
                strQuery += "'" + valKey + "'";
            }
        }
        conn = DBConnect.getConnection();
        stm = conn.createStatement();
        rst = stm.executeQuery(strQuery);
        while (rst.next()) {
            elencoValori.add(rst.getObject(1));
        }
        rst.close();
        stm.close();

        return elencoValori;
    }

    /**
     * Verifica se un campo di una Tabella Ã¨ DATE, TIMESTAMP o TIME
     * @param nomeTabella il nome della tabella o query
     * @param nomeAttributo il nome dell'attributo
     * @return true se il campo Ã¨ di un tipo numerico
     */
    public static String strDateOTime(int tipoCampo, Object valDateOTime)
            throws SQLException {
        String str = "";
        if (!(valDateOTime instanceof GregorianCalendar)) {
          throw new SQLException("Il valore NON Ã¨ del tipo GREGORIAN CALENDAR");
        }
        GregorianCalendar calendar = (GregorianCalendar) valDateOTime;
        if ((tipoCampo == Types.DATE) || (tipoCampo == Types.TIMESTAMP)) {
            str = "#" + +calendar.get(Calendar.YEAR) + "/" + 
                    calendar.get(Calendar.DAY_OF_MONTH) + "/"
                    + (calendar.get(Calendar.MONTH) + 1) + "#";
        }

        if ((tipoCampo == Types.TIME) || (tipoCampo == Types.TIMESTAMP)) {
            str += " " + calendar.get(Calendar.HOUR_OF_DAY) + "."
                    + calendar.get(Calendar.MINUTE) + "."
                    + calendar.get(Calendar.SECOND);
        }


        return str;
    }

    /**
     * Aggiunge un record ad una tabella
     * @param nomeTabella il nome della tabella o query
     * @param nomeCampi, elenco dei campi della tabella da valorizzare
     * @param valori, elenco dei valori dei campi della tabella
     */
    public static void insRec(String nomeTabella, String[] nomeCampi,
            Object[] valori) throws SQLException {
        Connection conn = null;
        Statement stm = null;
        ResultSet rst = null;
        String strQuery, strCampo = "";
        int numIns = 0;

        strQuery = "Insert INTO  " + nomeTabella + " (";
        for (int i = 0; i < nomeCampi.length; i++) {
            strQuery += nomeCampi[i] + ",";
        }
        strQuery = strQuery.substring(0, strQuery.length() - 1);
        strQuery += ") VALUES (";
        for (int i = 0; i < valori.length; i++) {
            if (isNumerico(nomeTabella, nomeCampi[i])) {
                strQuery += valori[i] + ",";
            } else {
                if (isDateOTime(nomeTabella, nomeCampi[i])) {
                    strCampo = strDateOTime(getTipoAttributo(nomeTabella, 
                            nomeCampi[i]), valori[i]);
                } else {
                    strCampo = "" + valori[i];
                }
                strQuery += "'" + strCampo + "',";
            }
        }
        strQuery = strQuery.substring(0, strQuery.length() - 1);
        strQuery += ");";

        System.out.println(strQuery);

        conn = DBConnect.getConnection();
        stm = conn.createStatement();
        numIns = stm.executeUpdate(strQuery);
        if (numIns != 1) {
            throw new SQLException("Inserimento non riuscito");
        }
        stm.close();
    }

}
