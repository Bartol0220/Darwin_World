package agh.ics.oop.model;

public enum MapDirection {
    NORTH(0),
    NORTH_EAST(1),
    EAST(2),
    SOUTH_EAST(3),
    SOUTH(4),
    SOUTH_WEST(5),
    WEST(6),
    NORTH_WEST(7);


    static final Vector2d NORTH_VECTOR = new Vector2d(0, 1);
    static final Vector2d NORTH_EAST_VECTOR = new Vector2d(1, 1);
    static final Vector2d EAST_VECTOR = new Vector2d(1, 0);
    static final Vector2d SOUTH_EAST_VECTOR = new Vector2d(1, -1);
    static final Vector2d SOUTH_VECTOR = new Vector2d(0, -1);
    static final Vector2d SOUTH_WEST_VECTOR = new Vector2d(-1, -1);
    static final Vector2d WEST_VECTOR = new Vector2d(-1, 0);
    static final Vector2d NORTH_WEST_VECTOR = new Vector2d(-1, 1);
    static final MapDirection[] MAP_DIRECTIONS = MapDirection.values();
    private final int index;

    MapDirection(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String toString() {
        return switch (this) {
            case NORTH -> "N";
            case NORTH_EAST -> "NE";
            case EAST -> "E";
            case SOUTH_EAST -> "SE";
            case SOUTH -> "S";
            case SOUTH_WEST -> "SW";
            case WEST -> "W";
            case NORTH_WEST -> "WE";
        };
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> NORTH_VECTOR;
            case NORTH_EAST -> NORTH_EAST_VECTOR;
            case EAST -> EAST_VECTOR;
            case SOUTH_EAST -> SOUTH_EAST_VECTOR;
            case SOUTH -> SOUTH_VECTOR;
            case SOUTH_WEST -> SOUTH_WEST_VECTOR;
            case WEST -> WEST_VECTOR;
            case NORTH_WEST -> NORTH_WEST_VECTOR;
        };
    }

    public MapDirection nextOrientation(int gene){
        return MAP_DIRECTIONS[(this.getIndex() + gene)%MapDirection.values().length];
    }
}

