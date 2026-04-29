package fr.cpe.service;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fr.cpe.model.Salle;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

@Singleton
public class MapService {

    private final LevelGeneratorService generatorService;
    private final Map<String, Salle> salles = new HashMap<>();
    private Salle salleCourante;
    private Pane gamePane;

    @Inject
    public MapService(LevelGeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    public void init(Pane gamePane) {
        this.gamePane = gamePane;
        int[][] grille = generatorService.genererNiveau();

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (grille[y][x] != 0) {
                    Salle s = new Salle(x, y, grille[y][x]);
                    
                    if (y > 0 && grille[y - 1][x] != 0) s.setPorteNord(true);
                    if (y < 4 && grille[y + 1][x] != 0) s.setPorteSud(true);
                    if (x < 4 && grille[y][x + 1] != 0) s.setPorteEst(true);
                    if (x > 0 && grille[y][x - 1] != 0) s.setPorteOuest(true);

                    salles.put(x + "," + y, s);

                    if (grille[y][x] == 1) {
                        salleCourante = s;
                    }
                }
            }
        }
        chargerSalleVisuel(salleCourante);
    }

    public void chargerSalleVisuel(Salle salle) {
        salleCourante = salle;
        gamePane.getChildren().clear();
        gamePane.setStyle("-fx-background-color: black;");

        ImageView fond = new ImageView("salle_fond.png");
        fond.setPreserveRatio(true);
        fond.fitWidthProperty().bind(gamePane.widthProperty());
        gamePane.getChildren().add(fond);

        if (salle.hasPorteNord()) {
            ImageView pNord = new ImageView("porte.png");
            pNord.setFitWidth(80);
            pNord.setFitHeight(80);
            pNord.setPreserveRatio(true);
            pNord.layoutXProperty().bind(gamePane.widthProperty().divide(2).subtract(40));
            pNord.setLayoutY(0);
            gamePane.getChildren().add(pNord);
        }
        if (salle.hasPorteSud()) {
            ImageView pSud = new ImageView("porte.png");
            pSud.setFitWidth(80);
            pSud.setFitHeight(80);
            pSud.setPreserveRatio(true);
            pSud.setRotate(180);
            pSud.layoutXProperty().bind(gamePane.widthProperty().divide(2).subtract(40));
            pSud.layoutYProperty().bind(gamePane.heightProperty().subtract(80));
            gamePane.getChildren().add(pSud);
        }
        if (salle.hasPorteOuest()) {
            ImageView pOuest = new ImageView("porte.png");
            pOuest.setFitWidth(80);
            pOuest.setFitHeight(80);
            pOuest.setPreserveRatio(true);
            pOuest.setRotate(270);
            pOuest.setLayoutX(0);
            pOuest.layoutYProperty().bind(gamePane.heightProperty().divide(2).subtract(40));
            gamePane.getChildren().add(pOuest);
        }
        if (salle.hasPorteEst()) {
            ImageView pEst = new ImageView("porte.png");
            pEst.setFitWidth(80);
            pEst.setFitHeight(80);
            pEst.setPreserveRatio(true);
            pEst.setRotate(90);
            pEst.layoutXProperty().bind(gamePane.widthProperty().subtract(80));
            pEst.layoutYProperty().bind(gamePane.heightProperty().divide(2).subtract(40));
            gamePane.getChildren().add(pEst);
        }

        afficherMinimap();
    }

    private void afficherMinimap() {
        double tailleCase = 25.0;
        double offsetX = 1750.0;
        double offsetY = 30.0;

        for (Salle s : salles.values()) {
            Rectangle rect = new Rectangle(tailleCase, tailleCase);
            rect.setLayoutX(offsetX + (s.getX() * tailleCase));
            rect.setLayoutY(offsetY + (s.getY() * tailleCase));

            if (s.getX() == salleCourante.getX() && s.getY() == salleCourante.getY()) {
                rect.setFill(Color.GREEN);
            } else if (s.getType() == 2) {
                rect.setFill(Color.RED);
            } else {
                rect.setFill(Color.GRAY);
            }

            rect.setStroke(Color.BLACK);
            gamePane.getChildren().add(rect);
        }
    }

    public Salle getSalleCourante() {
        return salleCourante;
    }

    public Salle getSalle(int x, int y) {
        return salles.get(x + "," + y);
    }
}