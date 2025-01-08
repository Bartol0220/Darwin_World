package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

import java.util.*;

public class RandomGrassGrow implements Iterable<Vector2d> {
    private final List<Vector2d> firstCategoryPositions;
    private final List<Vector2d> secondCategoryPositions;
    private final int grassNumber;
    private int generated = 0;
    private final Random random = new Random();

    public RandomGrassGrow(List<Vector2d> firstCategoryPositions, List<Vector2d> secondCategoryPositions, int grassNumber) {
        this.firstCategoryPositions = firstCategoryPositions;
        this.secondCategoryPositions = secondCategoryPositions;
        this.grassNumber = grassNumber;

        Collections.shuffle(firstCategoryPositions);
        Collections.shuffle(secondCategoryPositions);
    }

    private Vector2d findVector(List<Vector2d> possiblePositions) {
        Vector2d vector = possiblePositions.getLast();
        possiblePositions.removeLast();
        generated++;
        return vector;
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new Iterator<Vector2d>() {
            @Override
            public boolean hasNext() {
                return (!firstCategoryPositions.isEmpty() || !secondCategoryPositions.isEmpty()) && generated < grassNumber ;
            }

            @Override
            public Vector2d next() {
                if(hasNext()) {
                    if (firstCategoryPositions.isEmpty()) {
                        return findVector(secondCategoryPositions);
                    } else if (secondCategoryPositions.isEmpty()) {
                        return findVector(firstCategoryPositions);
                    } else {
                        if (random.nextInt(10) < 8) {
                            return findVector(firstCategoryPositions);
                        } else {
                            return findVector(secondCategoryPositions);
                        }
                    }
                }
                else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
