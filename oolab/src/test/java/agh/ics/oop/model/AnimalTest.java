package agh.ics.oop.model;

import agh.ics.oop.errors.IncorrectPositionException;
import agh.ics.oop.model.genes.GeneMutator;
import agh.ics.oop.model.genes.Genes;
import agh.ics.oop.model.genes.GenesFactory;
import agh.ics.oop.model.genes.SlightCorrection;
import agh.ics.oop.model.grass.AbstractGrassMaker;
import agh.ics.oop.model.grass.GrassMakerDeadAnimal;
import agh.ics.oop.statistics.Stats;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void animalsBreedEachother() {
        GlobeMap map = new GlobeMap(3, 3, 0);
        GeneMutator geneMutator = new SlightCorrection(0, 3);
        GenesFactory genesFactory = new GenesFactory(geneMutator, 5);
        AbstractGrassMaker grassMaker = new GrassMakerDeadAnimal(2, 2, map);
        Stats stats = new Stats(map, grassMaker, 5, 10, 6);
        AnimalCreator animalCreator = new AnimalCreator(10, 5, 10, genesFactory, stats);
        Genes genes = new Genes(5);

        Vector2d position = new Vector2d(1, 1);
        Animal stronger = new Animal(position, genes, 19, 10);
        Animal weaker = new Animal(position, genes, 13, 10);

        try {
            map.place(stronger);
            map.place(weaker);
        } catch (IncorrectPositionException exception) {
            exception.printStackTrace();
        }

        Animal kid = stronger.breed(weaker, 5, animalCreator);

        assertEquals(14, stronger.getEnergy());
        assertEquals(8, weaker.getEnergy());
        assertEquals(1, stronger.getAnimalStats().getChildrenCount());
        assertEquals(1, weaker.getAnimalStats().getChildrenCount());
        assertEquals(10, kid.getEnergy());
    }

    @Test
    void animalEats() {
        Genes genes = new Genes(5);
        Vector2d positiion = new Vector2d(2, 2);
        Animal animal = new Animal(positiion, genes, 12, 10);

        animal.eat();

        assertEquals(22, animal.getEnergy());
    }

    @Test
    void comparingAnimalsWorks() {
        GlobeMap map = new GlobeMap(3, 3, 0);
        GeneMutator geneMutator = new SlightCorrection(0, 3);
        GenesFactory genesFactory = new GenesFactory(geneMutator, 5);
        AbstractGrassMaker grassMaker = new GrassMakerDeadAnimal(2, 2, map);
        Stats stats = new Stats(map, grassMaker, 5, 10, 6);
        AnimalCreator animalCreator = new AnimalCreator(10, 0, 10, genesFactory, stats);

        Genes genes = new Genes(5);
        Vector2d positiion = new Vector2d(2, 2);

        Animal strongest = new Animal(positiion, genes, 12, 10);
        Animal younger = new Animal(positiion, genes, 10, 10);
        Animal older = new Animal(positiion, genes, 11, 10);
        Animal olderWithChildren = new Animal(positiion, genes, 11, 10);
        Animal breeder = new Animal(positiion, genes, 5, 10);

        older.move(map);
        olderWithChildren.move(map);
        olderWithChildren.breed(breeder, 0, animalCreator);


        assertTrue(0 > strongest.compareTo(older));
        assertEquals(younger.getEnergy(), older.getEnergy());
        assertTrue(0 > older.compareTo(younger));
        assertEquals(older.getAnimalStats().getAge(), olderWithChildren.getAnimalStats().getAge());
        assertEquals(1, olderWithChildren.getAnimalStats().getChildrenCount());
        assertTrue(0 < older.compareTo(olderWithChildren));
    }
}