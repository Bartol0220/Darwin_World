package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

import java.util.*;

public class RandomGrassGrow implements Iterable<Vector2d> {
    private final List<Vector2d> freeBetterGrassPositions;
    private final List<Vector2d> freeWorseGrassPositions;
    private final int grassNumber;
    private int generated = 0;
    private final Random random = new Random();

    public RandomGrassGrow(List<Vector2d> freeBetterGrassPositions, List<Vector2d> freeWorseGrassPositions, int grassNumber) {
        this.freeBetterGrassPositions = freeBetterGrassPositions;
        this.freeWorseGrassPositions = freeWorseGrassPositions;
        this.grassNumber = grassNumber;

        Collections.shuffle(freeBetterGrassPositions);
        Collections.shuffle(freeWorseGrassPositions);
    }

    private Vector2d findVector(List<Vector2d> possiblePositions) {
        Vector2d vector = possiblePositions.getLast();
        possiblePositions.removeLast();
        generated++;
        return vector;
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return (!freeBetterGrassPositions.isEmpty() || !freeWorseGrassPositions.isEmpty()) && generated < grassNumber;
            }

            @Override
            public Vector2d next() {
                if (hasNext()) {
                    if (freeBetterGrassPositions.isEmpty()) {
                        return findVector(freeWorseGrassPositions);
                    } else if (freeWorseGrassPositions.isEmpty()) {
                        return findVector(freeBetterGrassPositions);
                    } else {
                        if (random.nextInt(10) < 8) {
                            return findVector(freeBetterGrassPositions);
                        } else {
                            return findVector(freeWorseGrassPositions);
                        }
                    }
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
