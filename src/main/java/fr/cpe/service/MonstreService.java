package fr.cpe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.google.inject.Singleton;
import fr.cpe.model.Joueur;
import fr.cpe.model.Monstre;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

@Singleton
public class MonstreService {

    private final List<Monstre> monstres = new ArrayList<>();
    private final List<ImageView> sprites = new ArrayList<>();
    private final Random random = new Random();
    private Pane gamePane;

    public List<Monstre> getMonstres() {
        return this.monstres;
    }

    public void init(Pane gamePane) {
        this.gamePane = gamePane;
        for (int i = 0; i < 5; i++) {
            double startX = random.nextDouble() * 700;
            double startY = random.nextDouble() * 500;
            
            Monstre m = new Monstre(50, 10, startX, startY, 1);
            monstres.add(m);

            ImageView sprite = new ImageView("monstre.png");
            sprite.setFitWidth(100);
            sprite.setFitHeight(100);
            sprite.setPreserveRatio(true);
            sprite.setLayoutX(m.getX());
            sprite.setLayoutY(m.getY());

            sprites.add(sprite);
            gamePane.getChildren().add(sprite);
        }
    }

    public void update(double width, double height, long tempsActuel, Joueur joueur) {
        for (int i = 0; i < monstres.size(); i++) {
            Monstre m = monstres.get(i);
            ImageView sprite = sprites.get(i);

            if (m.getHp() <= 0) {
                gamePane.getChildren().remove(sprite);
                monstres.remove(i);
                sprites.remove(i);
                continue;
            }

            m.traquer(joueur, tempsActuel);

            sprite.setLayoutX(m.getX());
            sprite.setLayoutY(m.getY());
        }
    }
}