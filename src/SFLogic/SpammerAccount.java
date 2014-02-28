/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SFLogic;

/**
 *
 * @author Rashmika
 */
public class SpammerAccount {
    
    private String address;
    private int count;

    public SpammerAccount(String address, int count) {
        this.address = address;
        this.count = count;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }
    
    
    
}
