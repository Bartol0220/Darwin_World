package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

import java.util.*;

public class RandomVector2d implements Iterable<Vector2d> {
    private final double maximum;
    private final int mapWidth;
    private final int mapHeight;
    private int generated = 0;
    private final Random random = new Random();

    public RandomVector2d(int mapWidth, int mapHeight, int maximum) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.maximum = maximum;
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new Iterator<>() {

            @Override
            public boolean hasNext() {
                return generated < maximum ;
            }

            @Override
            public Vector2d next() {
                if(hasNext()) {
                    int x = random.nextInt(mapWidth);
                    int y = random.nextInt(mapHeight);
                    Vector2d vector = new Vector2d(x, y);
                    generated++;
                    return vector;
                }
                else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
