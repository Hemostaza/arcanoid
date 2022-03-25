package pl.hemostaza.client;

public class Player {
    private double playerPosX;
    private int playerWidth;

    public Player(double playerPosX, int playerWidth) {
        this.playerPosX = playerPosX;
        this.playerWidth = playerWidth;
    }

    public double getPlayerPosX() {
        return playerPosX;
    }

    public int getPlayerWidth() {
        return playerWidth;
    }

    public void moveRight() {
        this.playerPosX = playerPosX+1f;
    }
}
