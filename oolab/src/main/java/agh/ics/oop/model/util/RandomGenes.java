package agh.ics.oop.model.util; // czemu to jest util, a nie genes?

import java.util.*;

public class RandomGenes implements Iterable<Integer> {
    private final ArrayList<Integer> possibleValues = new ArrayList<>();
    private final int count;
    private int generated = 0;

    public RandomGenes(int maxValue, int without, int count) {
        this.count = count;
        for (int i = 0; i < maxValue; i++) {
            if (i != without) {
                possibleValues.add(i);
            }
        }
        Collections.shuffle(possibleValues);
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {

            @Override
            public boolean hasNext() {
                return !possibleValues.isEmpty() && generated < count;
            }

            @Override
            public Integer next() {
                if (hasNext()) {
                    Integer number = possibleValues.getLast();
                    possibleValues.removeLast();
                    generated++;
                    return number;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
