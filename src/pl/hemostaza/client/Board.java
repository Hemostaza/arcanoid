package pl.hemostaza.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.FocusPanel;


public class Board extends FocusPanel{

    //private Timer timer;
    private String message = "Game over";
    private Ball ball;
    private Paddle paddle;
    private Brick brick; //poozniej tablica


    private boolean inGame = true;

    public Board(){
        createBoard();
    }

    private void createBoard() {
        setFocus(true);
        setSize(""+Commons.WIDTH,""+Commons.HEIGHT);

        //Canvas c = Canvas.createIfSupported();
        //c.setCoordinateSpaceWidth(Commons.WIDTH);
        //c.setCoordinateSpaceHeight(Commons.HEIGHT);

        gameStart();
    }

    private void gameStart() {
        brick = new Brick(10,10,3);
        ball = new Ball();
        paddle = new Paddle();

        //pętla na cegiełki
        //timer = new Timer(Commons.SPEEED, new GameCycle());
        while(true){
            input();
            update();
            render();
        }
    }

    void input(){
        addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent keyDownEvent) {
                paddle.keyPressed(keyDownEvent);
            }
        });
        addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent keyUpEvent) {
                paddle.keyReleased(keyUpEvent);
            }
        });
    }

    public void renderContext(Context2d context2d){
        if (inGame) {
            render();
        }else
        {
            gameFinished(context2d);
        }
    }

    private void render() {
        add(ball.getImg());
        add(paddle.getImg());
        add(brick.getImg());
    }
    private void gameFinished(Context2d context2d){
        context2d.fillText(message,5,10);
    }

    private void update(){
        ball.move();
        paddle.move();
        checkCollision();
    }

    public void stopGame(){
        inGame=false;
        //timer stop
    }

    private void checkCollision(){

    }

}
