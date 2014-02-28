/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SFEngine;

import DBFacade.MailBoxFacade;
import SFBasic.Email;
import SFBasic.SFProperties;
import SFLogic.SpamAnalyzer;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Work as a mediator between UserInterface, SpamAnalyser and the Database
 * @author Rashmika
 */
public class Mediator {
    
    private String dbname;
    private String tablename_spam;
    private String tablename_nonspam;
    private SpamAnalyzer analyzer;

    public Mediator() {
        
        SFProperties prop = new SFProperties();
        this.dbname = prop.getDbname();
        this.tablename_spam = prop.getTablename_spam();
        this.tablename_nonspam = prop.getTablename_nonspam();
        
        this.analyzer = new SpamAnalyzer();
        
    }
    
    /**
     * Add the mail to the correct folder after analysing it
     * @param mail
     * @return 
     */
    public boolean sendMail(Email mail){
        
        MailBoxFacade facade = new MailBoxFacade();
        
        //check if the mail is a spam
        if(isSpam(mail)){
            JOptionPane.showMessageDialog(null, "This is a spam. Stored in SpamBox", "Spam Filter Confirmation", JOptionPane.WARNING_MESSAGE);
            return facade.addMail(this.dbname, this.tablename_spam, mail);
        }else{
            JOptionPane.showMessageDialog(null, "Mail send successfully. Stored in MailBox.", "Spam Filter Confirmation", JOptionPane.WARNING_MESSAGE);
            return facade.addMail(this.dbname, this.tablename_nonspam, mail);
        }
    }
    
    /**
     * get all the spam mails
     * @return ArrayList<Email>
     */
    public ArrayList<Email> getSpamEmails(){
        MailBoxFacade facade = new MailBoxFacade();
        return facade.getAllMails(this.dbname, this.tablename_spam);
    }
    
    /**
     * get all the legitimat mails
     * @return 
     */
    public ArrayList<Email> getNonSpamEmails(){
        MailBoxFacade facade = new MailBoxFacade();
        return facade.getAllMails(this.dbname, this.tablename_nonspam);
    }
    
    /**
     * check whether a mail is a spam
     * @param mail
     * @return 
     */
    private boolean isSpam(Email mail){
        
        boolean spamMail = this.analyzer.analyseMail(mail);
        mail.setPoints(this.analyzer.getPSpamMail());
        
        return spamMail;
    }  
    
}
