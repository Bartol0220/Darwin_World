package agh.ics.oop.model;

import agh.ics.oop.model.genes.Genes;
import agh.ics.oop.model.genes.GenesFactory;

public class AnimalCreator {
    private final int startingEnergy;
    private final GenesFactory genesFactory;
    private final int energyUsedWhileBreeding;
    private final int energyProvidedByEatingGrass;
    private final Stats stats;

    public AnimalCreator(int startingEnergy, int energyUsedWhileBreeding, int energyProvidedByEatingGrass, GenesFactory genesFactory, Stats stats) {
        this.startingEnergy = startingEnergy;
        this.genesFactory = genesFactory;
        this.energyProvidedByEatingGrass = energyProvidedByEatingGrass;
        this.energyUsedWhileBreeding = energyUsedWhileBreeding;
        this.stats = stats;
    }

    public Animal createStartingAnimal(Vector2d position, int dayNumber) {
        Genes genes = genesFactory.makeStartingGenes();
        stats.newAnimalPlaced(genes.getGenes());
        return new Animal(position, genes,startingEnergy, energyProvidedByEatingGrass);
    }

    public Animal createAnimal(int dayNumber, Animal stronger, Animal weaker) {
        Genes kidGenes = genesFactory.makeGenes(stronger, weaker);
        Vector2d position = stronger.getPosition();
        stats.newAnimalBorn(kidGenes.getGenes());
        // czy on dostaje energie "od obu rodzicow" (2*energy) czy po prostu energy?
        return new Animal(position, kidGenes, 2*energyUsedWhileBreeding, energyProvidedByEatingGrass);
    }

    public void reportDeadAnimal(int dayNumber, Animal animal) {
        stats.animalDied(animal.getGenes());
        stats.calculateNewAverageLifeSpan(animal.getAnimalStats().getAge());

    }
}
