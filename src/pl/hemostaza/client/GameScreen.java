package pl.hemostaza.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.*;

public class GameScreen implements EntryPoint {

    private static final int CANVAS_WIDTH = 440;
    private static final int CANVAS_HEIGHT = 780;

    //private Timer timer;
    private String message = "Game over";
    private Ball ball;
    private Paddle paddle;
    private Brick testBrick;
    private Brick[] bricks; //tablica cegielek


    private boolean inGame = false;

    private Canvas canvas;

    double speedMode;

    VerticalPanel difficultyMenu;

    HorizontalPanel hPanel;

    Arcanoid arcanoid;

    Audio song;


    private class buttonClickHandler implements ClickHandler{
        @Override
        public void onClick(ClickEvent clickEvent) {
            String mode = ((Button) clickEvent.getSource()).getText();
            switch(mode){
                case "Easy":
                    arcanoid.setBallSpeed(1);
                    break;
                case "Medium":
                    arcanoid.setBallSpeed(2);
                    break;
                case "Hard":
                    arcanoid.setBallSpeed(3);
                    break;
                case "Harder":
                    arcanoid.setBallSpeed(4);
                    break;
                case "Ultra Instinct":
                    arcanoid.setBallSpeed(6);
                    song.play();
                    break;
                default:
                    arcanoid.setBallSpeed(666);
                    break;
            }
            difficultyMenu.setVisible(false);
            arcanoid.gameInit();
            canvas.setVisible(true);
            canvas.setFocus(true);
            }

    }
    @Override
    public void onModuleLoad() {

        speedMode = 1;
        hPanel = new HorizontalPanel();


        canvas = Canvas.createIfSupported();
        canvas.setCoordinateSpaceWidth(Commons.WIDTH);
        canvas.setCoordinateSpaceHeight(Commons.HEIGHT);


        song = Audio.createIfSupported();

        song.setSrc("mywebapp/ultra.wav");

        setDifficultyMenu();
        difficultyMenu.setVisible(false);
        hPanel.add(difficultyMenu);
        arcanoid = new Arcanoid(canvas);
        Button b = new Button("New Game", new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (difficultyMenu.isVisible()) {
                    difficultyMenu.setVisible(false);
                } else difficultyMenu.setVisible(true);
                arcanoid.stopGame();
                canvas.setVisible(false);
                song.pause();
            }
        });


        RootPanel.get("gameScreen").add(b);
        RootPanel.get("gameScreen").add(hPanel);
        canvas.setVisible(false);
        RootPanel.get("gameScreen").add(canvas);

    }

    private void setDifficultyMenu() {
        difficultyMenu = new VerticalPanel();
        difficultyMenu.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        difficultyMenu.setSpacing(10);

        Button easyMode = new Button("Easy",new buttonClickHandler());
        Button mediumMode = new Button("Medium",new buttonClickHandler());
        Button hardMode = new Button("Hard",new buttonClickHandler());
        Button harderMode = new Button("Harder",new buttonClickHandler());
        Button ultraInstinct = new Button("Ultra Instinct",new buttonClickHandler());

        difficultyMenu.add(easyMode);
        difficultyMenu.add(mediumMode);
        difficultyMenu.add(hardMode);
        difficultyMenu.add(harderMode);
        difficultyMenu.add(ultraInstinct);

    }
}
