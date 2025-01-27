package agh.ics.oop.model;

import java.util.Objects;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return String.format("(%d,%d)", x, y);
    }

    public boolean precedes(Vector2d other) {
        return x <= other.getX() && y <= other.getY();
    }

    public boolean follows(Vector2d other) {
        return x >= other.getX() && y >= other.getY();
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.getX(), y + other.getY());
    }

    public int hashCode() {
        return Objects.hash(x, y);
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d that))
            return false;
        return Objects.equals(this.x, that.x) && Objects.equals(this.y, that.y);
    }
}
