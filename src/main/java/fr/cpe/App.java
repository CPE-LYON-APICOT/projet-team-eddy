package fr.cpe;

import com.google.inject.Guice;
import com.google.inject.Injector;

import fr.cpe.engine.GameEngine;
import fr.cpe.engine.InputService;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;

    private GameEngine engine;

    @Override
    public void start(Stage stage) {
        Injector injector = Guice.createInjector(new AppModule());

        engine = injector.getInstance(GameEngine.class);
        InputService inputService = injector.getInstance(InputService.class);

        Pane gamePane = new Pane();
        gamePane.setPrefSize(WIDTH, HEIGHT);
        gamePane.setMinSize(WIDTH, HEIGHT);
        gamePane.setMaxSize(WIDTH, HEIGHT);
        gamePane.setStyle("-fx-background-color: #1e1e2e;");

        Group scaleGroup = new Group(gamePane);

        StackPane root = new StackPane(scaleGroup);
        root.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        scene.widthProperty().addListener((obs, oldVal, newVal) -> ajusterEchelle(scene, scaleGroup));
        scene.heightProperty().addListener((obs, oldVal, newVal) -> ajusterEchelle(scene, scaleGroup));

        scene.setOnKeyPressed(e -> inputService.handleKeyPressed(e.getCode()));
        scene.setOnKeyReleased(e -> inputService.handleKeyReleased(e.getCode()));

        stage.setTitle("Black Holed");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

        engine.start(gamePane);
    }

    private void ajusterEchelle(Scene scene, Group scaleGroup) {
        double scale = Math.min(
            scene.getWidth() / WIDTH,
            scene.getHeight() / HEIGHT
        );
        scaleGroup.setScaleX(scale);
        scaleGroup.setScaleY(scale);
    }

    @Override
    public void stop() {
        if (engine != null) {
            engine.stop();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}