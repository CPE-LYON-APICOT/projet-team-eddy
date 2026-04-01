package fr.cpe.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.inject.Singleton;

import fr.cpe.model.Joueur;
import fr.cpe.model.Monstre;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

@Singleton
public class PlayerService {

    private ImageView playerSprite;
    private Joueur joueur;
    private Text healthText;
    private boolean isDead = false;
    private ImageView gameOverImage;
    private final double speed = 3.0;
    private final Set<KeyCode> activeKeys = new HashSet<>();

    public Joueur getJoueur() {
        return this.joueur;
    }

    public void ecranGameOver() {
    if (!isDead) {
        gameOverImage.setVisible(true);
        gameOverImage.toFront();
        healthText.setText("HP: 0");
        healthText.toFront();
        isDead = true;
    }
}

    public void init(Pane gamePane) {
        playerSprite = new ImageView("player.png");
        joueur = new Joueur(100, 50, 500, 500, speed);

        playerSprite.setFitWidth(100);
        playerSprite.setFitHeight(100);
        playerSprite.setPreserveRatio(true);
        
        gamePane.getChildren().add(playerSprite);

        healthText = new Text(20, 30, "HP: " + joueur.getHp());
        healthText.setFill(Color.web("#cdd6f4"));
        gamePane.getChildren().add(healthText);

        gameOverImage = new ImageView("Death_Screen.png");
        gameOverImage.setFitWidth(800);
        gameOverImage.setFitHeight(600);
        gameOverImage.setVisible(false);
        gamePane.getChildren().add(gameOverImage);

        gamePane.setFocusTraversable(true);
        gamePane.requestFocus(); 

        gamePane.setOnKeyPressed(e -> activeKeys.add(e.getCode()));
        gamePane.setOnKeyReleased(e -> activeKeys.remove(e.getCode()));
    }

    public void update(double width, double height, long tempsActuel, List<Monstre> monstres) {
        if (playerSprite == null || joueur == null) return;

        double dx = 0;
        double dy = 0;

        if (activeKeys.contains(KeyCode.W)) dy -= 1;
        if (activeKeys.contains(KeyCode.S)) dy += 1;
        if (activeKeys.contains(KeyCode.A)) dx -= 1;
        if (activeKeys.contains(KeyCode.D)) dx += 1;

        joueur.seDeplacer(dx, dy);

        joueur.attaquer(monstres, tempsActuel);

        playerSprite.setLayoutX(joueur.getX());
        playerSprite.setLayoutY(joueur.getY());

        healthText.setText("HP: " + joueur.getHp());
    }
}