package agh.ics.oop;

import java.util.Arrays;

public class HashArray {
    private final int[] array;

    public HashArray(int[] array) {
        this.array = array.clone(); // Defensive copy to ensure immutability
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Same object reference
        if (o == null || getClass() != o.getClass()) return false; // Type check
        HashArray that = (HashArray) o;
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
