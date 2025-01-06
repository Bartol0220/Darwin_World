package agh.ics.oop.model;

import agh.ics.oop.Simulation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {
    @Test
    void toStringWorksWithoutMovement() { // It also tests methods isOccupied and objectAt used by MapVisualizer.
        // give
        List<MoveDirection> directions1 = new ArrayList<>();
        List<Vector2d> positions1 = List.of(new Vector2d(1,1), new Vector2d(3,3));
        RectangularMap map1 = new RectangularMap(5, 5, 0);

        // when
        Simulation simulation1 = new Simulation(positions1, map1, directions1);

        // then
        assertEquals("""
                 y\\x  0 1 2 3 4\r
                  5: -----------\r
                  4: | | | | | |\r
                  3: | | | |N| |\r
                  2: | | | | | |\r
                  1: | |N| | | |\r
                  0: | | | | | |\r
                 -1: -----------\r
                """, map1.toString());
    }

    @Test
    void toStringWorksAfterMovement() {
        // give
        List<MoveDirection> directions1 = List.of(MoveDirection.FORWARD, MoveDirection.FORWARD);
        List<Vector2d> positions1 = List.of(new Vector2d(1,1), new Vector2d(3,3));
        RectangularMap map1 = new RectangularMap(5, 5, 0);

        // when
        Simulation simulation1 = new Simulation(positions1, map1, directions1);
        simulation1.run();

        // then
        assertEquals("""
                 y\\x  0 1 2 3 4\r
                  5: -----------\r
                  4: | | | |N| |\r
                  3: | | | | | |\r
                  2: | |N| | | |\r
                  1: | | | | | |\r
                  0: | | | | | |\r
                 -1: -----------\r
                """, map1.toString());
    }

    @Test
    void toStringDirections() {
        // give
        List<MoveDirection> directions1 = List.of(
                MoveDirection.RIGHT,
                MoveDirection.LEFT,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT
        );
        List<Vector2d> positions1 = List.of(
                new Vector2d(0,0),
                new Vector2d(1,1),
                new Vector2d(2,2),
                new Vector2d(3,3));
        RectangularMap map1 = new RectangularMap(5, 5, 0);

        // when
        Simulation simulation1 = new Simulation(positions1, map1, directions1);
        simulation1.run();

        // then
        assertEquals("""
                 y\\x  0 1 2 3 4\r
                  5: -----------\r
                  4: | | | |N| |\r
                  3: | | | | | |\r
                  2: | | |E| | |\r
                  1: | |W| | | |\r
                  0: |S| | | | |\r
                 -1: -----------\r
                """, map1.toString());
    }
}