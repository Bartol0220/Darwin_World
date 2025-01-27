package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void precedesWorks() {
        Vector2d position1 = new Vector2d(2, 2);
        Vector2d position2 = new Vector2d(2, 2);
        Vector2d position3 = new Vector2d(3, 2);
        Vector2d position4 = new Vector2d(2, 3);
        Vector2d position5 = new Vector2d(3, 3);
        Vector2d position6 = new Vector2d(1, 1);

        assertTrue(position1.precedes(position2));
        assertTrue(position1.precedes(position3));
        assertTrue(position1.precedes(position4));
        assertTrue(position1.precedes(position5));
        assertTrue(position6.precedes(position1));
        assertFalse(position3.precedes(position4));
    }

    @Test
    void followsWorks() {
        Vector2d position1 = new Vector2d(2, 2);
        Vector2d position2 = new Vector2d(2, 2);
        Vector2d position3 = new Vector2d(3, 2);
        Vector2d position4 = new Vector2d(2, 3);
        Vector2d position5 = new Vector2d(3, 3);
        Vector2d position6 = new Vector2d(1, 1);

        assertTrue(position2.follows(position1));
        assertTrue(position3.follows(position1));
        assertTrue(position4.follows(position1));
        assertTrue(position5.follows(position1));
        assertTrue(position1.follows(position6));
        assertFalse(position3.follows(position4));
    }

    @Test
    void addWorks() {
        Vector2d position1 = new Vector2d(2, 2);
        Vector2d position2 = new Vector2d(2, 2);
        Vector2d position3 = new Vector2d(3, 5);
        Vector2d position4 = new Vector2d(0, 0);
        Vector2d position5 = new Vector2d(-1, -2);


        assertEquals(new Vector2d(4, 4), position1.add(position2));
        assertEquals(new Vector2d(1, 0), position1.add(position5));
        assertEquals(new Vector2d(2, 3), position3.add(position5));
        assertEquals(position1, position1.add(position4));
    }

    @Test
    void equalsWorks() {
        Vector2d position1 = new Vector2d(2, 2);
        Vector2d position2 = new Vector2d(2, 2);
        Vector2d position3 = new Vector2d(3, 5);

        assertEquals(position1, position2);
        assertNotEquals(position1, position3);
    }
}