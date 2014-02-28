/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SFBasic;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Get the properties from the configuration file
 * @author Rashmika
 */
public class SFProperties {
    
    private float spamThreshold;
    private float defaultThreshold;
    private String username;
    private String password;
    private int limitOfInterestingWords;
    private String goodMailList;
    private String spamMailList;
    private String dbname;
    private String tablename_spam;
    private String tablename_nonspam;
    private String spamAddressFile;
    
    /**
     * At the construction time, read the app.config file and load its data to the application
     */
    public SFProperties() {
        
        InputStream is = null;
        
        try {
            Properties prop = new Properties();
            String fileName = "app.config";
            is = new FileInputStream(fileName);
            prop.load(is);
            
            this.spamThreshold =  Float.parseFloat(prop.getProperty("app.spam_threshhold"));
            this.defaultThreshold =  Float.parseFloat(prop.getProperty("app.defaultThreshold"));
            this.username =  prop.getProperty("app.username");
            this.password =  prop.getProperty("app.password");
            this.goodMailList = prop.getProperty("app.goodMailList");
            this.spamMailList = prop.getProperty("app.spamMailList");
            this.limitOfInterestingWords  = Integer.parseInt(prop.getProperty("app.limitOfInterestingWords"));
            this.dbname = prop.getProperty("app.dbname");
            this.tablename_spam = prop.getProperty("app.spamtable");
            this.tablename_nonspam = prop.getProperty("app.nonspamtable");
            this.spamAddressFile = prop.getProperty("app.spamAddressFile");
            
        } catch (Exception ex) {
            Logger.getLogger(SFProperties.class.getName()).log(Level.SEVERE, null, ex);
            this.spamThreshold = 0.85f; //default threshold value
            this.defaultThreshold = 0.5f;
            this.username = "cse";
            this.password = "admin";
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(SFProperties.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the spamThreshold
     */
    public float getSpamThreshold() {
        return spamThreshold;
    }

    /**
     * @return the defaultThreshold
     */
    public float getDefaultThreshold() {
        return defaultThreshold;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the limitOfInterestingWords
     */
    public int getLimitOfInterestingWords() {
        return limitOfInterestingWords;
    }

    /**
     * @return the goodMailList
     */
    public String getGoodMailList() {
        return goodMailList;
    }

    /**
     * @return the spamMailList
     */
    public String getSpamMailList() {
        return spamMailList;
    }

    /**
     * @return the dbname
     */
    public String getDbname() {
        return dbname;
    }

    /**
     * @return the tablename_spam
     */
    public String getTablename_spam() {
        return tablename_spam;
    }

    /**
     * @return the tablename_nonspam
     */
    public String getTablename_nonspam() {
        return tablename_nonspam;
    }

    /**
     * @return the spamAddressFile
     */
    public String getSpamAddressFile() {
        return spamAddressFile;
    }
    
}
