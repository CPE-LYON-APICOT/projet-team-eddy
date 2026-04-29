package fr.cpe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fr.cpe.model.Boss;
import fr.cpe.model.Joueur;
import fr.cpe.model.Monstre;
import fr.cpe.model.decorator.MonstrePoison;
import fr.cpe.service.factory.MonstreFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

@Singleton
public class MonstreService {

    private final List<Monstre> monstres = new ArrayList<>();
    private final List<ImageView> sprites = new ArrayList<>();
    private final List<Rectangle> hpBackgrounds = new ArrayList<>();
    private final List<Rectangle> hpForegrounds = new ArrayList<>();
    private final List<ImageView> bossProjectiles = new ArrayList<>();
    private final Random random = new Random();
    private Pane gamePane;
    private final MonstreFactory monstreFactory;

    @Inject
    public MonstreService(MonstreFactory monstreFactory) {
        this.monstreFactory = monstreFactory;
    }

    public List<Monstre> getMonstres() {
        return this.monstres;
    }

    public void init(Pane gamePane, int typeSalle) {
        this.gamePane = gamePane;

        if (typeSalle == 1) {
            return;
        }

        if (typeSalle == 2) {
            Boss boss = new Boss(960, 540);
            monstres.add(boss);

            ImageView sprite = new ImageView("vrai_boss.png");
            sprite.setFitWidth(200);
            sprite.setFitHeight(200);
            sprite.setPreserveRatio(true);
            sprite.setLayoutX(boss.getX());
            sprite.setLayoutY(boss.getY());

            Rectangle bgBar = new Rectangle(200, 15, Color.DARKGRAY);
            Rectangle fgBar = new Rectangle(200, 15, Color.RED);
            
            bgBar.setLayoutX(boss.getX());
            bgBar.setLayoutY(boss.getY() - 25);
            fgBar.setLayoutX(boss.getX());
            fgBar.setLayoutY(boss.getY() - 25);

            sprites.add(sprite);
            hpBackgrounds.add(bgBar);
            hpForegrounds.add(fgBar);

            gamePane.getChildren().addAll(sprite, bgBar, fgBar);

            for (int i = 0; i < 3; i++) {
                ImageView proj = new ImageView("balle_boss.png");
                proj.setFitWidth(30);
                proj.setFitHeight(30);
                proj.setPreserveRatio(true);
                bossProjectiles.add(proj);
                gamePane.getChildren().add(proj);
            }

        } else {
            for (int i = 0; i < 5; i++) {
                double startX = random.nextDouble() * 1600 + 100;
                double startY = random.nextDouble() * 800 + 100;
                
                String typeMonstre;
                String cheminImage;
                double tailleSprite;
                
                int chance = random.nextInt(100);
                
                if (chance < 5) {
                    typeMonstre = "BOSS";
                    cheminImage = "boss.png";
                    tailleSprite = 150.0;
                } else if (chance < 15) {
                    typeMonstre = "VELOCE";
                    cheminImage = "veloce.png";
                    tailleSprite = 60.0;
                } else {
                    typeMonstre = "BASE";
                    cheminImage = "monstre.png";
                    tailleSprite = 100.0;
                }

                Monstre m = monstreFactory.creerMonstre(typeMonstre, startX, startY);

                if (random.nextBoolean()) {
                    m = new MonstrePoison(m);
                }

                monstres.add(m);

                ImageView sprite = new ImageView(cheminImage);
                sprite.setFitWidth(tailleSprite);
                sprite.setFitHeight(tailleSprite);
                sprite.setPreserveRatio(true);
                sprite.setLayoutX(m.getX());
                sprite.setLayoutY(m.getY());

                Rectangle bgBar = new Rectangle(tailleSprite, 8, Color.DARKGRAY);
                Rectangle fgBar = new Rectangle(tailleSprite, 8, Color.RED);
                
                bgBar.setLayoutX(m.getX());
                bgBar.setLayoutY(m.getY() - 15);
                fgBar.setLayoutX(m.getX());
                fgBar.setLayoutY(m.getY() - 15);

                sprites.add(sprite);
                hpBackgrounds.add(bgBar);
                hpForegrounds.add(fgBar);

                gamePane.getChildren().addAll(sprite, bgBar, fgBar);
            }
        }
    }

    public void update(double width, double height, long tempsActuel, Joueur joueur) {
        for (int i = 0; i < monstres.size(); i++) {
            Monstre m = monstres.get(i);
            ImageView sprite = sprites.get(i);
            Rectangle bgBar = hpBackgrounds.get(i);
            Rectangle fgBar = hpForegrounds.get(i);

            if (m.getHp() <= 0) {
                gamePane.getChildren().removeAll(sprite, bgBar, fgBar);
                
                if (m instanceof Boss) {
                    gamePane.getChildren().removeAll(bossProjectiles);
                    bossProjectiles.clear();
                }

                monstres.remove(i);
                sprites.remove(i);
                hpBackgrounds.remove(i);
                hpForegrounds.remove(i);
                i--;
                continue;
            }

            m.traquer(joueur, tempsActuel);

            sprite.setLayoutX(m.getX());
            sprite.setLayoutY(m.getY());

            bgBar.setLayoutX(m.getX());
            bgBar.setLayoutY(m.getY() - 15);
            
            fgBar.setLayoutX(m.getX());
            fgBar.setLayoutY(m.getY() - 15);
            
            double ratio = Math.max(0, (double) m.getHp() / m.getHpMax());
            fgBar.setWidth(sprite.getFitWidth() * ratio);

            if (m instanceof Boss) {
                Boss b = (Boss) m;
                for (int j = 0; j < bossProjectiles.size(); j++) {
                    ImageView proj = bossProjectiles.get(j);
                    proj.setLayoutX(b.getProjectileX(j, bossProjectiles.size()));
                    proj.setLayoutY(b.getProjectileY(j, bossProjectiles.size()));
                }
            }
        }
    }

    public void reinitialiserSalle(Pane gamePane, int typeSalle) {
        if (this.gamePane != null) {
            this.gamePane.getChildren().removeAll(bossProjectiles);
        }
        bossProjectiles.clear();
        monstres.clear();
        sprites.clear();
        hpBackgrounds.clear();
        hpForegrounds.clear();
        init(gamePane, typeSalle);
    }
}