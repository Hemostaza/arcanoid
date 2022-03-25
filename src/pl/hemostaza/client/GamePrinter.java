package pl.hemostaza.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.touch.client.Point;

public class GamePrinter {
    private Context2d graphicsContext;
    private int pointSize;

    public GamePrinter(Context2d graphicContext, int pointSize) {
        this.graphicsContext = graphicContext;
        this.pointSize = pointSize;
    }

    private void drawPoint(Point point, int value){

        graphicsContext.setFillStyle("red"); //zmienic na pobeiranie koloru z value
        graphicsContext.fillRect(point.getX()*pointSize,point.getY()*pointSize,pointSize,pointSize);
    }

    public void print(Arcanoid game,Player player) {
        //int boardWidth = game.getxBound() * pointSize;
        //int boardHeight = game.getyBound() * pointSize;
        //graphicsContext.clearRect(0, 0, boardWidth, boardHeight);
        //rysuj gracza
        drawPlayer(player);

    }

    private void drawPlayer(Player player) {
        graphicsContext.fillRect(player.getPlayerPosX()-(player.getPlayerWidth()/2), 600,player.getPlayerWidth(),20);
    }
}
