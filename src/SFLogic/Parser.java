/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SFLogic;

import SFBasic.Email;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rashmika
 */
public class Parser {
    
    private String splitregex;

    public Parser() {
        this.splitregex = "\\W";;
    }
    
    public String[] getWordTokensOfEmail(Email mail){
        String divider = "\n";
        String content = "\n" + mail.getFrom() + divider + mail.getTo() + 
                            divider + mail.getCc() + divider + mail.getBcc() + 
                            divider + mail.getMessage();
        String[] tokens = content.split(splitregex);
        return tokens;
        
    }
    
    public String getCOntentofEmail(Email mail){
        String divider = "\n";
        String content = "\n" + mail.getFrom() + divider + mail.getTo() + 
                            divider + mail.getCc() + divider + mail.getBcc() + 
                            divider + mail.getMessage();
        return content;
    }
    
    public String[] getWordTokensOfFile(String fileName){
        String content = this.read(fileName);
        String[] tokens = content.split(splitregex);
        return tokens;
    }
    
    public HashMap getSpamAddresses(String fileName){
        
        HashMap spamAddr = new HashMap();
        String[] temp ;
        
        try{
            
          FileInputStream fstream = new FileInputStream(fileName);
          
          DataInputStream in = new DataInputStream(fstream);
          BufferedReader br = new BufferedReader(new InputStreamReader(in));
          String strLine;
          
          while ((strLine = br.readLine()) != null)   {
              temp = strLine.split(" ");
              spamAddr.put(temp[0], new SpammerAccount(temp[0], Integer.parseInt(temp[1])));
          }
          in.close();
          return spamAddr;
        }catch (Exception e){
          System.err.println("Error: " + e.getMessage());
        }
        
        return null;
    }
    
    public void addLine(String fileName, String line){
        try{
            
          FileWriter fstream = new FileWriter(fileName,true);
          BufferedWriter out = new BufferedWriter(fstream);
          out.write(line);
          out.close();
        }catch (Exception e){
          System.err.println("Error: " + e.getMessage());
        }
    }
    
    private String read(String fileName){
        
        FileInputStream fis = null;
        String content = "";
        
        try {
            fis = new FileInputStream(fileName);
            FileChannel fc = fis.getChannel();
            
            ByteBuffer bb = ByteBuffer.allocate((int)fc.size());
            fc.read(bb);
            fc.close();
            
            content = new String(bb.array());
            
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return content;
        
    }
    
}
