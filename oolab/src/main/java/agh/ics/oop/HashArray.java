package agh.ics.oop;

import java.util.Arrays;

public class HashArray {
    private final int[] array;

    public HashArray(int[] array) { this.array = array.clone(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashArray that = (HashArray) o;
        return Arrays.equals(this.array, that.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.array);
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}
