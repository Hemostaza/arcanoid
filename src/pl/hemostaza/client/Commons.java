package pl.hemostaza.client;

public interface Commons {
    int WIDTH = 400; //szerokosc gry
    int HEIGHT = 600; //wysokosc gry
    int EDGE = 580; //miejsce gie piłka umiera
    int BRICKS= 15;
    //poczatkowe polozenie poaletki w osiach x i y
    int INIT_PLAYER_X = 150;
    int INIT_PLAYER_Y = 550;
    //startowe polozenie pileczki w osiach x i y
    int INIT_BALL_X = 200;
    int INIT_BALL_Y = 355;
    //Czas na gre w milisekundach
    int time = 120000;
    //prędkosc piłki
    double SPEEED = 1.5;

}
