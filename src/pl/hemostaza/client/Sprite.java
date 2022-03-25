package pl.hemostaza.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

public class Sprite {
    double x;
    double y;
    private int imgWidth;
    private int imgHeight;
//
//    double relativeX;
//    double relativeY;
//    boolean colHorizontal;
    Image img;

    protected void setX(int x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public double getRightX() {
        return x + imgWidth;
    }

    protected void setY(int y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public double getDownY() {
        return y + imgHeight;
    }

    public double getImgWidth() {
        return imgWidth;
    }

    public double getImgHeight() {
        return imgHeight;
    }

    public Image getImg() {
        return img;
    }

    void getImageDimensions() {
        imgWidth = img.getWidth();
        imgHeight = img.getHeight();
    }

    public double[] getCollision() {
        return new double[]{x, x + imgWidth, y, y + imgWidth};
    }

//    public boolean isColliding(Sprite other) {
//
////        double[] otherCol = other.getCollision();
////        double[] thisCol = this.getCollision();
////        double colPosX; //srodek kolizji kurwa chuj wi
////        double colPosY;
//        //górna x dolna
//        //if((thisCol[0]>=otherCol[0] && thisCol[1]<=otherCol[1] || thisCol[0]<=otherCol[1])  && thisCol[3]>=otherCol[2]) {
//        //Jeżeli ten lewy x jest większy niz tamten lewy x i ten prawy x jest mniejszy niz
//
//        double otherCenterPosX = other.getX() + other.getImgWidth()/2;
//        double otherCenterPosY = other.getY() + other.getImgHeight()/2;
//        double thisCenterPosX = this.getX() + this.getImgWidth()/2;
//        double thisCenterPosY = this.getY() + this.getImgHeight()/2;
//        //trzeba to napsiac elpiej ale narazie chuj z tym
//        //kolizja w osi Y
//        if ((this.getDownY() >= other.y && this.y <= other.y)
//                || (this.y <= other.getDownY() && this.getDownY() >= other.getDownY())) {
//            //czy krawedzie this sa w other
//            if (this.x >= other.x && this.x <= other.getRightX()) {
//                relativeX = thisCenterPosX - otherCenterPosX;
//                relativeY = thisCenterPosY - otherCenterPosY;
//                other.relativeX = otherCenterPosX - thisCenterPosX;
//                other.relativeY = otherCenterPosY - thisCenterPosY;
//                colHorizontal=false;
//                return true;
//            }
//            if (this.getRightX() >= other.x && this.getRightX() <= other.getRightX()) {
//                relativeX = thisCenterPosX - otherCenterPosX;
//                relativeY = thisCenterPosY - otherCenterPosY;
//                other.relativeX = otherCenterPosX - thisCenterPosX;
//                other.relativeY = otherCenterPosY - thisCenterPosY;
//                colHorizontal=false;
//                return true;
//            }
//
//        }
//        //kolizja w osi X
//        if ((this.getRightX() >= other.x && this.x <= other.x)
//                || (this.x <= other.getRightX() && this.getRightX() >= other.getRightX())) {
//            if (this.y >= other.y && this.y <= other.getDownY()) {
//                relativeX = thisCenterPosX - otherCenterPosX;
//                relativeY = thisCenterPosY - otherCenterPosY;
//                other.relativeX = otherCenterPosX - thisCenterPosX;
//                other.relativeY = otherCenterPosY - thisCenterPosY;
//                colHorizontal=true;
//                return true;
//            }
//            if (this.getDownY() >= other.y && this.getDownY() <= other.getDownY()) {
//                relativeX = thisCenterPosX - otherCenterPosX;
//                relativeY = thisCenterPosY - otherCenterPosY;
//                other.relativeX = otherCenterPosX - thisCenterPosX;
//                other.relativeY = otherCenterPosY - thisCenterPosY;
//                colHorizontal=true;
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    public void collisions(Sprite other){
//
//        double otherCenterPosX = other.getX() + other.getImgWidth()/2; //srodek kolizji paletki X
//        double otherCenterPosY = other.getY() + other.getImgHeight()/2; //srodek kolizji paletki X
//        double thisCenterPosX = this.getX() + this.getImgWidth()/2; //srodek pileczki X
//        double thisCenterPosY = this.getY() + this.getImgHeight()/2; //srodek pileczki X
//        relativeX = thisCenterPosX - otherCenterPosX;
//        relativeY = thisCenterPosX - otherCenterPosX;
//    }

    public void renderSprite(Context2d context2d){
        //ImageElement img = ImageElement.as(getImg().getElement());
        context2d.drawImage(ImageElement.as(img.getElement()),x,y);
    }

}
