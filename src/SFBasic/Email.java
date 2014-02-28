/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SFBasic;

/**
 * 
 * Email object
 *
 * @author Rashmika
 */
public class Email {
    
    private String to;
    private String from;
    private String bcc;
    private String cc;
    private String message;
    private double points;

    public Email() {
    }

    /**
     * 
     * Compose an Email with all the fields
     * 
     * @param to
     * @param from
     * @param bcc
     * @param cc
     * @param message 
     */
    public Email(String to, String from, String bcc, String cc, String message) {
        this.to = to;
        this.from = from;
        this.bcc = bcc;
        this.cc = cc;
        this.message = message;
        this.points = 0.4;
    }
    
    /**
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the bcc
     */
    public String getBcc() {
        return bcc;
    }

    /**
     * @param bcc the bcc to set
     */
    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    /**
     * @return the cc
     */
    public String getCc() {
        return cc;
    }

    /**
     * @param cc the cc to set
     */
    public void setCc(String cc) {
        this.cc = cc;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the points
     */
    public double getPoints() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(double points) {
        this.points = points;
    }

    
}
