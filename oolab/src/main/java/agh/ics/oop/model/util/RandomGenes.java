package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

import java.util.*;

public class RandomGenes implements Iterable<Integer> {
    private final ArrayList<Integer> possibleValues = new ArrayList<>();
    private final int count;
    private final int maxValue;
    private int generated = 0;
    private Random random = new Random();

    //jesli without jest -1, to losuje wszystkie wartosci
    public RandomGenes(int maxValue, int without, int count) {
        /**
         * jesli without jest -1, to losuje wszystkie wartosci
         */
        this.maxValue = maxValue;
        this.count = count;
        for (int i = 0; i<maxValue; i++){
            if (i != without){
                possibleValues.add(i);
            }
        }
        Collections.shuffle(possibleValues);
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {

            @Override
            public boolean hasNext() {
                return !possibleValues.isEmpty() && generated < count ;
            }

            @Override
            public Integer next() {
                if(hasNext()) {
                    Integer number = possibleValues.getLast();
                    possibleValues.removeLast();
                    generated++;
                    return number;
                }
                else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
