package fr.cpe.service;

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║                                                                            ║
// ║   ✏️  FICHIER MODIFIABLE — Exemple de service injecté par Guice            ║
// ║                                                                            ║
// ║   Montre comment gérer un élément du jeu (modèle + vue + événements)      ║
// ║   dans un seul service. Remplacez-le par vos propres services.             ║
// ║                                                                            ║
// ╚══════════════════════════════════════════════════════════════════════════════╝

import com.google.inject.Inject;
import fr.cpe.engine.InputService;
import fr.cpe.model.Ball;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Service d'exemple — gère entièrement la balle : modèle, vue et événements.
 *
 * <p>C'est un <strong>exemple</strong> de service injecté par Guice dans GameService.
 * Il montre comment regrouper dans un même endroit :</p>
 * <ul>
 *   <li>Le <strong>modèle</strong> ({@link Ball}) — données du jeu</li>
 *   <li>La <strong>vue</strong> ({@link Circle}) — représentation visuelle</li>
 *   <li>Les <strong>événements</strong> — clavier (flèches) + clic souris</li>
 * </ul>
 *
 * <p>GameService reçoit ce service via {@code @Inject} dans son constructeur :</p>
 * <pre>
 *   @Inject
 *   public GameService(BallService ballService) { ... }
 * </pre>
 */
public class BallService {

    private static final double RADIUS = 60;
    private static final double ACCELERATION = 0.3;

    private final InputService inputService;
    private Ball ball;
    private Circle ballNode;

    @Inject
    public BallService(InputService inputService) {
        this.inputService = inputService;
    }

    /**
     * Crée la balle (modèle + vue) et l'ajoute au Pane.
     */
    public void init(Pane gamePane) {
        ball = new Ball(100, 100, 3, 2, Color.web("#f38ba8"));

        ballNode = new Circle(RADIUS, ball.getColor());
        ballNode.setCenterX(ball.x);
        ballNode.setCenterY(ball.y);

        // Clic sur la balle → changement de couleur
        ballNode.setOnMouseClicked(e -> {
            Color c = Color.color(Math.random(), Math.random(), Math.random());
            ball.setColor(c);
            ballNode.setFill(c);
        });

        gamePane.getChildren().add(ballNode);
    }

    /**
     * Met à jour la balle : clavier, physique, rebonds, synchronisation vue.
     */
    public void update(double width, double height) {
        // Les flèches courbent la trajectoire
        if (inputService.isKeyPressed(KeyCode.LEFT))  ball.dx -= ACCELERATION;
        if (inputService.isKeyPressed(KeyCode.RIGHT)) ball.dx += ACCELERATION;
        if (inputService.isKeyPressed(KeyCode.UP))    ball.dy -= ACCELERATION;
        if (inputService.isKeyPressed(KeyCode.DOWN))  ball.dy += ACCELERATION;

        ball.x += ball.dx;
        ball.y += ball.dy;

        boolean bounced = false;

        if (ball.x - RADIUS < 0 || ball.x + RADIUS > width) {
            ball.dx = -ball.dx;
            bounced = true;
        }
        if (ball.y - RADIUS < 0 || ball.y + RADIUS > height) {
            ball.dy = -ball.dy;
            bounced = true;
        }

        if (bounced) {
            ball.setColor(Color.color(Math.random(), Math.random(), Math.random()));
        }

        // Synchronise le modèle → la vue
        ballNode.setCenterX(ball.x);
        ballNode.setCenterY(ball.y);
        ballNode.setFill(ball.getColor());
    }
}
