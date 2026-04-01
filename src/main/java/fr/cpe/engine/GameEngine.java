package fr.cpe.engine;

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║                                                                            ║
// ║   🔒  FICHIER INTERDIT — NE PAS MODIFIER CE FICHIER  🔒                    ║
// ║                                                                            ║
// ║   Ce fichier contient la boucle de jeu (game loop). Il appelle             ║
// ║   automatiquement update() sur votre GameService à chaque frame (~60 fps). ║
// ║                                                                            ║
// ║   Vous n'avez PAS besoin de le modifier.                                   ║
// ║   Toute votre logique va dans GameService et vos propres services.         ║
// ║                                                                            ║
// ║   Voir CONTRIBUTING.md pour savoir quels fichiers modifier.                ║
// ║                                                                            ║
// ╚══════════════════════════════════════════════════════════════════════════════╝

import com.google.inject.Inject;

import fr.cpe.service.GameService;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

/**
 * Moteur de jeu — orchestre la boucle principale (game loop).
 *
 * <h2>Fonctionnement :</h2>
 * <p>À chaque frame (~60 fois par seconde), le moteur appelle :</p>
 * <ol>
 *   <li>{@code gameService.update(width, height)} — met à jour la logique du jeu</li>
 * </ol>
 *
 * <p>Le rendu est géré automatiquement par le Scene Graph JavaFX : vous ajoutez
 * des Nodes (Circle, Rectangle, Text…) au {@link Pane} et JavaFX les affiche.</p>
 *
 * <h2>Injection Guice :</h2>
 * <p>{@link GameService} est injecté automatiquement par Guice via le constructeur
 * annoté {@code @Inject}. Vous n'avez pas à instancier cette classe vous-même.</p>
 *
 * @see fr.cpe.service.GameService le service où vous codez votre logique
 */
public class GameEngine {

    private final GameService gameService;
    private AnimationTimer gameLoop;

    @Inject
    public GameEngine(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Démarre la boucle de jeu sur le Pane donné.
     * Appelle {@code gameService.init(gamePane)} une fois,
     * puis {@code gameService.update(w, h)} à chaque frame.
     */
    public void start(Pane gamePane) {
        gameService.init(gamePane);
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double w = gamePane.getWidth();
                double h = gamePane.getHeight();
                gameService.update(w, h, now);
            }
        };
        gameLoop.start();
    }

    /**
     * Arrête la boucle de jeu.
     */
    public void stop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
}
