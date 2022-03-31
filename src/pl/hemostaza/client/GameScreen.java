package pl.hemostaza.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.*;

public class GameScreen implements EntryPoint {


    private Canvas canvas;

    VerticalPanel difficultyMenu;

    Arcanoid arcanoid;

    Audio song;


    @Override
    public void onModuleLoad() {
        HorizontalPanel hPanel = new HorizontalPanel();
        VerticalPanel vMenu = new VerticalPanel();
        vMenu.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER); //wycentrowanie zawrtosci pionowego panelu
        vMenu.setWidth("160px"); //ustawienie szerokosci

        hPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER); //wycentrowanie poziomego panelu
        hPanel.setSpacing(50);

        canvas = Canvas.createIfSupported(); //stworzenie płótna o wymiarach z Commons
        canvas.setCoordinateSpaceWidth(Commons.WIDTH);
        canvas.setCoordinateSpaceHeight(Commons.HEIGHT);

        song = Audio.createIfSupported(); //wstęp ostatniego poziomu trudnosci
        song.setSrc("mywebapp/ultra.wav");

        //stworzenie menu trudności i ukrycie go
        setDifficultyMenu();
        difficultyMenu.setVisible(false);

        //Guzik nowej gry z handlerem na kliknięcie
        Button b = new Button("New Game", new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (difficultyMenu.isVisible()) {
                    difficultyMenu.setVisible(false); //jeżeli menu trudnosci jest widoczne to ukrycie go
                } else {
                    difficultyMenu.setVisible(true); //jezeli jest niewidoczne to pojawienie go i zatrzymanie gry jeżeli jest aktywna
                    if (arcanoid.isInGame()) arcanoid.stopGame();
                }
                song.pause(); //zatrzymanie wstępu
            }
        });
        vMenu.add(b); //dodanie guzika to pionowego panelu
        vMenu.add(difficultyMenu); //dodanie menu trudnosci do pionowego panelu

        arcanoid = new Arcanoid(canvas); //utworzenie arkanoida

        hPanel.add(vMenu); //dodanie pionowego panelu do poziomego panelu
        hPanel.add(canvas); //dodanie płótna do poziomego panelu
        RootPanel.get("gameScreen").add(hPanel); //dodanie poziomego panelu do div gameScreen
        //canvas.setVisible(false); // to było ukrycie płótna z grą ale postanowilem zostawić widok pustej planszy

    }

    //stworzenie menu trudności
    private void setDifficultyMenu() {
        //utworzenie pionowego panelu wraz z wycentrowaniem i przerwami
        difficultyMenu = new VerticalPanel();
        difficultyMenu.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        difficultyMenu.setSpacing(5);

        //stworzenie przycisków używających nowego handlera
        Button easyMode = new Button("Easy", new buttonClickHandler());
        Button mediumMode = new Button("Medium", new buttonClickHandler());
        Button hardMode = new Button("Hard", new buttonClickHandler());
        Button harderMode = new Button("Harder", new buttonClickHandler());
        Button ultraInstinct = new Button("Ultra Instinct", new buttonClickHandler());

        //dodanie przycisków do pionowego panelu
        difficultyMenu.add(easyMode);
        difficultyMenu.add(mediumMode);
        difficultyMenu.add(hardMode);
        difficultyMenu.add(harderMode);
        difficultyMenu.add(ultraInstinct);
    }

    //handler określający który guzik na poziom trudności został wybrany
    private class buttonClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent clickEvent) {
            //poziom trudności określany jest na podstawie tekstu na guziku
            String mode = ((Button) clickEvent.getSource()).getText();

            switch (mode) {
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
                    arcanoid.setBallSpeed(0);
                    break;
            }
            //ukrycie menu trudnosci po wyborze gry
            difficultyMenu.setVisible(false);

            //zainicjowanie gry, ustawienie aktywnosci na plótnie i wystartowanie gry
            arcanoid.gameInit();
            canvas.setFocus(true);
            arcanoid.gameStart();
        }

    }
}
