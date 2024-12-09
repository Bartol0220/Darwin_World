package agh.ics.oop.model;

import agh.ics.oop.Simulation;
import agh.ics.oop.World;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GrassFieldTest {
    @Test
    void isGrassNumberCorrect() {
        // given
        List<MoveDirection> directions1 = new ArrayList<>();
        List<Vector2d> positions1 = List.of(
                new Vector2d(-1,-1)
        );
        GrassField map1 = new GrassField(20);
        Simulation simulation1 = new Simulation(positions1, map1, directions1);

        // when
        simulation1.run();
        List<WorldElement> elements1 = map1.getElements();

        int n = 0;
        for(WorldElement element : elements1) {
            if(element instanceof Grass) {
                n += 1;
            }
        }

        // then
        assertEquals(20, n);
    }

    @Test
    void toStringGrass() {
        // given
        List<MoveDirection> directions1 = new ArrayList<>();
        List<Vector2d> positions1 = List.of(
                new Vector2d(-1,-1) // pozycja poza obszarem losowania dla trawy
        );
        GrassField map1 = new GrassField(20);
        Simulation simulation1 = new Simulation(positions1, map1, directions1);

        // when
        simulation1.run();
        String map1String = map1.toString();
        int count = 0;

        for (int i = 0; i < map1String.length(); i++) {
            if (map1String.charAt(i) == '*') {
                count++;
            }
        }
        System.out.println(map1String);

        // then
        assertEquals(20, count);
    }

}