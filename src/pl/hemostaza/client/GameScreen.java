package pl.hemostaza.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class GameScreen implements EntryPoint {

    private static final int CANVAS_WIDTH = 440;
    private static final int CANVAS_HEIGHT = 780;

    //private Timer timer;
    private String message = "Game over";
    private Ball ball;
    private Paddle paddle;
    private Brick brick; //poozniej tablica


    private boolean inGame = true;

    private Canvas board;
    private Context2d context2d;

    ImageElement ballImage;
    ImageElement paddleImage;
    ImageElement brickImage;
Image    brickImage1;


    @Override
    public void onModuleLoad() {
        final TextBox nameField = new TextBox();
        RootPanel.get("gameScreen").add(nameField);
        board = Canvas.createIfSupported();
        context2d = board.getContext2d();
        board.setCoordinateSpaceWidth(Commons.WIDTH);
        board.setCoordinateSpaceHeight(Commons.HEIGHT);
        RootPanel.get("gameScreen").add(board);
        gameStart();
        //RootPanel.get("gameScreen").add(brick.getImg());

    }

    private FocusPanel createBoard() {
        FocusPanel board = new FocusPanel();
        board.setFocus(true);
        board.setSize("" + Commons.WIDTH, "" + Commons.HEIGHT);

        return board;
        //Canvas c = Canvas.createIfSupported();
        //c.setCoordinateSpaceWidth(Commons.WIDTH);
        //c.setCoordinateSpaceHeight(Commons.HEIGHT);

        //gameStart();
    }

    private void gameStart() {
        brick = new Brick(10, 10,3);
        ball = new Ball();
        paddle = new Paddle();
        ballImage = ImageElement.as(ball.getImg().getElement());
        paddleImage = ImageElement.as(paddle.getImg().getElement());
        brickImage = ImageElement.as(brick.getImg().getElement());

        //pętla na cegiełki
        //timer = new Timer(Commons.SPEEED, new GameCycle());
        Timer gameLoop = new Timer() {
            @Override
            public void run() {
                input();
                update();
                render();
            }
        };
        gameLoop.scheduleRepeating(1);
    }

    void input() {
        board.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent keyDownEvent) {
                paddle.keyPressed(keyDownEvent);
            }
        });
        board.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent keyUpEvent) {
                paddle.keyReleased(keyUpEvent);
            }
        });
    }

    public void renderContext(Context2d context2d) {
        if (inGame) {
            render();
        } else {
            gameFinished(context2d);
        }
    }

    private void render() {
        context2d.clearRect(0, 0, Commons.WIDTH, Commons.HEIGHT);
        //context.drawImage(ballImage, ball.getX(), ball.getY());
        //context.drawImage(paddleImage, paddle.getX(), paddle.getY());
        brick.renderSprite(context2d);
        context2d.setGlobalAlpha(1);
        ball.renderSprite(context2d);
        paddle.renderSprite(context2d);
        //if(!brick.isDestroyed())context.drawImage(brickImage, brick.getX(), brick.getY());

        //context.fillRect(ballCol[0],ballCol[1],ballCol[2],ballCol[3]);
        //board.add(ball.getImg());
        //board.add(paddle.getImg());
        //board.add(brick.getImg());
    }

    private void gameFinished(Context2d context2d) {
        context2d.fillText(message, 5, 10);
    }

    private void update() {
        ball.move();
        paddle.move();
        checkCollision();
    }

    public void stopGame() {
        inGame = false;
        //timer stop
    }

    private void checkCollision() {

        //double[] ballCol = ball.getCollision();
        //double[] paddleCol = paddle.getCollision();
        //double[] brickCol = brick.getCollision();
        //0 x| 1 x1| 2 y| 3 y1|
        //ball.Collisions();
        //kolizja pilki z paletka od gory

        if (ball.isColliding(paddle)){// || ball.isColliding(brick)) {

            ball.collisions();
            ball.calculateRelativePos(paddle);
            //double paddleCentrePosX = paddle.getX() + paddle.getImgWidth()/2; //srodek kolizji paletki X
            //double paddleCentrePosY = paddle.getY() + paddle.getImgHeight()/2; //srodek kolizji paletki X
            //double ballCentrePosX = ball.getX() + ball.getImgWidth()/2; //srodek pileczki X
            //double ballCentrePosY = ball.getY() + ball.getImgHeight()/2; //srodek pileczki X
            //double balRelX = ballCentrePosX - paddleCentrePosX;
            //double balRelY = ballCentrePosY - paddleCentrePosY;

            //tylko obliczyc jak daleko od srodka i wyjebac na podstawie tego kat... IZI PIZI LEMON SQUIZ

        }


        if(ball.isColliding(brick) && !brick.isDestroyed())
        {
            if(ball.colHorizontal)ball.setXDir(ball.getXdir()*-1);
            if(!ball.colHorizontal)ball.setYDir(ball.getYDir()*-1);
            brick.damage();
        }
//        if(ball.isColliding(brick) && !brick.isDestroyed())
//        {
//            ball.collisions();
//            brick.damage();
//        }

    }
}
