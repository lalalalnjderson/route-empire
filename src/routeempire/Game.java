package routeempire;
import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import routeempire.core.GameSpeed;
import routeempire.core.GameState;
import routeempire.core.MapBuilder;
import routeempire.render.Camera;
import routeempire.render.Renderer;


public class Game extends Application {
    private GameState gameState;
    private Renderer renderer;
    private Camera camera;
    private GameSpeed currentSpeed;
    private Label dateLabel;
    private Label budgetLabel;
    private Label speedLabel;
    private double lastMouseX;
    private double lastMouseY;
    private boolean isDragging;
 
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Route Empire");
 
        gameState = MapBuilder.createNewGame();
        currentSpeed = GameSpeed.NORMAL;
 
        BorderPane root = new BorderPane();
 
        Canvas canvas = new Canvas(1200, 700);
        camera = new Camera((int) canvas.getWidth(), (int) canvas.getHeight());
        camera.centerOn(30, 20); // center on middle of map
        renderer = new Renderer(canvas, camera);
 
        root.setCenter(canvas);
 

        HBox toolbar = createToolbar();
        root.setTop(toolbar);
 
        HBox statusBar = createStatusBar();
        root.setBottom(statusBar);
 
        canvas.setOnMousePressed(e -> {
            lastMouseX = e.getX();
            lastMouseY = e.getY();
            isDragging = true;
        });
 
        canvas.setOnMouseDragged(e -> {
            if (isDragging) {
                double dx = lastMouseX - e.getX();
                double dy = lastMouseY - e.getY();
                camera.pan(dx, dy);
                lastMouseX = e.getX();
                lastMouseY = e.getY();
            }
        });
 
        canvas.setOnMouseReleased(e -> {
            isDragging = false;
        });
 
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SPACE:
                    togglePause();
                    break;
                case DIGIT1:
                    currentSpeed = GameSpeed.NORMAL;
                    break;
                case DIGIT2:
                    currentSpeed = GameSpeed.FAST;
                    break;
                case DIGIT3:
                    currentSpeed = GameSpeed.VERY_FAST;
                    break;
            }
        });

        canvas.widthProperty().bind(scene.widthProperty());
        canvas.heightProperty().bind(scene.heightProperty().subtract(60)); // minus toolbar+statusbar
        canvas.widthProperty().addListener((obs, old, val) ->
            camera.setViewportSize(val.intValue(), (int) canvas.getHeight()));
        canvas.heightProperty().addListener((obs, old, val) ->
            camera.setViewportSize((int) canvas.getWidth(), val.intValue()));
 
        primaryStage.setScene(scene);
        primaryStage.setWidth(1280);
        primaryStage.setHeight(800);
        primaryStage.show();
        startGameLoop();
    }
 
    private HBox createToolbar() {
        HBox toolbar = new HBox(10);
        toolbar.setStyle("-fx-background-color: #3c3c3c; -fx-padding: 8 12;");
 
        speedLabel = new Label("Speed: Normal");
        speedLabel.setTextFill(Color.WHITE);
 
        toolbar.getChildren().add(speedLabel);
        return toolbar;
    }
 
    private HBox createStatusBar() {
        HBox statusBar = new HBox();
        statusBar.setStyle("-fx-background-color: #3c3c3c; -fx-padding: 8 16;");
 
        dateLabel = new Label("Jan 1, 2000");
        dateLabel.setTextFill(Color.WHITE);
        dateLabel.setPrefWidth(400);
 
        budgetLabel = new Label("Budget: $10,000");
        budgetLabel.setTextFill(Color.WHITE);
 
        statusBar.getChildren().addAll(dateLabel, budgetLabel);
        return statusBar;
    }
 
    private void togglePause() {
        if (currentSpeed == GameSpeed.PAUSED) {
            currentSpeed = GameSpeed.NORMAL;
        } else {
            currentSpeed = GameSpeed.PAUSED;
        }
    }

    private void startGameLoop() {
        final double TICK_DURATION = 1.0 / 20.0;
        final double[] accumulatedTime = {0.0};
        final long[] lastFrame = {0};
 
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long nowNanos) {
                if (lastFrame[0] == 0) {
                    lastFrame[0] = nowNanos;
                    return;
                }
                double deltaSeconds = (nowNanos - lastFrame[0]) / 1_000_000_000.0;
                lastFrame[0] = nowNanos;
                if (deltaSeconds > 0.25) {
                    deltaSeconds = 0.25;
                }
                double scaledDelta = deltaSeconds * currentSpeed.getMultiplier();
                accumulatedTime[0] += scaledDelta;

                while (accumulatedTime[0] >= TICK_DURATION) {
                    gameTick();
                    accumulatedTime[0] -= TICK_DURATION;
                }
                renderer.render(gameState);
                updateStatusBar();
            }
        };
        timer.start();
    }

    private void gameTick() {
        gameState.getCalendar().tick();
        if (gameState.getCalendar().isMonthEnd()) {
            for (var city : gameState.getCities()) {
                city.monthlyUpdate(gameState.getMap());
            }
        }
        int ticksPerMonth = gameState.getCalendar().getTicksPerMonth();
        for (var facility : gameState.getFacilities()) {
            var linkedStops = gameState.getLinkedStops(facility);
            facility.update(linkedStops, ticksPerMonth);
        }
    }
    private void updateStatusBar() {
        dateLabel.setText(gameState.getCalendar().getDateString());
        budgetLabel.setText("Budget: $" + gameState.getEconomy().getCapital());

        String speedText = "Speed: ";
        switch (currentSpeed) {
            case PAUSED:    speedText += "Paused"; break;
            case NORMAL:    speedText += "Normal"; break;
            case FAST:      speedText += "Fast (2x)"; break;
            case VERY_FAST: speedText += "Very Fast (4x)"; break;
        }
        speedLabel.setText(speedText);
    }

    public static void main(String[] args) {
        launch(args);
    }
}