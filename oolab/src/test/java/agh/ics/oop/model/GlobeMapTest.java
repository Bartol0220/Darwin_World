package agh.ics.oop.model;

import agh.ics.oop.errors.IncorrectPositionException;
import agh.ics.oop.model.genes.GeneMutator;
import agh.ics.oop.model.genes.Genes;
import agh.ics.oop.model.genes.GenesFactory;
import agh.ics.oop.model.genes.SlightCorrection;
import agh.ics.oop.model.grass.AbstractGrassMaker;
import agh.ics.oop.model.grass.GrassMakerDeadAnimal;
import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.statistics.Stats;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GlobeMapTest {

    @Test
    void canHandleBoundPositions() {
        GlobeMap map = new GlobeMap(3, 3, 0);
        Vector2d outOfRightBound = new Vector2d(3, 2);
        Vector2d correctPosition = new Vector2d(1, 1);
        Vector2d outOfLeftBound = new Vector2d(-1, 1);

        assertEquals(new Vector2d(0, 2), map.handleBoundsPositions(outOfRightBound));
        assertEquals(new Vector2d(1, 1), map.handleBoundsPositions(correctPosition));
        assertEquals(new Vector2d(2, 1), map.handleBoundsPositions(outOfLeftBound));
    }

    @Test
    void cannotMoveOutOfBounds() {
        GlobeMap map = new GlobeMap(3, 3, 0);
        Vector2d outOfUpperBound = new Vector2d(1, 4);
        Vector2d correctPosition = new Vector2d(1, 1);
        Vector2d outOfLowerBound = new Vector2d(-1, 1);

        assertFalse(map.canMoveTo(outOfUpperBound));
        assertTrue(map.canMoveTo(correctPosition));
        assertFalse(map.canMoveTo(outOfLowerBound));
    }

    @Test
    void animalIsPlacedOrThrowsException() {
        GlobeMap map = new GlobeMap(3, 3, 0);
        Genes genes = new Genes(5);

        Vector2d position1 = new Vector2d(2, 2);
        Vector2d position2 = new Vector2d(0, 0);
        Vector2d position3 = new Vector2d(-1, -1);

        Animal animal1 = new Animal(position1, genes, 0, 10);
        Animal animal2 = new Animal(position2, genes, 0, 10);
        Animal animal3 = new Animal(position2, genes, 10, 10);
        Animal animal4 = new Animal(position3, genes, 10, 10);

        try {
            map.place(animal1);
            map.place(animal2);
            map.place(animal3);
        } catch (IncorrectPositionException exception) {
            exception.printStackTrace();
        }

        assertEquals(1, map.getMapField(position1).getNumberOfAnimals());
        assertEquals(2, map.getMapField(position2).getNumberOfAnimals());
        assertThrows(IncorrectPositionException.class, () -> map.place(animal4));
    }

    @Test
    void addsGrass() {
        GlobeMap map = new GlobeMap(3, 3, 0);

        Vector2d position = new Vector2d(2, 2);
        map.addGrass(position);

        assertTrue(map.getMapField(position).hasGrass());
    }

    @Test
    void animalMovesAndLoosesEnergy() {
        GlobeMap map = new GlobeMap(3, 3, 0);
        Vector2d placedPosition = new Vector2d(1, 1);
        Genes genes = new Genes(5);
        Animal animal = new Animal(placedPosition, genes, 5, 10);

        map.move(animal);

        assertEquals(4, animal.getEnergy());
        assertNotEquals(placedPosition, animal.getPosition());
    }

    @Test
    void animalEats() {
        GlobeMap map = new GlobeMap(3, 3, 0);
        //not used but places grass upon being created
        AbstractGrassMaker grassMaker = new GrassMakerDeadAnimal(9, 2, map);
        //places grass on every position, so animal has to eat it

        Vector2d position = new Vector2d(2, 2);
        Genes genes = new Genes(5);
        Animal animal = new Animal(position, genes, 5, 10);

        map.move(animal);
        map.feedAnimals();

        assertEquals(14, animal.getEnergy());
    }

    @Test
    void findsOccupiedFields() {
        GlobeMap map = new GlobeMap(3, 3, 0);
        Vector2d animalPosition = new Vector2d(2, 2);
        Vector2d grassPosition = new Vector2d(1, 2);
        Vector2d freePosition = new Vector2d(1, 1);
        Genes genes = new Genes(5);
        Animal animal = new Animal(animalPosition, genes, 5, 10);
        map.addGrass(grassPosition);


        try {
            map.place(animal);
        } catch (IncorrectPositionException exception) {
            exception.printStackTrace();
        }

        assertEquals(animal, map.objectAt(animalPosition).orElse(null));
        assertEquals(map.getMapField(grassPosition).getGrass().orElse(null), map.objectAt(grassPosition).orElse(null));
        assertNull(map.objectAt(freePosition).orElse(null));
    }

    @Test
    void isOccupiedByAnimalWorksProperly() {
        GlobeMap map = new GlobeMap(3, 3, 0);
        Genes genes = new Genes(5);

        Vector2d oneAnimalPosition = new Vector2d(2, 2);
        Vector2d manyAnimalsPostiton = new Vector2d(0, 0);
        Vector2d freePosition = new Vector2d(1, 2);

        Animal animal1 = new Animal(oneAnimalPosition, genes, 0, 10);
        Animal animal2 = new Animal(manyAnimalsPostiton, genes, 0, 10);
        Animal animal3 = new Animal(manyAnimalsPostiton, genes, 10, 10);

        try {
            map.place(animal1);
            map.place(animal2);
            map.place(animal3);
        } catch (IncorrectPositionException exception) {
            exception.printStackTrace();
        }

        assertTrue(map.isOccupiedByAnimal(manyAnimalsPostiton));
        assertFalse(map.isOccupiedByAnimal(freePosition));
    }

    @Test
    void breedingAnimals() {
        GlobeMap map = new GlobeMap(3, 3, 0);
        GeneMutator geneMutator = new SlightCorrection(0, 3);
        GenesFactory genesFactory = new GenesFactory(geneMutator, 5);
        AbstractGrassMaker grassMaker = new GrassMakerDeadAnimal(2, 2, map);
        Stats stats = new Stats(map, grassMaker, 5, 10, 6);
        Genes genes = new Genes(5);
        AnimalCreator animalCreator = new AnimalCreator(10, 10, 10, genesFactory, stats);
        Breeding breeding = new Breeding(10, 10, map, animalCreator);
        Simulation simulation = new Simulation(map, grassMaker, breeding, animalCreator, 0, stats);

        Vector2d position = new Vector2d(2, 2);
        Animal weakAnimal1 = new Animal(position, genes, 5, 10);
        Animal weakAnimal2 = new Animal(position, genes, 3, 10);
        Animal weakAnimal3 = new Animal(position, genes, 3, 10);
        Animal weakAnimal4 = new Animal(position, genes, 2, 10);
        Animal strongAnimal1 = new Animal(position, genes, 16, 10);
        Animal strongAnimal2 = new Animal(position, genes, 15, 10);
        Animal strongAnimal3 = new Animal(position, genes, 12, 10);

        try {
            map.place(weakAnimal1);
            map.place(weakAnimal2);
            map.place(weakAnimal3);
            map.place(weakAnimal4);
            map.place(strongAnimal1);
            map.place(strongAnimal2);
            map.place(strongAnimal3);
        } catch (IncorrectPositionException exception) {
            exception.printStackTrace();
        }

        breeding.breedAnimals(simulation);
        map.getMapField(position).getNumberOfAnimals();

        assertEquals(8, map.getMapField(position).getNumberOfAnimals());
        assertEquals(6, strongAnimal1.getEnergy());
        assertEquals(5, strongAnimal2.getEnergy());
    }
}