package routeempire.render;

import routeempire.model.map.Position;

/**
 * Controls what part of the map is visible on screen.
 * The camera has a position (top-left corner of the view) and handles
 * converting between screen pixels and world tile coordinates.
 */
public class Camera {

    public static final int TILE_SIZE = 32; // pixels per tile

    private double x; // world pixel offset (top-left corner)
    private double y;
    private int viewportWidth;  // screen size in pixels
    private int viewportHeight;

    public Camera(int viewportWidth, int viewportHeight) {
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        this.x = 0;
        this.y = 0;
    }

    /**
     * Move the camera by pixel offset (for mouse dragging).
     */
    public void pan(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * Center the camera on a tile position.
     */
    public void centerOn(int tileX, int tileY) {
        this.x = tileX * TILE_SIZE - viewportWidth / 2.0;
        this.y = tileY * TILE_SIZE - viewportHeight / 2.0;
    }

    /**
     * Convert screen pixel coordinates to tile coordinates.
     * Used when the player clicks the map.
     */
    public Position screenToTile(double screenX, double screenY) {
        int tileX = (int) ((screenX + x) / TILE_SIZE);
        int tileY = (int) ((screenY + y) / TILE_SIZE);
        return new Position(tileX, tileY);
    }

    /**
     * Convert world tile position to screen pixel coordinates.
     * Returns the top-left pixel of the tile on screen.
     */
    public double getScreenX(double worldTileX) {
        return worldTileX * TILE_SIZE - x;
    }

    public double getScreenY(double worldTileY) {
        return worldTileY * TILE_SIZE - y;
    }

    /**
     * Which tile column is the leftmost visible tile?
     */
    public int getFirstVisibleTileX() {
        return Math.max(0, (int) (x / TILE_SIZE));
    }

    /**
     * Which tile row is the topmost visible tile?
     */
    public int getFirstVisibleTileY() {
        return Math.max(0, (int) (y / TILE_SIZE));
    }

    /**
     * How many tile columns fit on screen (plus 1 for partial tiles).
     */
    public int getVisibleTilesX() {
        return viewportWidth / TILE_SIZE + 2;
    }

    /**
     * How many tile rows fit on screen (plus 1 for partial tiles).
     */
    public int getVisibleTilesY() {
        return viewportHeight / TILE_SIZE + 2;
    }

    public void setViewportSize(int width, int height) {
        this.viewportWidth = width;
        this.viewportHeight = height;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getViewportWidth() { return viewportWidth; }
    public int getViewportHeight() { return viewportHeight; }
}