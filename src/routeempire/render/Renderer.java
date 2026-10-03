package routeempire.render;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import routeempire.core.GameState;
import routeempire.model.City;
import routeempire.model.facility.Facility;
import routeempire.model.map.GameMap;
import routeempire.model.map.Position;
import java.util.List;
import routeempire.model.map.Tile;
import routeempire.model.map.TileType;

/**
 * Draws the game world onto a JavaFX Canvas.
 * For now, tiles are colored rectangles. Later we'll add sprites.
 *
 * The renderer never modifies GameState — it only reads data and draws.
 */
public class Renderer {

    private final Canvas canvas;
    private final Camera camera;

    public Renderer(Canvas canvas, Camera camera) {
        this.canvas = canvas;
        this.camera = camera;
    }

    public void render(GameState state) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear screen
        gc.setFill(Color.rgb(50, 50, 50));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        renderTiles(gc, state);
        renderFacilityLabels(gc, state);
        renderCityLabels(gc, state);
    }

    private void renderTiles(GraphicsContext gc, GameState state) {
        GameMap map = state.getMap();
        int tileSize = Camera.TILE_SIZE;

        int startX = camera.getFirstVisibleTileX();
        int startY = camera.getFirstVisibleTileY();
        int endX = startX + camera.getVisibleTilesX();
        int endY = startY + camera.getVisibleTilesY();

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                Tile tile = map.getTile(x, y);
                if (tile == null) continue;

                double screenX = camera.getScreenX(x);
                double screenY = camera.getScreenY(y);

                // Set color based on tile type
                gc.setFill(getTileColor(tile.getType()));
                gc.fillRect(screenX, screenY, tileSize, tileSize);

                // Draw grid lines
                gc.setStroke(Color.rgb(80, 80, 80, 0.3));
                gc.setLineWidth(0.5);
                gc.strokeRect(screenX, screenY, tileSize, tileSize);
            }
        }
    }

    private Color getTileColor(TileType type) {
        switch (type) {
            case EMPTY:         return Color.rgb(180, 210, 140); // light green
            case VEGETATION:    return Color.rgb(80, 150, 60);   // dark green
            case WATER:         return Color.rgb(70, 130, 200);  // blue
            case CITY_BUILDING: return Color.rgb(160, 140, 120); // beige/brown
            case CITY_ROAD:     return Color.rgb(130, 130, 130); // grey
            case PLAYER_ROAD:   return Color.rgb(100, 100, 100); // dark grey
            case BRIDGE:        return Color.rgb(140, 120, 100); // brown-grey
            case STOP:          return Color.rgb(220, 180, 50);  // yellow
            case GARAGE:        return Color.rgb(180, 80, 80);   // red
            case FACILITY:      return Color.rgb(200, 160, 80);  // orange/tan
            default:            return Color.MAGENTA;             // error color
        }
    }

    /**
     * Draws facility names on top of facility tiles.
     */
    private void renderFacilityLabels(GraphicsContext gc, GameState state) {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(10));

        for (Facility facility : state.getFacilities()) {
            List<Position> tiles = facility.getOccupiedTiles();
            if (tiles.isEmpty()) continue;

            // Draw label at the first tile of the facility
            Position first = tiles.get(0);
            double screenX = camera.getScreenX(first.getX());
            double screenY = camera.getScreenY(first.getY());

            gc.setFill(Color.rgb(0, 0, 0, 0.6));
            gc.fillRect(screenX, screenY - 12, facility.getName().length() * 6, 14);
            gc.setFill(Color.WHITE);
            gc.fillText(facility.getName(), screenX + 2, screenY - 1);
        }
    }

    /**
     * Draws city names above cities.
     */
    private void renderCityLabels(GraphicsContext gc, GameState state) {
        gc.setFont(new Font(12));

        for (City city : state.getCities()) {
            double centerX = camera.getScreenX(city.getOriginX() + city.getGridWidth() / 2.0);
            double topY = camera.getScreenY(city.getOriginY());

            String label = city.getName() + " (pop: " + city.getPopulation() + ")";

            gc.setFill(Color.rgb(0, 0, 0, 0.7));
            gc.fillRect(centerX - label.length() * 3, topY - 16, label.length() * 7, 16);
            gc.setFill(Color.WHITE);
            gc.fillText(label, centerX - label.length() * 3 + 3, topY - 3);
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}