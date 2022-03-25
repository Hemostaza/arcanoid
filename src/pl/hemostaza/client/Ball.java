package pl.hemostaza.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

public class Ball extends Sprite {
    private double xdir;
    private double ydir;
    //private int speed;


    double relativeX;
    double relativeY;
    boolean colHorizontal;

    public Ball() {
        createBall();
    }

    private void createBall() {
        xdir = 1;
        ydir = -1;
        //speed = Commons.SPEEED;

        loadImage();
        getImageDimensions();
        resetState();
    }

    private void loadImage() {
        Image image = new Image("mywebapp/ball.png");
        img = image;
    }

    void move() {
        x += Commons.SPEEED * xdir;
        y += Commons.SPEEED * ydir;

        if (x <= 0) {
            setXDir(xdir * -1);
        }

        if (x >= Commons.WIDTH - getImgWidth()) {
            setXDir(xdir * -1);
        }

        if (y <= 0) {
            setYDir(getYDir() * -1);
        }
        if (y >= Commons.HEIGHT - getImgWidth()) {
            setYDir(getYDir() * -1);
        }
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

    private void resetState() {
        x = Commons.INIT_BALL_X;
        y = Commons.INIT_BALL_Y;
    }

    public boolean isColliding(Sprite other) {

//        double otherCenterPosX = other.getX() + other.getImgWidth()/2;
//        double otherCenterPosY = other.getY() + other.getImgHeight()/2;
//        double thisCenterPosX = this.getX() + this.getImgWidth()/2;
//        double thisCenterPosY = this.getY() + this.getImgHeight()/2;
        //trzeba to napsiac elpiej ale narazie chuj z tym
        //kolizja w osi Y
        if ((this.getDownY() >= other.y && this.y <= other.y)
                || (this.y <= other.getDownY() && this.getDownY() >= other.getDownY())) {
            colHorizontal = false;
            //czy krawedzie this sa w other
            if (this.x >= other.x && this.x <= other.getRightX()) {

                return true;
            }
            if (this.getRightX() >= other.x && this.getRightX() <= other.getRightX()) {

                return true;
            }

        }
        //kolizja w osi X
        if ((this.getRightX() >= other.x && this.x <= other.x)
                || (this.x <= other.getRightX() && this.getRightX() >= other.getRightX())) {
            colHorizontal = true;
            if (this.y >= other.y && this.y <= other.getDownY()) {

                return true;
            }
            if (this.getDownY() >= other.y && this.getDownY() <= other.getDownY()) {

                return true;
            }
        }

        return false;
    }

    public void calculateRelativePos(Sprite other) {
        double otherCenterPosX = other.getX() + other.getImgWidth() / 2;
        double otherCenterPosY = other.getY() + other.getImgHeight() / 2;
        double thisCenterPosX = this.getX() + this.getImgWidth() / 2;
        double thisCenterPosY = this.getY() + this.getImgHeight() / 2;
        relativeX = thisCenterPosX - otherCenterPosX;
        relativeY = thisCenterPosY - otherCenterPosY;
    }

    public void collisions() {
        double xDir = relativeX / getImgWidth();
        if (xDir > 1) xDir = 1;
        if (xDir < -1) xDir = -1;
        double yDir = relativeY / getImgHeight();
        if (yDir > 1) yDir = 1;
        if (yDir < -1) yDir = -1;
        setXDir(xDir);
        setYDir(yDir);
    }

    public void renderSprite(Context2d context2d) {
        //context2d.setGlobalAlpha(1);
        super.renderSprite(context2d);
    }

}
