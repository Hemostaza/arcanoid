package pl.hemostaza.client;

import com.google.gwt.event.dom.client.*;

public class Paddle extends Sprite {
    //zmiana w osi x
    private int dx;

    public Paddle() {
        loadImage("mywebapp/paddle.png");
        setDimensions();
        x = Commons.INIT_PLAYER_X;
        y = Commons.INIT_PLAYER_Y;
    }

    //ruch paletki
    public void move() {
        x += dx * 2;
        //jej prosta kolizja ze ścianą
        if (x <= 0) {
            x = 0;
        }
        if (x >= Commons.WIDTH - getWidth()) {
            x = Commons.WIDTH - getWidth();
        }
    }

    //wcisnięcie klawisza
    public void keyPressed(KeyDownEvent event) {
        int key = event.getNativeKeyCode();
        if (key == KeyCodes.KEY_LEFT) {
            dx = -1;
        }
        if (key == KeyCodes.KEY_RIGHT) {
            dx = 1;
        }
    }

    //puszczenie klawisza
    public void keyReleased(KeyUpEvent event) {
        int key = event.getNativeKeyCode();
        if (key == KeyCodes.KEY_LEFT) {
            dx = 0;
        }
        if (key == KeyCodes.KEY_RIGHT) {
            dx = 0;
        }
    }

    //ruch za myszą
    public void moveToMouse(MouseMoveEvent event){
        x = event.getX()-getWidth()/2;
    }

}
