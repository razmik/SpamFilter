/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SFLogic;

/**
 *
 * @author Rashmika
 */
public class Word {
    
        private String word;    // The String which is calculation
	private int nBad;       // Number of appearences in spam mails
	private int nGood;      // Number of appearences in genuin mails
	private float rBad;     // bad-count / total-words ratio
	private float rGood;    // good-count / total-words ratio
	private float pSpam;    // probability of being a spam word
        
	public Word(String word) {
            this.word = word;
            this.nBad = 0;
            this.nGood = 0;
            this.rBad = 0.0f;
            this.rGood = 0.0f;
            this.pSpam = 0.0f;
	}
	
	// Occurence of spam mail. Increase by 1
	public void countBad() {
            nBad++;
	}

	// Occurence of non-spam mail. Increase by 1
	public void countGood() {
            nGood++;
	}

	/**
         * Computer how often this word is bad
         */
	public void calcBadProb(int total) {
            if (total > 0) {
                rBad = nBad / (float) total;
            }
	}
	
	/*
         * Computer how often this word is good
         * 
         * I want to bias the probabilities slightly to avoid false positives, 
         * and by trial and error I've found that a good way to do it is 
         * to double all the numbers in good. - Paul Graham
         * 
         */
	public void calcGoodProb(int total) {
            if (total > 0) {
                rGood = 2*nGood / (float) total;
            }
	}

	/*
         * According to Bayes theoram, calculate the probability 
         * of the word being an spam
         */
	public void completeProb() {
		
            //calculate the spam probablity
            if (rGood + rBad > 0) {
                pSpam = rBad / (rBad + rGood);//P(Spam|Word)
            }
            
            //moderating the spam probability
            if (pSpam < 0.01f) {
                pSpam = 0.01f;
            }else if (pSpam > 0.99f) {
                pSpam = 0.99f;
            }
	}

	
	/**
         * Gives the variance from 0.5f
         *
         * @return float 
         */
	public float interestRate() {
		return Math.abs(pSpam - 0.5f);
	}
	
		
	public float getPGood() {
		return rGood;
	}

	public float getPBad() {
		return rBad;
	}
	
	public float getPSpam() {
		return pSpam;
	}

	public void setPSpam(float f) {
		pSpam = f;
	}

	public String getWord() {
		return word;
	}
	
}
