package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void equals() {
        // given
        Vector2d vector1 = new Vector2d(1, 1);
        Vector2d vector2 = new Vector2d(1, 1);
        Vector2d vector3 = new Vector2d(2, 2);
        Vector2d vector4 = null;
        int number = 1;

        // when
        boolean resultEqualsVector11 = vector1.equals(vector1);
        boolean resultEqualsVector12 = vector1.equals(vector2);
        boolean resultEqualsVector13 = vector1.equals(vector3);
        boolean resultEqualsVector14 = vector1.equals(vector4);
        boolean resultEqualsVector1number = vector1.equals(number);

        // then
        assertTrue(resultEqualsVector11);
        assertTrue(resultEqualsVector12);
        assertFalse(resultEqualsVector13);
        assertFalse(resultEqualsVector14);
        assertFalse(resultEqualsVector1number);
    }

    @Test
    void isToStringWorking() {
        // given
        Vector2d vector1 = new Vector2d(0, 1);

        // when
        String resultToString = vector1.toString();

        // then
        assertEquals("(0,1)", resultToString);
    }

    @Test
    void precedes() {
        // given
        Vector2d vector1 = new Vector2d(1, 1);
        Vector2d vector2 = new Vector2d(2, 2);
        Vector2d vector3 = new Vector2d(0, 0);
        Vector2d vector4 = new Vector2d(2, 0);
        Vector2d vector5 = new Vector2d(0, 2);
        Vector2d vector6 = new Vector2d(1, 1);

        // when
        boolean resultPrecedesVector12 = vector1.precedes(vector2);
        boolean resultPrecedesVector13 = vector1.precedes(vector3);
        boolean resultPrecedesVector14 = vector1.precedes(vector4);
        boolean resultPrecedesVector15 = vector1.precedes(vector5);
        boolean resultPrecedesVector16 = vector1.precedes(vector6);

        // then
        assertTrue(resultPrecedesVector12);
        assertFalse(resultPrecedesVector13);
        assertFalse(resultPrecedesVector14);
        assertFalse(resultPrecedesVector15);
        assertTrue(resultPrecedesVector16);
    }

    @Test
    void follows() {
        // given
        Vector2d vector1 = new Vector2d(1, 1);
        Vector2d vector2 = new Vector2d(2, 2);
        Vector2d vector3 = new Vector2d(0, 0);
        Vector2d vector4 = new Vector2d(2, 0);
        Vector2d vector5 = new Vector2d(0, 2);
        Vector2d vector6 = new Vector2d(1, 1);

        // when
        boolean resultFollowsVector12 = vector1.follows(vector2);
        boolean resultFollowsVector13 = vector1.follows(vector3);
        boolean resultFollowsVector14 = vector1.follows(vector4);
        boolean resultFollowsVector15 = vector1.follows(vector5);
        boolean resultFollowsVector16 = vector1.follows(vector6);

        // then
        assertFalse(resultFollowsVector12);
        assertTrue(resultFollowsVector13);
        assertFalse(resultFollowsVector14);
        assertFalse(resultFollowsVector15);
        assertTrue(resultFollowsVector16);
    }

    @Test
    void upperRight() {
        // given
        Vector2d vector1 = new Vector2d(1, 1);
        Vector2d vector2 = new Vector2d(2, 2);
        Vector2d vector3 = new Vector2d(0, 0);
        Vector2d vector4 = new Vector2d(2, 0);
        Vector2d vector5 = new Vector2d(0, 2);
        Vector2d vector6 = new Vector2d(1, 1);

        // when
        Vector2d resultUpperRight12 = vector1.upperRight(vector2);
        Vector2d resultUpperRight13 = vector1.upperRight(vector3);
        Vector2d resultUpperRight14 = vector1.upperRight(vector4);
        Vector2d resultUpperRight15 = vector1.upperRight(vector5);
        Vector2d resultUpperRight16 = vector1.upperRight(vector6);

        // then
        assertEquals(new Vector2d(2, 2), resultUpperRight12);
        assertEquals(new Vector2d(1, 1), resultUpperRight13);
        assertEquals(new Vector2d(2, 1), resultUpperRight14);
        assertEquals(new Vector2d(1, 2), resultUpperRight15);
        assertEquals(new Vector2d(1, 1), resultUpperRight16);
    }

    @Test
    void lowerLeft() {
        // given
        Vector2d vector1 = new Vector2d(1, 1);
        Vector2d vector2 = new Vector2d(2, 2);
        Vector2d vector3 = new Vector2d(0, 0);
        Vector2d vector4 = new Vector2d(2, 0);
        Vector2d vector5 = new Vector2d(0, 2);
        Vector2d vector6 = new Vector2d(1, 1);

        // when
        Vector2d resultLowerRight12 = vector1.lowerLeft(vector2);
        Vector2d resultLowerRight13 = vector1.lowerLeft(vector3);
        Vector2d resultLowerRight14 = vector1.lowerLeft(vector4);
        Vector2d resultLowerRight15 = vector1.lowerLeft(vector5);
        Vector2d resultLowerRight16 = vector1.lowerLeft(vector6);

        // then
        assertEquals(new Vector2d(1, 1), resultLowerRight12);
        assertEquals(new Vector2d(0, 0), resultLowerRight13);
        assertEquals(new Vector2d(1, 0), resultLowerRight14);
        assertEquals(new Vector2d(0, 1), resultLowerRight15);
        assertEquals(new Vector2d(1, 1), resultLowerRight16);
    }

    @Test
    void add() {
        // given
        Vector2d vector1 = new Vector2d(10, 20);
        Vector2d vector2 = new Vector2d(0, 0);
        Vector2d vector3 = new Vector2d(-1, -1);
        Vector2d vector4 = new Vector2d(1, 1);
        Vector2d vector5 = new Vector2d(-1, 1);
        Vector2d vector6 = new Vector2d(1, -1);
        Vector2d vector7 = new Vector2d(-20, -50);

        // when
        Vector2d resultAdd12 = vector1.add(vector2);
        Vector2d resultAdd13 = vector1.add(vector3);
        Vector2d resultAdd14 = vector1.add(vector4);
        Vector2d resultAdd15 = vector1.add(vector5);
        Vector2d resultAdd16 = vector1.add(vector6);
        Vector2d resultAdd17 = vector1.add(vector7);

        // then
        assertEquals(new Vector2d(10, 20), resultAdd12);
        assertEquals(new Vector2d(9, 19), resultAdd13);
        assertEquals(new Vector2d(11, 21), resultAdd14);
        assertEquals(new Vector2d(9, 21), resultAdd15);
        assertEquals(new Vector2d(11, 19), resultAdd16);
        assertEquals(new Vector2d(-10, -30), resultAdd17);
    }

    @Test
    void substract() {
        // given
        Vector2d vector1 = new Vector2d(10, 20);
        Vector2d vector2 = new Vector2d(0, 0);
        Vector2d vector3 = new Vector2d(-1, -1);
        Vector2d vector4 = new Vector2d(1, 1);
        Vector2d vector5 = new Vector2d(-1, 1);
        Vector2d vector6 = new Vector2d(1, -1);
        Vector2d vector7 = new Vector2d(20, 50);

        // when
        Vector2d resultSubstract12 = vector1.subtract(vector2);
        Vector2d resultSubstract13 = vector1.subtract(vector3);
        Vector2d resultSubstract14 = vector1.subtract(vector4);
        Vector2d resultSubstract15 = vector1.subtract(vector5);
        Vector2d resultSubstract16 = vector1.subtract(vector6);
        Vector2d resultSubstract17 = vector1.subtract(vector7);

        // then
        assertEquals(new Vector2d(10, 20), resultSubstract12);
        assertEquals(new Vector2d(11, 21), resultSubstract13);
        assertEquals(new Vector2d(9, 19), resultSubstract14);
        assertEquals(new Vector2d(11, 19), resultSubstract15);
        assertEquals(new Vector2d(9, 21), resultSubstract16);
        assertEquals(new Vector2d(-10, -30), resultSubstract17);
    }

    @Test
    void opposite() {
        // given
        Vector2d vector1 = new Vector2d(0, 0);
        Vector2d vector2 = new Vector2d(1, 1);
        Vector2d vector3 = new Vector2d(-1, -1);
        Vector2d vector4 = new Vector2d(1, -1);
        Vector2d vector5 = new Vector2d(-1, 1);

        // when
        vector1 = vector1.opposite();
        vector2 = vector2.opposite();
        vector3 = vector3.opposite();
        vector4 = vector4.opposite();
        vector5 = vector5.opposite();

        // then
        assertEquals(new Vector2d(0, 0), vector1);
        assertEquals(new Vector2d(-1, -1), vector2);
        assertEquals(new Vector2d(1, 1), vector3);
        assertEquals(new Vector2d(-1, 1), vector4);
        assertEquals(new Vector2d(1, -1), vector5);
    }
}