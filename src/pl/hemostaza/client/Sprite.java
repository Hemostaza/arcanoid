package pl.hemostaza.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

public class Sprite {
    //pozycja na planszy
    protected double x;
    protected double y;
    //wymiary sprita
    private int width;
    private int height;

    //obrazek
    private Image image;

//    public double getX() {
//        return x;
//    }
//    public double getY() {
//        return y;
//    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    //załadowanie grafiki
    public void loadImage(String src) {
        image = new Image(src);
    }

    //ustalenie wymiarów na podstawie grafiki
    void setDimensions() {
        width = image.getWidth();
        height = image.getHeight();
    }
    //wysweitlenie grafiki
    public void renderSprite(Context2d context2d) {
        context2d.drawImage(ImageElement.as(image.getElement()), x, y);
    }

}
