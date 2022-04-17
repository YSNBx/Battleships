public enum SHIPTYPE {
    AIRCRAFT_HEALTH(5),
    BATTLESHIP_HEALTH(4),
    SUBMARINE_HEALTH(3),
    CRUISER_HEALTH(3),
    DESTROYER_HEALTH(2);

    private final int health;

    SHIPTYPE(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }
}
