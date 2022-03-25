package pl.hemostaza.client;

import com.google.gwt.canvas.dom.client.Context2d;

public class Arcanoid {
    private int xBound, yBound;

    Player player;

    private GamePrinter printer;

    private Context2d graphicContext;

    public Arcanoid(int xBound, int yBound, Context2d graphicContext) {
        this.xBound = xBound;
        this.yBound = yBound;
        this.graphicContext = graphicContext;
    }

    public int getxBound() {
        return xBound;
    }

    public int getyBound() {
        return yBound;
    }
}
