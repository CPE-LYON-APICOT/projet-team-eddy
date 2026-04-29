package fr.cpe.service;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Singleton;

import fr.cpe.model.Monstre;
import fr.cpe.model.Projectile;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

@Singleton
public class ProjectileService {
    private final List<Projectile> projectiles = new ArrayList<>();
    private final List<ImageView> sprites = new ArrayList<>();
    private Pane gamePane;

    public void init(Pane gamePane) {
        this.gamePane = gamePane;
    }

    public void lacherProjectile(double startX, double startY, double targetX, double targetY) {
        Projectile p = new Projectile(startX, startY, targetX, targetY);
        projectiles.add(p);

        ImageView sprite = new ImageView("balle.png");
        sprite.setFitWidth(15);
        sprite.setFitHeight(15);
        
        sprites.add(sprite);
        gamePane.getChildren().add(sprite);
        sprite.toFront();
    }

    public void update(List<Monstre> monstres) {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = projectiles.get(i);
            ImageView s = sprites.get(i);

            p.update();
            s.setLayoutX(p.getX());
            s.setLayoutY(p.getY());

            boolean aTouche = false;
            for (Monstre m : monstres) {
                if (checkCollision(p, m)) {
                    m.recevoirDegats(20);
                    aTouche = true;
                    break; 
                }
            }

            if (aTouche || isHorsLimites(p)) {
                gamePane.getChildren().remove(s);
                projectiles.remove(i);
                sprites.remove(i);
                i--; 
            }
        }
    }

    private boolean checkCollision(Projectile p, Monstre m) {
        double dx = p.getX() - (m.getX() + 50); 
        double dy = p.getY() - (m.getY() + 50);
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < 40;
    }

    private boolean isHorsLimites(Projectile p) {
        return p.getX() < 0 || p.getX() > 1920 || p.getY() < 0 || p.getY() > 1080;
    }
}