package pl.hemostaza.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;

import java.util.Date;

public class Arcanoid {

    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    private final Context2d context2d;

    private int lives;
    private int destroyedBricks;

    private boolean ballInGame; //piłka w grze - w ruchu
    private boolean inGame; //gra aktywna

    private Timer gameLoop; //pętla gry

    private Date lastDate; //ostatni czas

    private int timeMilis; //czas do gry w milisekundach

    private int ballSpeed;

    private Image endScreen;

    private Audio sfx = Audio.createIfSupported();


    public Arcanoid(Canvas canvas) {

        context2d = canvas.getContext2d();
        gameInit();
        endScreen = new Image("mywebapp/images/endScreen.png");

        //Handlery do ruchu
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
    }

    public boolean isInGame() {
        return inGame;
    }

    //wystartowanie początkowe piłeczki
    private void startBall() {
        //jeżeli nie jest w grze
        if (!ballInGame) {
            ballInGame = true;
            ball.setYDir(1);
            //jeżeli przyokazji gra się skończyła to reset gry
            if (!inGame) {
                gameInit();
                gameStart();
            }
        }
    }

    //przygotowanie gry
    public void gameInit() {
        context2d.clearRect(0, 0, Commons.WIDTH, Commons.HEIGHT); //czyszczenie
        timeMilis = Commons.TIME_TO_PLAY; //ustalenie czasu do gry z Commons
        lastDate = new Date();
        lives = 3; //życia do utraty
        ballInGame = false; //piłeczka nie w grze
        destroyedBricks = 0; //zniszczonych cegiełek

        ball = new Ball();
        ball.setSpeed(ballSpeed);
        paddle = new Paddle();
        Brick originBirck = new Brick(0, 0, 0);

        bricks = new Brick[Commons.BRICKS];
        int brickStartX = (Commons.WIDTH - originBirck.getWidth() * 5) / 2; // początek w osi X ukłądania cegiełek tak żeby 5 cegiełek było na środku
        int k = 0; //stowrzone cegiełki
        for (int i = 0; i < Commons.BRICKS / 5; i++) {
            for (int j = 0; j < 5; j++) {
                //ustawienie zycia cegiełek
                int hp = 3 - i;
                if (hp < 1) hp = 1;
                bricks[k] = new Brick(brickStartX + j * originBirck.getWidth(), 150 + i * originBirck.getHeight(), hp);
                k++;
            }
        }

    }

    //ustawienie prędkosci pileczki z wybranego poziomu trudnosci
    public void setBallSpeed(int speed) {
        ballSpeed = speed;
    }

    //Start gry
    public void gameStart() {
        inGame = true;
        sfx.setSrc("mywebapp/sounds/start.wav");
        sfx.play();
        //pętla gry
        gameLoop = new Timer() {
            @Override
            public void run() {
                update();
                render();
                updateTime();
                //warunek zatrzymania gry (ukończenia)
                if (lives <= 0 || timeMilis <= 0 || destroyedBricks == Commons.BRICKS) {
                    stopGame();
                }
            }
        };
        gameLoop.scheduleRepeating(1);

    }

    //aktualizacja gry
    private void update() {
        ball.move();
        paddle.move();
        checkCollision();
    }

    //wyświetlenie gry
    private void render() {
        context2d.clearRect(0, 0, Commons.WIDTH, Commons.HEIGHT); //czyszczenie ekrany
        ball.renderSprite(context2d);
        paddle.renderSprite(context2d);
        //renderowanie cegiełek jeżeli nie są zniszczone
        for (int i = 0; i < Commons.BRICKS; i++) {
            if (!bricks[i].isDestroyed()) {
                bricks[i].renderSprite(context2d);
            }
        }
        //wyśweitlenie ilości żyć
        context2d.setFont("15px arial");
        context2d.setFillStyle("#ffffff");
        context2d.fillText("Lives: " + lives, Commons.WIDTH - 60, 16);
        //wyświetlenie tła w css żeby nie renderować za każdym razem kolejnej pętli z orbazkami
    }

    //aktualizacja czasu gry
    void updateTime() {
        Date updatedDate = new Date(); //pobranie nowej daty
        long dif = updatedDate.getTime() - lastDate.getTime(); //odjęcie starej daty od nowej

        context2d.setFillStyle("#ffffff");
        context2d.setFont("15px arial");

        timeMilis -= dif; //odjęcie różnicy w czasie

        String time = "";
        //zmiana z milisekund na sekundy i minuty
        if (timeMilis < 0) timeMilis = 0;
        else {
            int x = timeMilis / 1000;
            int seconds = x % 60;
            x /= 60;
            int minutes = x % 60;
            if (seconds < 10)
                time = minutes + " : 0" + seconds; //nie miałem lepszego pomysłu
            else time = minutes + " : " + seconds;
            lastDate = updatedDate;
        }
        context2d.fillText(time, 10, 15);
    }

    //zatrzymanie gry
    public void stopGame() {

        ballInGame = false;
        inGame = false;
        gameLoop.cancel();

        //ustalenie pozycji na ekran końcowy
        int x = Commons.WIDTH / 2 - endScreen.getWidth() / 2;
        int y = 300;
        context2d.drawImage(ImageElement.as(endScreen.getElement()), x, y);
        context2d.setFillStyle("#ffffff");
        context2d.setFont("50px arial");

        //w przypadku wygranej jeżeli zniszczoncyh cegiełek jest tylko ile było na poczatku
        if (destroyedBricks == Commons.BRICKS) {
            context2d.fillText("You Win!", x + 75, y + 70);
            context2d.setFont("25px arial");
            sfx.setSrc("mywebapp/sounds/win.wav"); //ustawienie dzwieku wygranej
        } else {
            //albo przegranej po utracie żyć lub skończocnym czasie
            context2d.fillText("Game Over", x + 48, y + 60);
            context2d.setFont("25px arial");
            sfx.setSrc("mywebapp/sounds/gameOver.wav"); //ustawienie dzwieku przegranej

            if (lives <= 0) {
                context2d.fillText("Out of lives", x + 115, y + 90);
            } else if (timeMilis <= 0) {
                context2d.fillText("Out of time", x + 115, y + 90);
            }
        }
        sfx.play();
    }

    //sprawdzenie kolizji
    private void checkCollision() {

        //jeżeli piłeczka wypadnie za krawędź
        if (ball.y > Commons.EDGE) {
            ballInGame = false;
            lives -= 1;
            ball.resetState(); //resetujemy pozycje
            //odgrywamy utraconej piłeczki dźwięk
            sfx.setSrc("mywebapp/sounds/lostBall.wav");
            sfx.play();
        }
        if (ball.isColliding(paddle)) { //jeżeli koliduje z paletką
            ball.calculateCollisions(paddle); //obliczamy kolizje
        }
        //dla każdej cegiełki sprawdzamy kolizje (moglem to chyba zrobić na zasadzie listy z ktorej bym usuwał zniszczone cegiełki)
        for (int i = 0; i < Commons.BRICKS; i++) {
            //jeżeli nie jest zniszczona
            if (!bricks[i].isDestroyed()) {
                //jeżeli piłeczka z nią koliduje
                if (ball.isColliding(bricks[i])) {
                    bricks[i].damage(); //uszkadzamy cegiełke
                    if (bricks[i].isDestroyed()) {
                        destroyedBricks += 1; //jak cegiełka sie zniszczyła to ją dodajemy do zniszcozncyh
                    }
                    //odibjamy piłeczke i rpzerywamy petlę
                    ball.bounce();
                    break;
                }
            }
        }
    }
}
