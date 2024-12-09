package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {

    @Test
    void next() {
        // given
        MapDirection direction1 = MapDirection.NORTH;
        MapDirection direction2 = MapDirection.EAST;
        MapDirection direction3 = MapDirection.SOUTH;
        MapDirection direction4 = MapDirection.WEST;

        // when
        direction1 = direction1.next();
        direction2 = direction2.next();
        direction3 = direction3.next();
        direction4 = direction4.next();

        // then
        assertEquals(MapDirection.EAST, direction1);
        assertEquals(MapDirection.SOUTH, direction2);
        assertEquals(MapDirection.WEST, direction3);
        assertEquals(MapDirection.NORTH, direction4);
    }

    @Test
    void previous() {
        // given
        MapDirection direction1 = MapDirection.NORTH;
        MapDirection direction2 = MapDirection.EAST;
        MapDirection direction3 = MapDirection.SOUTH;
        MapDirection direction4 = MapDirection.WEST;

        // when
        direction1 = direction1.previous();
        direction2 = direction2.previous();
        direction3 = direction3.previous();
        direction4 = direction4.previous();

        // then
        assertEquals(MapDirection.WEST, direction1);
        assertEquals(MapDirection.NORTH, direction2);
        assertEquals(MapDirection.EAST, direction3);
        assertEquals(MapDirection.SOUTH, direction4);
    }

}