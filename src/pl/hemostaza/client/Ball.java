package pl.hemostaza.client;

import com.google.gwt.media.client.Audio;

public class Ball extends Sprite {
    private double xdir;
    private double ydir;
    private double speed;

    Audio collideAudio;
    Audio colOther;

    boolean colInHorizontal;

    public Ball() {
        xdir = 0;
        ydir = 0;
        colInHorizontal=false;
        collideAudio = Audio.createIfSupported();
        colOther = Audio.createIfSupported();

        loadImage("mywebapp/ball.png");
        setDimensions();
        resetState();
    }

    void move() {
        x += speed * xdir;
        y += speed * ydir;
    }

    public void setYDir(double y) {
        ydir = y;
    }

    public void setXDir(double x) {
        xdir = x;
    }

    double getYDir() {
        return ydir;
    }

    double getXdir() {
        return xdir;
    }

    public void resetState() {
        xdir=0;
        ydir=0;
        x = Commons.INIT_BALL_X;
        y = Commons.INIT_BALL_Y;
    }

    public void isColliding() {
        if (x<= 0 || x>= Commons.WIDTH - getWidth()) {
            setXDir(xdir * -1);
            colOther.setSrc("mywebapp/ColBrick.wav");
            colOther.play();
        }
        if (y <= 0) {
            setYDir(getYDir() * -1);
            colOther.setSrc("mywebapp/ColBrick.wav");
            colOther.play();
        }
    }

    public boolean isColliding(Sprite other) {

        if (x + getWidth() + xdir *speed > other.x && x + xdir*speed < other.x + other.getWidth()
                && y + getHeight() > other.y && y < other.y + other.getHeight()) {
            colInHorizontal=true;
            return true;
        }
        if (x + getWidth() > other.x && x < other.x + other.getWidth()
                && y + getHeight() + ydir*speed > other.y && y + ydir *speed< other.y +  other.getHeight()) {
            colInHorizontal=false;
            return true;
        }
        return false;

    }

    public void calculateCollisions(Paddle other) {

        double otherCenterPosX = other.getX() + other.getWidth() / 2;
        double thisCenterPosX = getX() + getWidth() / 2;
        double relativeX = thisCenterPosX - otherCenterPosX;

        double xDir = ( relativeX / (other.getWidth()*0.5));
        setXDir(xDir);
        ydir *= -1;
        collideAudio.setSrc("mywebapp/colPaddle.wav");
        collideAudio.play();
    }

    public void bounce() {
        if(colInHorizontal)xdir*=-1;
        else ydir*=-1;
        collideAudio.setSrc("mywebapp/ColBrick.wav");
        collideAudio.play();
    }

    void setSpeed(int speed){
        this.speed=speed;
    }

}
