package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AbstractWorldMapTest {
    @Test
    void placeException() {
        // given
        List<MoveDirection> directions1 = List.of(MoveDirection.FORWARD);
        List<Vector2d> positions1 = List.of(new Vector2d(1,1), new Vector2d(1,1));
        RectangularMap map1 = new RectangularMap(5, 5, 0);
        Animal animal1 = new Animal(positions1.getFirst());
        Animal animal2 = new Animal(positions1.getLast());

        // when, then
        assertDoesNotThrow(() -> map1.place(animal1));
        assertThrows(IncorrectPositionException.class, () -> map1.place(animal2));
    }

}