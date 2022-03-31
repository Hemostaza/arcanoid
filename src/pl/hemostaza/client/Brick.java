package pl.hemostaza.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.media.client.Audio;

public class Brick extends Sprite {

    private boolean destroyed; //czy zniszczona
    private int hp; //aktualne punkty życia cegiełki
    private int maxHp; //maksymalne punkty życia cegiełki
    private String color;//color cegiełki

    private Audio destroyedAudio;

    public Brick(int x, int y, int hp) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        maxHp = hp;
        //ustalenie jaki kolor ma cegiełka
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

    //uszkodzenie cegiełki i jej zniszczenie jeżeli punkty życia osiągną 0
    public void damage() {
        hp -= 1;
        if (hp == 0) setDestroyed(true);
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    private void setDestroyed(boolean value) {
        destroyedAudio.setSrc("mywebapp/brickDest.wav");
        destroyedAudio.play();
        destroyed = value;
    }

    public void renderSprite(Context2d context2d) {
        //wyrenderowanie głównej grafiki
        super.renderSprite(context2d);
        //ustalenie stylu rysowanego prostokąta
        context2d.setGlobalAlpha((double) hp / maxHp * 0.5); //transparentność przykrywajacego prostokata
        context2d.setFillStyle(color); //kolor przykrywającego prostokąta
        context2d.fillRect(x + 1, y + 1, getWidth() - 1, getHeight() - 1); //przykrywający prostokąt na wcześniej wyrenderowanej cegiełce
        context2d.setGlobalAlpha(1); //powrot do braku transprantności
    }

}
