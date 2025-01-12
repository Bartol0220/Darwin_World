package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomDifferentVector2d implements Iterable<Vector2d> {
    private final ArrayList<Vector2d> possiblePositions = new ArrayList<>();
    private final double maximum;
    private final int grassNumber;
    private int generated = 0;
    private final Random random = new Random();

    public RandomDifferentVector2d(int grassNumber) {
        this.grassNumber = grassNumber;
        this.maximum = Math.sqrt(grassNumber *10);
        generatePossiblePositions();
    }

    private void generatePossiblePositions() {
        for(int x = 0; x <= maximum; x++) {
            for(int y = 0; y <= maximum; y++) {
                possiblePositions.add(new Vector2d(x, y));
            }
        }
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new Iterator<>() {

            @Override
            public boolean hasNext() {
                return !possiblePositions.isEmpty() && generated < grassNumber ;
            }

            @Override
            public Vector2d next() {
                if(hasNext()) {
                    int index = random.nextInt(possiblePositions.size());
                    Vector2d vector = possiblePositions.get(index);
                    possiblePositions.set(index, possiblePositions.getLast());
                    possiblePositions.removeLast();
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
