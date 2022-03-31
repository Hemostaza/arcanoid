package pl.hemostaza.client;

import com.google.gwt.event.dom.client.*;

public class Paddle extends Sprite {
    private int dx;

    public Paddle() {
        loadImage("mywebapp/paddle.png");
        setDimensions();
        resetState();
    }

    private void resetState() {
        x = Commons.INIT_PLAYER_X;
        y = Commons.INIT_PLAYER_Y;
    }

    public void move() {
        x += dx * 2;
        if (x <= 0) {
            x = 0;
        }
        if (x >= Commons.WIDTH - image.getWidth()) {
            x = Commons.WIDTH - image.getWidth();
        }
    }

    void keyPressed(KeyDownEvent event) {
        int key = event.getNativeKeyCode();
        if (key == KeyCodes.KEY_LEFT) {
            dx = -1;
        }
        if (key == KeyCodes.KEY_RIGHT) {
            dx = 1;
        }
    }

    void keyReleased(KeyUpEvent event) {
        int key = event.getNativeKeyCode();
        if (key == KeyCodes.KEY_LEFT) {
            dx = 0;
        }
        if (key == KeyCodes.KEY_RIGHT) {
            dx = 0;
        }
    }

    void moveToMouse(MouseMoveEvent event){
        x = event.getX()-image.getWidth()/2;
    }

}
