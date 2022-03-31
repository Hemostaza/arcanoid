package pl.hemostaza.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;

import java.util.Date;

public class Arcanoid {

    Ball ball;
    Paddle paddle;
    Brick testBrick;
    Brick[] bricks;
    Canvas canvas;
    Context2d context2d;

    int lives;
    int destroyedBricks = 0;

    boolean inGame = false;
    boolean ballInGame = false;

    Timer gameLoop;

    Date lastDate;

    Image endScreen;

    int timeMilis;

    int ballSpeed;


    public Arcanoid(Canvas canvas) {

        this.canvas = canvas;
        context2d = canvas.getContext2d();
        canvas.addMouseMoveHandler(new MouseMoveHandler() {
            @Override
            public void onMouseMove(MouseMoveEvent mouseMoveEvent) {
                paddle.moveToMouse(mouseMoveEvent);
            }
        });

        canvas.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent keyDownEvent) {
                paddle.keyPressed(keyDownEvent);
                if (keyDownEvent.getNativeKeyCode() == 32) {
                    startBall();
                }
            }
        });
        canvas.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent keyUpEvent) {
                paddle.keyReleased(keyUpEvent);
            }
        });

        canvas.addMouseDownHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent mouseDownEvent) {
                startBall();
            }
        });
        gameInit();
    }

    private void startBall() {
        if (!ballInGame) {
            ballInGame = true;
            ball.setYDir(1);
            if (!inGame) {
                gameInit();
            }
        }
    }

    public void gameInit() {
        context2d.clearRect(0, 0, Commons.WIDTH, Commons.HEIGHT);
        timeMilis = Commons.time;
        lastDate = new Date();
        lives = 3;
        ballInGame = false;
        ball = new Ball();
        ball.setSpeed(ballSpeed);
        paddle = new Paddle();
        testBrick = new Brick(10, 10, 4);
        bricks = new Brick[Commons.BRICKS];
        destroyedBricks = 0;
        int brickStartX = (Commons.WIDTH - testBrick.getWidth() * 5) / 2;
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                bricks[k] = new Brick(brickStartX + j * testBrick.getImg().getWidth(), 150 + i * testBrick.getImg().getHeight(), 3 - i);
                k++;
            }
        }
        inGame = true;
        endScreen = new Image("mywebapp/endScreen.png");
        gameStart();
    }

    public void setBallSpeed(int speed) {
        ballSpeed = speed;
    }

    public void gameStart() {

            gameLoop = new Timer() {
                @Override
                public void run() {
                    update();
                    render();
                    updateShowTime();
                    if (lives <= 0 || timeMilis <= 0 || destroyedBricks == Commons.BRICKS) {
                        stopGame();
                        gameLoop.cancel();
                    }
                }
            };
            gameLoop.scheduleRepeating(1);

    }

    private void render() {
        context2d.clearRect(0, 0, Commons.WIDTH, Commons.HEIGHT);
        context2d.setGlobalAlpha(1);
        ball.renderSprite(context2d);
        paddle.renderSprite(context2d);
        for (int i = 0; i < Commons.BRICKS; i++) {
            if (!bricks[i].isDestroyed()) {
                bricks[i].renderSprite(context2d);
            }
        }
        context2d.setFont("15px arial");
        context2d.setFillStyle("#ffffff");
        context2d.fillText("Lives: " + lives, Commons.WIDTH - 60, 16);
    }

    void updateShowTime() {
        Date updatedDate = new Date();
        long dif = updatedDate.getTime() - lastDate.getTime();
        context2d.setFillStyle("#ffffff");
        context2d.setFont("15px arial");
        timeMilis -= dif;
        int x = timeMilis / 1000;
        int seconds = x % 60;
        x /= 60;
        int minutes = x % 60;
        context2d.fillText(minutes + " : " + seconds, 10, 15);
        lastDate = updatedDate;
    }

    private void update() {

        ball.move();
        paddle.move();
        checkCollision();
    }

    public void stopGame() {
        inGame = false;
        ballInGame = false;
        gameLoop.cancel();
        int x = Commons.WIDTH / 2 - endScreen.getWidth() / 2;
        int y = 300;
        context2d.drawImage(ImageElement.as(endScreen.getElement()), x, y);
        context2d.setFillStyle("#ffffff");
        context2d.setFont("50px arial");

        if (destroyedBricks == Commons.BRICKS) {
            context2d.fillText("You Win!", x + 75, y + 70);
            context2d.setFont("25px arial");
        } else {
            context2d.fillText("Game Over", x + 48, y + 60);
            context2d.setFont("25px arial");
            if (lives <= 0) {
                context2d.fillText("Out of lives", x + 115, y + 90);
            } else if (timeMilis < 0) {
                context2d.fillText("Out of time", x + 110, y + 100);
            }
        }
    }

    private void checkCollision() {

        ball.isColliding();
        if (ball.getY() > Commons.EDGE) {
            ballInGame = false;
            lives -= 1;
            ball.resetState();
        }
        if (ball.isColliding(paddle)) {
            ball.calculateCollisions(paddle);
        }
        for (int i = 0; i < Commons.BRICKS; i++) {
            if (!bricks[i].isDestroyed()) {
                if (ball.isColliding(bricks[i])) {
                    bricks[i].damage();
                    if (bricks[i].isDestroyed()) {
                        destroyedBricks += 1;
                    }
                    ball.bounce();
                    break;
                }
            }
        }

    }
}
