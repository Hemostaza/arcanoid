package pl.hemostaza.client;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.Image;

public class Paddle extends Sprite {
    private int dx;

    public Paddle() {
        createPaddle();
    }

    private void createPaddle() {
        loadImage();
        getImageDimensions();
        resetState();
    }

    private void resetState() {
        x = Commons.INIT_PLAYER_X;
        y = Commons.INIT_PLAYER_Y;
    }

    private void loadImage() {
        Image image = new Image("mywebapp/paddle.png");
        img = image;
    }

    public void move() {
        x += dx;
        if (x <= 0) {
            x = 0;
        }
        if (x >= Commons.WIDTH - img.getWidth()) {
            x = Commons.WIDTH - img.getWidth();
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

}
