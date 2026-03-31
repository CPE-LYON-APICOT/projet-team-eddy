package fr.cpe.service;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class PlayerService {

    private ImageView playerSprite;
    private final double speed = 3.0;
    private final Set<KeyCode> activeKeys = new HashSet<>();

    public void init(Pane gamePane) {
        playerSprite = new ImageView("player.png");
        playerSprite.setFitWidth(100);
        playerSprite.setFitHeight(100);
        playerSprite.setPreserveRatio(true);
        
        gamePane.getChildren().add(playerSprite);

        gamePane.setFocusTraversable(true);
        gamePane.requestFocus(); 

        gamePane.setOnKeyPressed(e -> activeKeys.add(e.getCode()));
        gamePane.setOnKeyReleased(e -> activeKeys.remove(e.getCode()));
    }

    public void update(double width, double height) {
        if (playerSprite == null) return;

        double nextX = playerSprite.getLayoutX();
        double nextY = playerSprite.getLayoutY();

        if (activeKeys.contains(KeyCode.W)) nextY -= speed;
        if (activeKeys.contains(KeyCode.S)) nextY += speed;
        if (activeKeys.contains(KeyCode.A)) nextX -= speed;
        if (activeKeys.contains(KeyCode.D)) nextX += speed;

        if (nextX >= 0 && nextX + playerSprite.getFitWidth() <= width) {
            playerSprite.setLayoutX(nextX);
        }
        if (nextY >= 0 && nextY + playerSprite.getFitHeight() <= height) {
            playerSprite.setLayoutY(nextY);
        }
    }
}