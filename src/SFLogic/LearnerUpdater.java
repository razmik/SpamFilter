/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SFLogic;

import SFBasic.SFProperties;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Rashmika
 */
public class LearnerUpdater {
    
    private SFProperties prop;

    public LearnerUpdater() {
        this.prop = new SFProperties();
    }
    
    public void updateKnoledgeMail(String content, boolean isSpam){
        if(isSpam){
            this.append(prop.getSpamMailList(), content);            
        }else{
            this.append(prop.getGoodMailList(), content);
        }
    }
    
    public void updateKnowledgeFile(String filename, boolean isSpam){
        
        String mailContent = this.readMail(filename);
        
        if(isSpam){
            this.append(prop.getSpamMailList(), mailContent);            
        }else{
            this.append(prop.getGoodMailList(), mailContent);
        }
        
    }
    
    private void append(String fileName, String content){
        
        content = "\n\n"+ content;
        
        try{
            FileWriter fstream = new FileWriter(fileName,true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.append(content);
            out.close();
        }catch (IOException e){
            System.err.println("Error: " + e.getMessage());
        }
        
    }
    
    private String readMail(String filename){
          String content = "";
          
          try{
              FileInputStream fstream = new FileInputStream(filename);
              DataInputStream in = new DataInputStream(fstream);
              BufferedReader br = new BufferedReader(new InputStreamReader(in));
              String strLine;
              //Read File Line By Line
              while ((strLine = br.readLine()) != null)   {
                content += strLine;
              }
              in.close();
              return content;
          }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
          }
          return content;
    }
    
}
