package it.csal.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlo
 */
public class DBConnect {
      private static Connection conn = null;
      public static Connection getConnection() {
        if(conn == null) {
            try {
               Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("qqq"+DBConnect.class.getName());
            }

            String dburl = "jdbc:sqlite:F:\\A.s 2017-18\\Informatica\\AJAX-JSON\\dblibrerie.sqlite";

            try {
                conn = DriverManager.getConnection(dburl);
            } catch (SQLException ex) {
                Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            }            
        } 
        return conn ;
    } 
    
      public static void closeConnection() {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            } 
            finally {
                conn = null;
            }
        } 
    }        
      
}