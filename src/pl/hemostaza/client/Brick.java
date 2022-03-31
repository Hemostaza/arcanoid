package pl.hemostaza.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.media.client.Audio;

public class Brick extends Sprite {

    private boolean destroyed;
    int hp;
    private int maxHp;
    String color;

    Audio destroyedAudio;

    public Brick(int x, int y, int hp) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        maxHp = hp;
        if (maxHp == 3) {
            color = "Red";
        } else if (maxHp == 2) {
            color = "Blue";
        } else {
            color = "Yellow";
        }
        destroyed = false;
        destroyedAudio = Audio.createIfSupported();
        loadImage("mywebapp/brick.png");
        setDimensions();
    }

    void damage() {
        hp -= 1;
        if (hp == 0) setDestroyed(true);
    }

    boolean isDestroyed() {
        return destroyed;
    }

    void setDestroyed(boolean value) {
        destroyedAudio.setSrc("mywebapp/brickDest.wav");
        destroyedAudio.play();
        destroyed = value;
    }

    public void renderSprite(Context2d context2d) {
        super.renderSprite(context2d);
        context2d.setGlobalAlpha((double) hp / maxHp * 0.5);
        context2d.setFillStyle(color);
        context2d.fillRect(x + 1, y + 1, getWidth() - 1, getHeight() - 1);
        context2d.setGlobalAlpha(1);
    }

}
