package fr.cpe.service;

import com.google.inject.Inject;

import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Text;

public class GameService {

    private final PlayerService playerService;
    private final MonstreService monstreService;

    @Inject
    public GameService(PlayerService playerService, MonstreService monstreService) {
        this.playerService = playerService;
        this.monstreService = monstreService;
    }

    public void init(Pane gamePane) {
        playerService.init(gamePane);
        monstreService.init(gamePane);

        //Text text = new Text(20, 30, "Black Holed");
        //text.setFill(Color.web("#cdd6f4"));
        //gamePane.getChildren().add(text);
    }

    public void update(double width, double height, long now) {
        if (playerService.getJoueur().getHp() <= 0) {
            playerService.ecranGameOver();
            return;
        }

        playerService.update(width, height, now, monstreService.getMonstres());
        monstreService.update(width, height, now, playerService.getJoueur());
        // System.out.println("Frame: " + now);
    }
}