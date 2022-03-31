package pl.hemostaza.client;

import com.google.gwt.media.client.Audio;

public class Ball extends Sprite {

    private double xDir; //kierunek piłeczki w osiach x i y
    private double yDir;
    private double speed; //prędkosc pileczki

    private Audio collideAudio; //audio uderzenia

    private boolean colInHorizontal; //czy kolizja wystąpiła w poziomie - tu tez nie miałem lepszego pomysłu na rozwiązanie problemu

    public Ball() {
        xDir = 0;
        yDir = 0;
        collideAudio = Audio.createIfSupported();

        loadImage("mywebapp/images/ball.png");
        setDimensions();
        resetState();
    }

    void move() {
        x += speed * xDir;
        y += speed * yDir;

        //odbicie piłeczki przy krawędziach poziomo
        if (x <= 0 || x >= Commons.WIDTH - getWidth()) {
            colInHorizontal = true;
            bounce();
        }
        //i pionowo
        if (y <= 0) {
            colInHorizontal = false;
            bounce();
        }
    }

    //settery kierunku piłeczki
    public void setYDir(double y) {
        yDir = y;
    }

    public void setXDir(double x) {
        xDir = x;
    }

    public void resetState() {
        xDir = 0;
        yDir = 0;
        x = Commons.INIT_BALL_X;
        y = Commons.INIT_BALL_Y;
    }

    //sprawdzenie czy piłeczka koliduje
    public boolean isColliding(Sprite other) {
        if (x + getWidth() + xDir * speed > other.x //jej prawy X w ruchu będzie większy niż lewy X innego obiektu i
                && x + xDir * speed < other.x + other.getWidth() //jej lewy X w ruchu będzie mniejszy niż prawy X innego obiekty i
                && y + getHeight() > other.y //jej dolny Y będzie większy niż górny Y innego obiektu i
                && y < other.y + other.getHeight() //jej gówny Y będzie mniejszy niż dolny Y innego obiektu
        ) {
            colInHorizontal = true; //to będzie kolizja w poziomie
            return true;
        }
        if (x + getWidth() > other.x //jej prawy X będzie większy niż lewy X innego obiektu i
                && x < other.x + other.getWidth() //jej lewy X będzie mniejszy niż prawy X innego obiektu i
                && y + getHeight() + yDir * speed > other.y //jej dolny Y w ciągłym ruchu będzie większy od górnego Y innego obiektu
                && y + yDir * speed < other.y + other.getHeight()) //jej górny Y w ciągłym ruchu będzie mniejszy niż dolny Y innego obiektu
        {
            colInHorizontal = false; //to bedzie kolizja w pionie
            return true;
        }
        //a jeżeli nic z wyżej to nie ma kolizji
        return false;

    }

    //Odbicie piłeczki od paletki
    public void calculateCollisions(Paddle other) {

        double otherCenterPosX = other.x + other.getWidth() / 2; //środek paletki
        double thisCenterPosX = x + getWidth() / 2; //środek piłeczki
        double relativeX = thisCenterPosX - otherCenterPosX; //relatywna pozycja piłeczki względem paletki

        xDir = (relativeX / (other.getWidth() * 0.5)); //ustalenie maksymalnego kierunku w osi X (nie mialem lepszego pomyslu ale przy odbiciu krańcem paletki jest ok 65 stopni)
        yDir *= -1; //odbicie w osi Y

        collideAudio.setSrc("mywebapp/sounds/colPaddle.wav");
        collideAudio.play();
    }

    //odbiciepiłeczki od wszystkiego innego
    public void bounce() {
        if (colInHorizontal) {
            xDir *= -1;//jezeli kolizja w poziomie to odbicie w poziomie
        }
        else {
            yDir *= -1; //jeżeli nie w poziomie to odbicie w pionie
        }
        collideAudio.setSrc("mywebapp/sounds/ColBrick.wav");
        collideAudio.play();
    }

    //usatwienie prędkości piłeczki
    void setSpeed(int speed) {
        this.speed = speed;
    }

}
