package fr.cpe.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fr.cpe.model.Boss;
import fr.cpe.model.Joueur;
import fr.cpe.model.Monstre;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

@Singleton
public class PlayerService {

    private final ProjectileService projectileService;
    private ImageView playerSprite;
    private Joueur joueur;
    private boolean isDead = false;
    private final Set<KeyCode> activeKeys = new HashSet<>();
    private Rectangle hpBackground;
    private Rectangle hpForeground;
    private long lastDamageTime = 0;

    @Inject
    public PlayerService(ProjectileService projectileService) {
        this.projectileService = projectileService;
    }

    public void init(Pane gamePane) {
        playerSprite = new ImageView("player.png");
    
        playerSprite.setFitWidth(50);
        playerSprite.setFitHeight(50);
        playerSprite.setPreserveRatio(true);
        
        joueur = new Joueur(100, 50, 400, 300, 4.0);
        gamePane.getChildren().add(playerSprite);

        hpBackground = new Rectangle(200, 20, Color.DARKGRAY);
        hpBackground.setLayoutX(30);
        hpBackground.setLayoutY(30);

        hpForeground = new Rectangle(200, 20, Color.BLUE);
        hpForeground.setLayoutX(30);
        hpForeground.setLayoutY(30);

        gamePane.getChildren().addAll(hpBackground, hpForeground);

        gamePane.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY && !isDead) {
                double targetX = e.getX();
                double targetY = e.getY();
                
                double startX = joueur.getX() + (playerSprite.getFitWidth() / 2);
                double startY = joueur.getY() + (playerSprite.getFitHeight() / 2);

                projectileService.lacherProjectile(startX, startY, targetX, targetY);
            }
        });

        gamePane.setFocusTraversable(true);
        gamePane.requestFocus();
        gamePane.setOnKeyPressed(e -> activeKeys.add(e.getCode()));
        gamePane.setOnKeyReleased(e -> activeKeys.remove(e.getCode()));
    }

    public void update(double width, double height, long tempsActuel, List<Monstre> monstres) {
        if (isDead) return;

        double dx = 0, dy = 0;
        if (activeKeys.contains(KeyCode.W)) dy -= 1;
        if (activeKeys.contains(KeyCode.S)) dy += 1;
        if (activeKeys.contains(KeyCode.A)) dx -= 1;
        if (activeKeys.contains(KeyCode.D)) dx += 1;

        if (dx != 0 && dy != 0) {
            double norme = Math.sqrt(dx * dx + dy * dy);
            dx /= norme;
            dy /= norme;
        }

        joueur.seDeplacer(dx, dy);
        
        limiterDeplacement();
        
        playerSprite.setLayoutX(joueur.getX());
        playerSprite.setLayoutY(joueur.getY());

        if (tempsActuel - lastDamageTime > 1_000_000_000L) {
            for (Monstre m : monstres) {
                double distanceX = Math.abs(joueur.getX() - m.getX());
                double distanceY = Math.abs(joueur.getY() - m.getY());
                
                if (distanceX < 40 && distanceY < 40) {
                    joueur.setHp(joueur.getHp() - 10);
                    lastDamageTime = tempsActuel;
                    break;
                }

                if (m instanceof Boss) {
                    Boss b = (Boss) m;
                    boolean hitProjectile = false;
                    for (int p = 0; p < 3; p++) {
                        double projX = b.getProjectileX(p, 3);
                        double projY = b.getProjectileY(p, 3);
                        double distProjX = Math.abs(joueur.getX() - projX);
                        double distProjY = Math.abs(joueur.getY() - projY);

                        if (distProjX < 30 && distProjY < 30) {
                            joueur.setHp(joueur.getHp() - 15);
                            lastDamageTime = tempsActuel;
                            hitProjectile = true;
                            break;
                        }
                    }
                    if (hitProjectile) break;
                }
            }
        }

        double ratio = Math.max(0, (double) joueur.getHp() / 100.0);
        hpForeground.setWidth(200 * ratio);
    }

    public void replacerDansNouveauPane(Pane gamePane, double newX, double newY) {
        joueur.setX(newX);
        joueur.setY(newY);
        
        gamePane.getChildren().removeAll(playerSprite, hpBackground, hpForeground);
        gamePane.getChildren().addAll(playerSprite, hpBackground, hpForeground);
    }

    public void limiterDeplacement() {
        double x = joueur.getX();
        double y = joueur.getY();
        
        double limiteGauche = 60;
        double limiteDroite = 1920 - 110;
        double limiteHaut = 60;
        double limiteBas = 1080 - 110;

        if (x < limiteGauche) {
            joueur.setX(limiteGauche);
        } else if (x > limiteDroite) {
            joueur.setX(limiteDroite);
        }

        if (y < limiteHaut) {
            joueur.setY(limiteHaut);
        } else if (y > limiteBas) {
            joueur.setY(limiteBas);
        }
    }

    public Joueur getJoueur() { return joueur; }
    public void ecranGameOver() { isDead = true; }
}