package agh.ics.oop;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    // RectangularMap

    @Test
    void runAnimalHasTheCorrectDirection() {
        // given
        RectangularMap map1 = new RectangularMap(5, 5);
        List<MoveDirection> directions1 = new ArrayList<>();
        List<Vector2d> positions1 = List.of(new Vector2d(2,2), new Vector2d(3,4));
        Simulation simulation1 = new Simulation(positions1, map1, directions1);
        List<Animal> animals1 = simulation1.getAnimals();

        RectangularMap map2 = new RectangularMap(5, 5);
        List<MoveDirection> directions2 = List.of(MoveDirection.RIGHT);
        List<Vector2d> positions2 = List.of(new Vector2d(1,1));
        Simulation simulation2 = new Simulation(positions2, map2, directions2);
        List<Animal> animals2 = simulation2.getAnimals();

        RectangularMap map3 = new RectangularMap(5, 5);
        List<MoveDirection> directions3 = List.of(MoveDirection.LEFT);
        List<Vector2d> positions3 = List.of(new Vector2d(1,1));
        Simulation simulation3 = new Simulation(positions3, map3, directions3);
        List<Animal> animals3 = simulation3.getAnimals();

        RectangularMap map4 = new RectangularMap(5, 5);
        List<MoveDirection> directions4 = List.of(MoveDirection.FORWARD);
        List<Vector2d> positions4 = List.of(new Vector2d(1,1));
        Simulation simulation4 = new Simulation(positions4, map4, directions4);
        List<Animal> animals4 = simulation4.getAnimals();

        RectangularMap map5 = new RectangularMap(5, 5);
        List<MoveDirection> directions5 = List.of(MoveDirection.BACKWARD);
        List<Vector2d> positions5 = List.of(new Vector2d(1,1));
        Simulation simulation5 = new Simulation(positions5, map5, directions5);
        List<Animal> animals5 = simulation5.getAnimals();

        RectangularMap map6 = new RectangularMap(5, 5);
        List<MoveDirection> directions6 = List.of(MoveDirection.RIGHT, MoveDirection.FORWARD, MoveDirection.RIGHT);
        List<Vector2d> positions6 = List.of(new Vector2d(1,1));
        Simulation simulation6 = new Simulation(positions6, map6, directions6);
        List<Animal> animals6 = simulation6.getAnimals();

        // when
        simulation1.run();
        simulation2.run();
        simulation3.run();
        simulation4.run();
        simulation5.run();
        simulation6.run();

        // then
        assertEquals(MapDirection.NORTH, animals1.get(0).getOrientation());
        assertEquals(MapDirection.NORTH, animals1.get(1).getOrientation());

        assertEquals(MapDirection.EAST, animals2.get(0).getOrientation());

        assertEquals(MapDirection.WEST, animals3.get(0).getOrientation());

        assertEquals(MapDirection.NORTH, animals4.get(0).getOrientation());

        assertEquals(MapDirection.NORTH, animals5.get(0).getOrientation());

        assertEquals(MapDirection.SOUTH, animals6.get(0).getOrientation());
    }

    @Test
    void runAnimalHasTheCorrectPosition() {
        // given
        List<MoveDirection> directions1 = List.of(
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.RIGHT,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD
        );
        List<Vector2d> positions1 = List.of(
                new Vector2d(1,1),
                new Vector2d(2,2),
                new Vector2d(0,4),
                new Vector2d(4,4)
        );
        RectangularMap map1 = new RectangularMap(5, 5);
        Simulation simulation1 = new Simulation(positions1, map1, directions1);
        List<Animal> animals1 = simulation1.getAnimals();

        List<MoveDirection> directions2 = List.of(
                MoveDirection.FORWARD,
                MoveDirection.LEFT,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT
        );
        List<Vector2d> positions2 = List.of(new Vector2d(2,2), new Vector2d(1,1));
        RectangularMap map2 = new RectangularMap(5, 5);
        Simulation simulation2 = new Simulation(positions2, map2, directions2);
        List<Animal> animals2 = simulation2.getAnimals();

        List<MoveDirection> directions3 = List.of(
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.BACKWARD,
                MoveDirection.LEFT,
                MoveDirection.BACKWARD,
                MoveDirection.RIGHT,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD
        );
        List<Vector2d> positions3 = List.of(new Vector2d(2,2));
        RectangularMap map3 = new RectangularMap(5, 5);
        Simulation simulation3 = new Simulation(positions3, map3, directions3);
        List<Animal> animals3 = simulation3.getAnimals();

        // when
        simulation1.run();
        simulation2.run();
        simulation3.run();

        // then
        assertEquals(new Vector2d(1, 3), animals1.get(0).getPosition());
        assertEquals(new Vector2d(2, 0), animals1.get(1).getPosition());
        assertEquals(new Vector2d(1, 4), animals1.get(2).getPosition());
        assertEquals(new Vector2d(3, 4), animals1.get(3).getPosition());

        assertEquals(new Vector2d(2, 0), animals2.get(0).getPosition());
        assertEquals(new Vector2d(1, 4), animals2.get(1).getPosition());

        assertEquals(new Vector2d(2, 0), animals3.get(0).getPosition());
    }

    @Test
    void runAnimalDoesNotGoOutsideTheMap() {
        // given
        List<MoveDirection> directions1 = List.of(
                MoveDirection.RIGHT,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD
        );
        List<Vector2d> positions1 = List.of(
                new Vector2d(4,2),
                new Vector2d(0, 2),
                new Vector2d(2, 4),
                new Vector2d(2, 0)
        );
        RectangularMap map1 = new RectangularMap(5, 5);
        Simulation simulation1 = new Simulation(positions1, map1, directions1);
        List<Animal> animals1 = simulation1.getAnimals();

        List<MoveDirection> directions2 = List.of(
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD
        );
        RectangularMap map2 = new RectangularMap(5, 5);
        List<Vector2d> positions2 = List.of(new Vector2d(2,0));
        Simulation simulation2 = new Simulation(positions2, map2, directions2);
        List<Animal> animals2 = simulation2.getAnimals();

        // when
        simulation1.run();
        simulation2.run();

        // then
        for(int i = 0; i < positions1.size(); i++) {
            assertEquals(positions1.get(i), animals1.get(i).getPosition());
        }

        assertEquals(new Vector2d(2, 4), animals2.get(0).getPosition());
    }

    @Test
    void runInputGivenAsAnArrayOfStrings() {
        // given
        String[] arguments1 = {"f", "r", "b", "l", "l"};
        List<Vector2d> positions1 = List.of(new Vector2d(2,2));
        RectangularMap map1 = new RectangularMap(5, 5);
        Simulation simulation1 = new Simulation(positions1, map1, OptionsParser.parse(arguments1));
        List<Animal> animals1 = simulation1.getAnimals();

        String[] arguments2 = {"f", "f", "r", "f"};
        List<Vector2d> positions2 = List.of(new Vector2d(1,1));
        RectangularMap map2 = new RectangularMap(5, 5);
        Simulation simulation2 = new Simulation(positions2, map2, OptionsParser.parse(arguments2));
        List<Animal> animals2 = simulation2.getAnimals();

        // when
        simulation1.run();
        simulation2.run();

        // then
        assertEquals(new Vector2d(1, 3), animals1.get(0).getPosition());
        assertEquals(MapDirection.WEST, animals1.get(0).getOrientation());
        assertEquals(new Vector2d(2, 3), animals2.get(0).getPosition());
    }

    @Test
    void animalsCannotBePlacedOnSameSpot() {
        // given
        List<MoveDirection> directions1 = new ArrayList<>();
        List<Vector2d> positions1 = List.of(new Vector2d(2,2), new Vector2d(2,2));
        RectangularMap map1 = new RectangularMap(5, 5);

        // when
        Simulation simulation1 = new Simulation(positions1, map1, directions1);
        List<Animal> animals1 = simulation1.getAnimals();

        // then
        assertEquals(1, animals1.size());
    }

    @Test
    void animalsCannotBeMovedOnSameSpot() {
        // given
        List<MoveDirection> directions1 = List.of(MoveDirection.FORWARD);
        List<Vector2d> positions1 = List.of(new Vector2d(1,1), new Vector2d(1,2));
        RectangularMap map1 = new RectangularMap(5, 5);
        Simulation simulation1 = new Simulation(positions1, map1, directions1);
        List<Animal> animals1 = simulation1.getAnimals();

        // when
        simulation1.run();

        // then
        assertEquals(new Vector2d(1, 1), animals1.get(0).getPosition());
        assertEquals(new Vector2d(1, 2), animals1.get(1).getPosition());
    }

    @Test
    void noAnimal() {
        // given
        List<MoveDirection> directions1 = List.of(MoveDirection.FORWARD);
        List<Vector2d> positions1 = new ArrayList<>();
        RectangularMap map1 = new RectangularMap(5, 5);
        Simulation simulation1 = new Simulation(positions1, map1, directions1);

        // when
        simulation1.run();

        // then
        // nie wiem co, ale nic się nie powinno stać
    }

    // GrassField

    @Test
    void grassFieldRunAnimalHasTheCorrectDirection() {
        // given
        GrassField map1 = new GrassField(10);
        List<MoveDirection> directions1 = new ArrayList<>();
        List<Vector2d> positions1 = List.of(new Vector2d(2,2), new Vector2d(3,4));
        Simulation simulation1 = new Simulation(positions1, map1, directions1);
        List<Animal> animals1 = simulation1.getAnimals();

        GrassField map2 = new GrassField(10);
        List<MoveDirection> directions2 = List.of(MoveDirection.RIGHT);
        List<Vector2d> positions2 = List.of(new Vector2d(1,1));
        Simulation simulation2 = new Simulation(positions2, map2, directions2);
        List<Animal> animals2 = simulation2.getAnimals();

        GrassField map3 = new GrassField(10);
        List<MoveDirection> directions3 = List.of(MoveDirection.LEFT);
        List<Vector2d> positions3 = List.of(new Vector2d(1,1));
        Simulation simulation3 = new Simulation(positions3, map3, directions3);
        List<Animal> animals3 = simulation3.getAnimals();

        GrassField map4 = new GrassField(10);
        List<MoveDirection> directions4 = List.of(MoveDirection.FORWARD);
        List<Vector2d> positions4 = List.of(new Vector2d(1,1));
        Simulation simulation4 = new Simulation(positions4, map4, directions4);
        List<Animal> animals4 = simulation4.getAnimals();

        GrassField map5 = new GrassField(10);
        List<MoveDirection> directions5 = List.of(MoveDirection.BACKWARD);
        List<Vector2d> positions5 = List.of(new Vector2d(1,1));
        Simulation simulation5 = new Simulation(positions5, map5, directions5);
        List<Animal> animals5 = simulation5.getAnimals();

        GrassField map6 = new GrassField(10);
        List<MoveDirection> directions6 = List.of(MoveDirection.RIGHT, MoveDirection.FORWARD, MoveDirection.RIGHT);
        List<Vector2d> positions6 = List.of(new Vector2d(1,1));
        Simulation simulation6 = new Simulation(positions6, map6, directions6);
        List<Animal> animals6 = simulation6.getAnimals();

        // when
        simulation1.run();
        simulation2.run();
        simulation3.run();
        simulation4.run();
        simulation5.run();
        simulation6.run();

        // then
        assertEquals(MapDirection.NORTH, animals1.get(0).getOrientation());
        assertEquals(MapDirection.NORTH, animals1.get(1).getOrientation());

        assertEquals(MapDirection.EAST, animals2.get(0).getOrientation());

        assertEquals(MapDirection.WEST, animals3.get(0).getOrientation());

        assertEquals(MapDirection.NORTH, animals4.get(0).getOrientation());

        assertEquals(MapDirection.NORTH, animals5.get(0).getOrientation());

        assertEquals(MapDirection.SOUTH, animals6.get(0).getOrientation());
    }

    @Test
    void grassFieldrunAnimalHasTheCorrectPosition() {
        // given
        List<MoveDirection> directions1 = List.of(
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.RIGHT,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD
        );
        List<Vector2d> positions1 = List.of(
                new Vector2d(1,1),
                new Vector2d(2,2),
                new Vector2d(0,4),
                new Vector2d(4,4)
        );
        GrassField map1 = new GrassField(10);
        Simulation simulation1 = new Simulation(positions1, map1, directions1);
        List<Animal> animals1 = simulation1.getAnimals();

        List<MoveDirection> directions2 = List.of(
                MoveDirection.FORWARD,
                MoveDirection.LEFT,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT
        );
        List<Vector2d> positions2 = List.of(new Vector2d(2,2), new Vector2d(1,1));
        GrassField map2 = new GrassField(10);
        Simulation simulation2 = new Simulation(positions2, map2, directions2);
        List<Animal> animals2 = simulation2.getAnimals();

        List<MoveDirection> directions3 = List.of(
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.BACKWARD,
                MoveDirection.LEFT,
                MoveDirection.BACKWARD,
                MoveDirection.RIGHT,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD
        );
        List<Vector2d> positions3 = List.of(new Vector2d(2,2));
        GrassField map3 = new GrassField(10);
        Simulation simulation3 = new Simulation(positions3, map3, directions3);
        List<Animal> animals3 = simulation3.getAnimals();

        // when
        simulation1.run();
        simulation2.run();
        simulation3.run();

        // then
        assertEquals(new Vector2d(1, 3), animals1.get(0).getPosition());
        assertEquals(new Vector2d(2, 0), animals1.get(1).getPosition());
        assertEquals(new Vector2d(1, 4), animals1.get(2).getPosition());
        assertEquals(new Vector2d(3, 4), animals1.get(3).getPosition());

        assertEquals(new Vector2d(2, 0), animals2.get(0).getPosition());
        assertEquals(new Vector2d(1, 4), animals2.get(1).getPosition());

        assertEquals(new Vector2d(2, 0), animals3.get(0).getPosition());
    }

    @Test
    void grassFieldMoves() {
        // given
        List<MoveDirection> directions1 = List.of(
                MoveDirection.LEFT,
                MoveDirection.LEFT,
                MoveDirection.RIGHT,
                MoveDirection.LEFT,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.LEFT,
                MoveDirection.LEFT,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.BACKWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD
        );
        List<Vector2d> positions1 = List.of(
                new Vector2d(0,0),
                new Vector2d(2,2),
                new Vector2d(4,4),
                new Vector2d(6,6)
        );
        GrassField map1 = new GrassField(20);
        Simulation simulation1 = new Simulation(positions1, map1, directions1);
        List<Animal> animals1 = simulation1.getAnimals();

        // when
        simulation1.run();

        // then
        assertEquals(new Vector2d(-1, -2), animals1.get(0).getPosition());
        assertEquals(new Vector2d(-2, 3), animals1.get(1).getPosition());
        assertEquals(new Vector2d(5, 2), animals1.get(2).getPosition());
        assertEquals(new Vector2d(4, 5), animals1.get(3).getPosition());
    }
}