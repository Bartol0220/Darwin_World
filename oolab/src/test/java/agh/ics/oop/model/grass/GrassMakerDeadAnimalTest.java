package agh.ics.oop.model.grass;

import agh.ics.oop.model.*;
import agh.ics.oop.model.genes.GeneMutator;
import agh.ics.oop.model.genes.Genes;
import agh.ics.oop.model.genes.GenesFactory;
import agh.ics.oop.model.genes.SlightCorrection;
import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.statistics.Stats;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrassMakerDeadAnimalTest {

    @Test
    void makesPositionsBetter() {
        GlobeMap map = new GlobeMap(3, 3, 0);
        GrassMakerDeadAnimal grassMaker = new GrassMakerDeadAnimal(3, 3, map);
        Vector2d placedPosition = new Vector2d(0, 0);
        Genes genes = new Genes(5);
        Animal animal = new Animal(placedPosition, genes, 0, 10);

        MapField betterPosition1 = map.getMapField(placedPosition);
        MapField betterPosition2 = map.getMapField(new Vector2d(0, 1));
        MapField betterPosition3 = map.getMapField(new Vector2d(1, 0));
        MapField betterPosition4 = map.getMapField(new Vector2d(1, 1));
        MapField betterPosition5 = map.getMapField(new Vector2d(2, 0));
        MapField betterPosition6 = map.getMapField(new Vector2d(2, 1));

        grassMaker.newDay(5);
        grassMaker.animalDied(animal);

        assertEquals(6, grassMaker.getTemporarilyBetterPositions().size());
        assertTrue(grassMaker.getTemporarilyBetterPositions().contains(betterPosition1));
        assertTrue(grassMaker.getTemporarilyBetterPositions().contains(betterPosition2));
        assertTrue(grassMaker.getTemporarilyBetterPositions().contains(betterPosition3));
        assertTrue(grassMaker.getTemporarilyBetterPositions().contains(betterPosition4));
        assertTrue(grassMaker.getTemporarilyBetterPositions().contains(betterPosition5));
        assertTrue(grassMaker.getTemporarilyBetterPositions().contains(betterPosition6));
    }

    @Test
    void removesAfterFewDays() {
        //given
        GlobeMap map = new GlobeMap(3, 3, 0);
        GrassMakerDeadAnimal grassMaker = new GrassMakerDeadAnimal(3, 3, map);
        Vector2d placedPosition = new Vector2d(0, 0);
        Genes genes = new Genes(5);
        Animal animal = new Animal(placedPosition, genes, 0, 10);

        MapField betterPosition1 = map.getMapField(placedPosition);
        MapField betterPosition2 = map.getMapField(new Vector2d(0, 1));
        MapField betterPosition3 = map.getMapField(new Vector2d(1, 0));
        MapField betterPosition4 = map.getMapField(new Vector2d(1, 1));
        MapField betterPosition5 = map.getMapField(new Vector2d(2, 0));
        MapField betterPosition6 = map.getMapField(new Vector2d(2, 1));

        //when
        grassMaker.newDay(5);
        grassMaker.animalDied(animal);
        grassMaker.newDay(9);
        grassMaker.changeFieldsPositionToWorseAfterFewDays();

        //then
        assertEquals(6, grassMaker.getTemporarilyBetterPositions().size());
        assertTrue(grassMaker.getTemporarilyBetterPositions().contains(betterPosition1));
        assertTrue(grassMaker.getTemporarilyBetterPositions().contains(betterPosition2));
        assertTrue(grassMaker.getTemporarilyBetterPositions().contains(betterPosition3));
        assertTrue(grassMaker.getTemporarilyBetterPositions().contains(betterPosition4));
        assertTrue(grassMaker.getTemporarilyBetterPositions().contains(betterPosition5));
        assertTrue(grassMaker.getTemporarilyBetterPositions().contains(betterPosition6));

        //when
        grassMaker.newDay(10);
        grassMaker.changeFieldsPositionToWorseAfterFewDays();

        //then
        assertEquals(0, grassMaker.getTemporarilyBetterPositions().size());
        assertFalse(grassMaker.getTemporarilyBetterPositions().contains(betterPosition1));
        assertFalse(grassMaker.getTemporarilyBetterPositions().contains(betterPosition2));
        assertFalse(grassMaker.getTemporarilyBetterPositions().contains(betterPosition3));
        assertFalse(grassMaker.getTemporarilyBetterPositions().contains(betterPosition4));
        assertFalse(grassMaker.getTemporarilyBetterPositions().contains(betterPosition5));
        assertFalse(grassMaker.getTemporarilyBetterPositions().contains(betterPosition6));
    }

}