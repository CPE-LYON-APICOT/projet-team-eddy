package fr.cpe.service;

import com.google.inject.Inject;

import fr.cpe.model.Salle;
import fr.cpe.service.observer.GameStateListener;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

public class GameService implements GameStateListener {

    private final PlayerService playerService;
    private final MonstreService monstreService;
    private final GameStateService gameStateService;
    private final ProjectileService projectileService;
    private final MapService mapService;
    private Pane gamePane;

    @Inject
    public GameService(PlayerService playerService, MonstreService monstreService, GameStateService gameStateService, ProjectileService projectileService, MapService mapService) {
        this.playerService = playerService;
        this.monstreService = monstreService;
        this.gameStateService = gameStateService;
        this.projectileService = projectileService;
        this.mapService = mapService;
        this.gameStateService.subscribe(this);
    }

    public void init(Pane gamePane) {
        this.gamePane = gamePane;
        mapService.init(gamePane);
        playerService.init(gamePane);
        monstreService.init(gamePane, mapService.getSalleCourante().getType());
        projectileService.init(gamePane);
        gameStateService.setCurrentState(GameStateService.RunState.IN_GAME);
    }

    public void update(double width, double height, long now) {
        if (gameStateService.getCurrentState() != GameStateService.RunState.IN_GAME) {
            return;
        }

        if (playerService.getJoueur().getHp() <= 0) {
            gameStateService.setCurrentState(GameStateService.RunState.DEATH_SCREEN);
            playerService.ecranGameOver();
            return;
        }

        Salle courante = mapService.getSalleCourante();

        if (courante.getType() == 2 && monstreService.getMonstres().isEmpty()) {
            gameStateService.setCurrentState(GameStateService.RunState.VICTORY_SCREEN);
            return;
        }

        playerService.update(width, height, now, monstreService.getMonstres());
        monstreService.update(width, height, now, playerService.getJoueur());
        projectileService.update(monstreService.getMonstres());

        verifierPortes();
    }

    private void verifierPortes() {
        if (!monstreService.getMonstres().isEmpty()) {
            return;
        }

        if (gamePane == null || gamePane.getScene() == null) {
            return;
        }

        double px = playerService.getJoueur().getX();
        double py = playerService.getJoueur().getY();
        double screenW = 1920;
        double screenH = 1080;

        Salle courante = mapService.getSalleCourante();

        if (courante.hasPorteNord() && py <= 65 && px >= 860 && px <= 1060) {
            changerDeSalle(courante.getX(), courante.getY() - 1, px, screenH - 150);
        } else if (courante.hasPorteSud() && py >= 965 && px >= 860 && px <= 1060) {
            changerDeSalle(courante.getX(), courante.getY() + 1, px, 100);
        } else if (courante.hasPorteOuest() && px <= 65 && py >= 440 && py <= 640) {
            changerDeSalle(courante.getX() - 1, courante.getY(), screenW - 150, py);
        } else if (courante.hasPorteEst() && px >= 1805 && py >= 440 && py <= 640) {
            changerDeSalle(courante.getX() + 1, courante.getY(), 100, py);
        }
    }

    private void changerDeSalle(int targetX, int targetY, double newPlayerX, double newPlayerY) {
        Salle prochaine = mapService.getSalle(targetX, targetY);
        
        if (prochaine != null) {
            mapService.chargerSalleVisuel(prochaine);
            playerService.replacerDansNouveauPane(gamePane, newPlayerX, newPlayerY);
            monstreService.reinitialiserSalle(gamePane, prochaine.getType());
        }
    }

    @Override
    public void onStateChanged(GameStateService.RunState newState) {
        if (gamePane == null) {
            return;
        }

        if (newState == GameStateService.RunState.VICTORY_SCREEN) {
            gamePane.getChildren().clear();
            ImageView ecranVictoire = new ImageView("victoire.png");
            ecranVictoire.setFitWidth(1920);
            ecranVictoire.setFitHeight(1080);
            gamePane.getChildren().add(ecranVictoire);
        } else if (newState == GameStateService.RunState.DEATH_SCREEN) {
            gamePane.getChildren().clear();
            ImageView ecranDefaite = new ImageView("gameover.png");
            ecranDefaite.setFitWidth(1920);
            ecranDefaite.setFitHeight(1080);
            gamePane.getChildren().add(ecranDefaite);
        }
    }

    @Override
    public void onStardustChanged(int newAmount) {
        
    }
}