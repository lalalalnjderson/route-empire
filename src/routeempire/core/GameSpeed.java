package routeempire.core;

public enum GameSpeed {
    PAUSED(0.0),
    NORMAL(1.0),
    FAST(2.0),
    VERY_FAST(4.0);

    private final double multiplier;

    GameSpeed(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }
}