package pl.hemostaza.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

public class Sprite {
    double x;
    double y;
    private int imgWidth;
    private int imgHeight;

    protected Image image;

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public Image getImg() {
        return image;
    }

    public void loadImage(String src){
        image = new Image(src);
    }

    void setDimensions() {
        imgWidth = image.getWidth();
        imgHeight = image.getHeight();
    }

    public void renderSprite(Context2d context2d) {
        context2d.drawImage(ImageElement.as(image.getElement()), x, y);
    }

}
