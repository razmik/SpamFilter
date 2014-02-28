/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DBFacade;

import SFBasic.Email;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Work to integrate all the database communication 
 * from the application to the Database
 *
 * @author Rashmika
 */
public class MailBoxFacade {
    
    /**
     * To get all the mails in the given table
     * @param dbname
     * @param tableName
     * @return ArrayList<Email>
     */
    public ArrayList<Email> getAllMails(String dbname, String tableName){

        Connection connection = this.getConnection(dbname);
        
        ArrayList<Email> emails = new ArrayList<Email>();

        try{

            String query = "SELECT * from "+tableName;

            //getting statement object
            PreparedStatement  pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){

                Email mail = new Email();

                mail.setFrom(rs.getString(2));
                mail.setTo(rs.getString(3));
                mail.setCc(rs.getString(4));
                mail.setBcc(rs.getString(5));
                mail.setMessage(rs.getString(6));
                mail.setPoints(rs.getDouble(7));
                
                emails.add(mail);

            }

            pstmt.close();
            connection.close();

        } catch(SQLException ex){
            Logger.getLogger(MailBoxFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

        return emails;

    }

    /**
     * Will add a mail to the given table
     * @param dbname
     * @param tableName
     * @param mail
     * @return true if the mail is added successfully
     */
    public synchronized boolean addMail(String dbname, String tableName, Email mail){
        
        boolean sent = false;

        try {
            
            Connection connection = this.getConnection(dbname);

            String query = "INSERT INTO " + tableName + " (sf_from, sf_to, sf_cc, sf_bcc, sf_message, sf_point) values(?, ?, ?, ?, ?, ?)";

            //getting statement object
            PreparedStatement  pstmt = (PreparedStatement) connection.prepareStatement(query);

            pstmt.setString(1, mail.getFrom());
            pstmt.setString(2, mail.getTo());
            pstmt.setString(3, mail.getCc());
            pstmt.setString(4, mail.getBcc());
            pstmt.setString(5, mail.getMessage());
            pstmt.setDouble(6, mail.getPoints());

            pstmt.executeUpdate();

            pstmt.close();
            connection.close();
            
            sent = true;

        } catch (SQLException ex) {
            Logger.getLogger(MailBoxFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sent;

    }
    
    /**
     * Method to get the database connection
     * @param dbName
     * @return 
     */
    private Connection getConnection(String dbName){
        
        try {
            //Setting up the connection
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/" + dbName;
            Connection connection = (Connection) DriverManager.getConnection( url,"root", "");
            return connection;
        } catch (SQLException ex) {
            Logger.getLogger(MailBoxFacade.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MailBoxFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }
    
    /**
     * Method to create the database if it doesn't exist
     * @param dbname 
     */
    private void createDB(String dbname){
        
        try {
            
            Connection connection = this.getConnection(dbname);

            String query = "CREATE DATABASE "+dbname;

            //getting statement object
            Statement  stmt = (Statement) connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            
            query = "CREATE TABLE spambox (sf_id int(11) PRIMARY KEY, sf_from char(50), sf_to char(50), sf_cc char(50), sf_bcc char(50), sf_message text, sf_point float)";
            stmt = (Statement) connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            
            query = "CREATE TABLE nonspambox (sf_id int(11) PRIMARY KEY, sf_from char(50), sf_to char(50), sf_cc char(50), sf_bcc char(50), sf_message text, sf_point float)";
            stmt = (Statement) connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(MailBoxFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * For testing purposes
     * @param args 
     */
    public static void main(String[] args) {
        Email e = new Email();
        e.setFrom("navak");
        e.setBcc("afs");
        e.setBcc("fasd");
        e.setMessage("asfasd");
        e.setTo("faf");
        e.setPoints(0.7);
        
        MailBoxFacade fac = new MailBoxFacade();
        fac.addMail("spamfilterdb", "spambox", e);
        
    }
    
}
