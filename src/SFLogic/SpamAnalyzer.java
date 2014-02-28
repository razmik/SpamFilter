/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SFLogic;

import SFBasic.Email;
import SFBasic.SFProperties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The steps of Baysian Filter
 * 1. Incoming message (e-mail)
 * 2. Message tokenization
 * 3. Probability estimation
 * 4. Feature selection
 * 5. Decide by Naive Bayesian classifier
 * 
 * @author Rashmika
 */
public class SpamAnalyzer {
    
    private HashMap knowledgeBase;
    private HashMap spammerAdresses;
    private Parser parser;
    private Pattern wordregex;
    private int limitOfInterestingWords;
    private float spamThreshold;
    private float defaultThreshold;
    private float PSpamMail;
    private LearnerUpdater updater;

    public SpamAnalyzer() {
        this.parser = new Parser();
	this.wordregex = Pattern.compile("\\w+");
        this.updater = new LearnerUpdater();
        this.setup();
    }
    
    /**
     * Setting up the SPam Analyser before in use
     */
    private void setup(){
        
        //set the properties from configuration file
        SFProperties prop = new SFProperties();
        this.spamThreshold = prop.getSpamThreshold();
        this.limitOfInterestingWords = prop.getLimitOfInterestingWords();
        this.defaultThreshold = prop.getDefaultThreshold();
        
        //set the filenames of good and bad word list from the relavent files
        String[] goodTokens = parser.getWordTokensOfFile(prop.getGoodMailList());
        String[] badTokens = parser.getWordTokensOfFile(prop.getSpamMailList());
        
        //Train the knowledge base with words
        WordTrainer trainer = new WordTrainer();
        trainer.trainGood(goodTokens);
        trainer.trainBad(badTokens);
        trainer.completeTraining();
        
        this.knowledgeBase = trainer.getKnowledgeBase();
        
        this.spammerAdresses = parser.getSpamAddresses(prop.getSpamAddressFile());
        
    }
    
    /**
     * Analyse a mail and return true if its a spam mail
     * @param mail
     * @return 
     */
    public boolean analyseMail(Email mail){
        
        //Break the new mail into words
        String[] mailTokens = parser.getWordTokensOfEmail(mail);
        String mailContent = parser.getCOntentofEmail(mail);
        boolean isSpam = this.analyze(mailTokens);
        boolean fromSpammer = false;//this.checkForSpammers(mail); 
        
        //after analysing update the knowledge with the words in the mail
        this.learnMail(mailContent, isSpam);
        
        if(isSpam || fromSpammer){
            this.addSpammer(mail);
            return true;
        }
        
        return isSpam;
        
    }
    
    /**
     * Adding a spammer into the content
     * @param mail 
     */
    private void addSpammer(Email mail){
        SFProperties prop;
        if(!this.spammerAdresses.containsKey(mail.getFrom())){
            this.spammerAdresses.put(mail.getFrom(), new SpammerAccount(mail.getFrom(), 1));
            prop = new SFProperties();
            this.parser.addLine("\r\n"+prop.getSpamAddressFile(), mail.getFrom()+" "+1);
        }
    }
    
    /**
     * Cheking is the sender address is in the spammer database
     * @param mail 
     */
    private boolean checkForSpammers(Email mail){
        
        //If in the spammer db, add +1
        //If spammer count is >3 make it spam
        if(this.spammerAdresses.containsKey(mail.getFrom())){
            //System.out.println(""+this.spammerAdresses.get(mail.getFrom()));
            SpammerAccount spammer = (SpammerAccount) this.spammerAdresses.get(mail.getFrom());
            
            if(spammer.getCount()>0){
                mail.setPoints(1);
                return true;
            }else{
                spammer.setCount(spammer.getCount()+1);
                this.addSpammer(mail);
            }
        }
        
        return false;
        
    }
    
    /**
     * Analyse the mail and result if a spam or not
     * @param filename of the email
     * @return Spam -> True
     */
    public boolean analyseFile(String filename){
        
        String[] mailTokens = parser.getWordTokensOfFile(filename);
        boolean isSpam = this.analyze(mailTokens);
        
        //after analysing update the knowledge with the words in the mail
        this.learnFile(filename, isSpam);
        
        return isSpam;
    }
    
    /**
     * Analyse a set of strin gand return true if its spam
     * The Naive Baysian Logic is placed in this method
     * 
     * Interesting words; max(|threshold-spamicity|)
     * 
     * @param mailTokens
     * @return 
     */
    private boolean analyze(String[] mailTokens){
        
        ArrayList<Word> interestList = new ArrayList<Word>();
        String wrdstr;
        Word word;
        
        for(int i=0; i<mailTokens.length; i++){
            
            wrdstr = mailTokens[i].toLowerCase();
            Matcher m = this.wordregex.matcher(wrdstr);
            
            if(m.matches()){
                //Get the word w.r.t the token word
                if(knowledgeBase.containsKey(wrdstr)){
                    word = (Word) this.knowledgeBase.get(wrdstr);
                }else{
                    word = new Word(wrdstr);
                    word.setPSpam(this.defaultThreshold);
                }
                
                //check the interseting rate
                if(interestList.isEmpty()){
                    interestList.add(word);
                }else{
                    for (int j = 0; j < interestList.size(); j++) {
                        
                        Word curWord = (Word) interestList.get(j);
                        
                        if (word.getWord().equals(curWord.getWord())) {
                            break;
                        } else if (word.interestRate() > curWord.interestRate()) {
                                interestList.add(j,word);
                                break;
                        } else if (j == interestList.size()-1) {//if last word is reached, then add it into the list (below 15)
                                interestList.add(word);
                        }
                    }
                }
            }          
            
        }
        
        // Apply Bayes' Probability function developmed by Paul Graham
        float prodNominator = 1.0f;
        float prodDenominator = 1.0f;
        
        int minCount = Math.min(this.limitOfInterestingWords, interestList.size()); 

        //Calculating probability of spam using PSpam of interesting worsd
        /**
         * Naive assumptions are used to calculate P(S|W1,W2......,W15)
         * p1p2..p15/(p1p2...p15+ (1-p1).(1-p2)....(1-p15))
         * pn=p(Spam|Wordn)
         */
        for (int i = 0; i < minCount; i++) {
            
                Word wrd = (Word) interestList.get(i);
                //System.out.println(wrd.getWord()+" "+wrd.getPBad()+" "+wrd.getPSpam());
                prodNominator *= wrd.getPSpam();
                prodDenominator *= (1.0f - wrd.getPSpam());
                
        }
        
        this.PSpamMail = prodNominator / (prodNominator + prodDenominator);
        
        // If the computed value is great than spamThreshold, we have a Spam!!
        if (this.PSpamMail > this.spamThreshold) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * @return the PSpamMail
     */
    public float getPSpamMail() {
        return PSpamMail;
    }
    
    /**
     * Update the knowledge base
     * @param filename
     * @param isSpam 
     */
    private void learnFile(String filename, boolean isSpam){
        updater.updateKnowledgeFile(filename, isSpam);
    }
    
    /**
     * Update the knowledge base
     * @param content
     * @param isSpam 
     */
    private void learnMail(String content, boolean isSpam){
        updater.updateKnoledgeMail(content, isSpam);
    }
    
}
