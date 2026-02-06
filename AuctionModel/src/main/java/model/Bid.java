package model;


import java.io.Serializable;

/**Representa una oferta de un usuario*/
public class Bid implements Serializable {
    private int itemId;
    private String bidderName;
    private double amount;
    private boolean isWinning;
    public Bid(int itemId, String bidderName, double amount) {
        this.itemId = itemId;
        this.bidderName = bidderName;
        this.amount = amount;
        this.isWinning = false;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getBidderName() {
        return bidderName;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isWinning() {
        return isWinning;
    }

    public void setWinning(boolean winning) {
        isWinning = winning;
    }
}
