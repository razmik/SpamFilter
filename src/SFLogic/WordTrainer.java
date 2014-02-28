/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SFLogic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Rashmika
 */
public class WordTrainer {
    
    private HashMap words;
    private Pattern wordregex;

    public WordTrainer() {
        this.words = new HashMap();
        this.wordregex = Pattern.compile("\\w+");
    }
    
    /**
     * Train the words in genuin mails
     * @param tokens 
     */
    public void trainGood(String[] tokens){
        
        String wordstr;
        int total = 0;
        
        for(int i=0; i<tokens.length; i++){            
            wordstr = tokens[i].toLowerCase();
            Matcher match = this.wordregex.matcher(wordstr);            
            if(match.matches()){
                total++;               
                if (words.containsKey(wordstr)) {
                        Word wrd = (Word) words.get(wordstr);
                        wrd.countGood();
                } else {
                        Word wrd = new Word(wordstr);
                        wrd.countGood();
                        words.put(wordstr,wrd);
                }
            }            
        }
        
        //for each word calculate the probability of being a good word
        Iterator iterator = words.values().iterator();
        while (iterator.hasNext()) {
                Word word = (Word) iterator.next();
                word.calcGoodProb(total);
        }
    }
    /**
     * Train the words in spam mails
     * @param tokens 
     */
    public void trainBad(String[] tokens){
        
        String wordstr;
        int total = 0;
        
        for(int i=0; i<tokens.length; i++){            
            wordstr = tokens[i].toLowerCase();
            Matcher match = this.wordregex.matcher(wordstr);            
            if(match.matches()){
                total++;               
                if (words.containsKey(wordstr)) {
                        Word wrd = (Word) words.get(wordstr);
                        wrd.countBad();
                } else {
                        Word wrd = new Word(wordstr);
                        wrd.countBad();
                        words.put(wordstr,wrd);
                }
            }            
        }
        
        //for each word calculate the probability of being a good word
        Iterator iterator = words.values().iterator();
        while (iterator.hasNext()) {
                Word word = (Word) iterator.next();
                word.calcBadProb(total);
        }
    }
    
    /**
     * After setting the good and bad counts finalizing the ratios
     */
    public void completeTraining(){
        Iterator iterator = words.values().iterator();
        while (iterator.hasNext()) {
                Word word = (Word) iterator.next();
                word.completeProb();                        
        }
    }
    
    /**
     * get the word list
     * @return 
     */
    public HashMap getKnowledgeBase(){
        return words;
    }
    
}
