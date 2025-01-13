package agh.ics.oop.model;

import java.util.Arrays;

public class ArrayHashMap {
    private final int[] array;

    public ArrayHashMap(int[] array) {
        this.array = array.clone(); // Defensive copy to ensure immutability
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Same object reference
        if (o == null || getClass() != o.getClass()) return false; // Type check
        ArrayHashMap that = (ArrayHashMap) o;
        return Arrays.equals(this.array, that.array); // Content comparison
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.array); // Content-based hash code
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}
