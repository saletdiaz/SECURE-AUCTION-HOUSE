package model;

import java.io.Serializable;

/**Representa el estado actual de la subasta transmitida por el servidor*/
public class GameStatus implements Serializable {
    private String message;
    private AuctionItem curretItem;
    private double currentHighestBid;
    private String winnerName;
    public GameStatus(String message, AuctionItem curretItem, double currentHighestBid, String winnerName) {
        this.message = message;
        this.curretItem = curretItem;
        this.currentHighestBid = currentHighestBid;
        this.winnerName = winnerName;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AuctionItem getCurretItem() {
        return curretItem;
    }

    public void setCurretItem(AuctionItem curretItem) {
        this.curretItem = curretItem;
    }

    public double getCurrentHighestBid() {
        return currentHighestBid;
    }

    public void setCurrentHighestBid(double currentHighestBid) {
        this.currentHighestBid = currentHighestBid;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }
}
