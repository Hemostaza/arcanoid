package pl.hemostaza.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;

public class Game {

    private static final int POINT_SIZE = 20;

    private Context2d graphicContext;
    private Arcanoid arcanoid;
    private Player player;
    private GamePrinter gp;

    public Game(Context2d graphicContext) {
        this.graphicContext = graphicContext;
        //int xBound = CANVAS_WIDTH/POINT_SIZE;
        //int yBound = CANVAS_HEIGHT/POINT_SIZE;
        player = new Player(220 / 2 -(3/2),120);
        //arcanoid = new Arcanoid(420,700,graphicContext);
        gp = new GamePrinter(graphicContext,20);
        //gp.print(arcanoid,player);
    }

    public void update() {
        player.moveRight();
    }

    public void render() {
        gp.print(arcanoid,player);
    }
}
