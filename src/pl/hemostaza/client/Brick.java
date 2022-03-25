package pl.hemostaza.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.ui.Image;

public class Brick extends Sprite{

    private boolean destroyed;
    int hp;
    private double maxHp;

    public Brick(int x, int y, int hp){
        createBrick(x,y,hp);
    }

    private void createBrick(int x, int y, int hp) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        maxHp=hp;
        destroyed = false;

        loadImage();
        getImageDimensions();
    }

    private void loadImage() {
        Image image = new Image("mywebapp/brick.png");
        img = image;
    }

    void damage(){
        hp-=1;
        if(hp==0)setDestroyed(true);
    }

    boolean isDestroyed(){
        return destroyed;
    }

    void setDestroyed(boolean value){
        destroyed=value;
    }

//    public boolean isColliding(Sprite other) {
//        //if(isDestroyed())return false;
//        //else super.isColliding(other);
//        //return false;
//    }
    public void renderSprite(Context2d context2d){
        context2d.setGlobalAlpha(hp/maxHp);
        super.renderSprite(context2d);
    }

}
