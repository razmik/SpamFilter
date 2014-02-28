/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import SFLogic.SpamAnalyzer;

/**
 *
 * @author Rashmika
 */
public class TestLogic {
    
    private static long start;
    private static long end;
    private static long total1, total2;
    private static long avg;
    
    public static void main(String[] args) {
        
        SpamAnalyzer main = new SpamAnalyzer();
        TestSpam(main);
        total1 = end -start;
        
        Testlegitimate(main);
        total2 = end -start;
        
        avg = (total1+total2)/(20);
        System.out.println("Performance: "+avg/10000+" ms");
        
    }
    
    public static void TestSpam(SpamAnalyzer analyser){
        String filepath1 = "TestMail/spam/s";
        String filepath2 = ".txt";
        boolean isSpam = false;
        float spamicity;
        System.out.println("*******Start Testing Spam*******");
        start = System.nanoTime();
        for(int i=1; i<11; i++){
            isSpam = analyser.analyseFile(filepath1+i+filepath2);
            spamicity = analyser.getPSpamMail();
            System.out.println("\nMail Number: "+i+"\nSpamicity: "+spamicity+"\nIs Spam: "+isSpam);
        }
        end = System.nanoTime();
        System.out.println("*******End Testing Spam*******\n\n");
    }
    
    public static void Testlegitimate(SpamAnalyzer analyser){
        String filepath1 = "TestMail/good/g";
        String filepath2 = ".txt";
        boolean isSpam = false;
        float spamicity;
        System.out.println("*******Start Testing Legitimate*******");
        start = System.nanoTime();
        for(int i=1; i<11; i++){
            isSpam = analyser.analyseFile(filepath1+i+filepath2);
            spamicity = analyser.getPSpamMail();
            System.out.println("\nMail Number: "+i+"\nSpamicity: "+spamicity+"\nIs Spam: "+isSpam);
        }
        end = System.nanoTime();
        System.out.println("*******End Testing Legitimate*******\n\n");
    }
    
}
