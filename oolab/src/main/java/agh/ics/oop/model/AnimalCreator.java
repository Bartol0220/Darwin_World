package agh.ics.oop.model;

import agh.ics.oop.model.genes.Genes;
import agh.ics.oop.model.genes.GenesFactory;

public class AnimalCreator {
    private final int startingEnergy;
    private final GenesFactory genesFactory;
    private final int energyUsedWhileBreeding;
    private final int energyProvidedByEatingGrass;

    public AnimalCreator(int startingEnergy, int energyUsedWhileBreeding, int energyProvidedByEatingGrass, GenesFactory genesFactory) {
        this.startingEnergy = startingEnergy;
        this.genesFactory = genesFactory;
        this.energyProvidedByEatingGrass = energyProvidedByEatingGrass;
        this.energyUsedWhileBreeding = energyUsedWhileBreeding;
    }

    public Animal createStartingAnimal(Vector2d position, int dayNumber) {
        Genes genes = genesFactory.makeStartingGenes();
        return new Animal(position, genes, dayNumber, startingEnergy, energyProvidedByEatingGrass);
    }

    public Animal createAnimal(int dayNumber, Animal stronger, Animal weaker) {
        Genes kidGenes = genesFactory.makeGenes(stronger, weaker);
        Vector2d position = stronger.getPosition();
        // czy on dostaje energie "od obu rodzicow" (2*energy) czy po prostu energy?
        return new Animal(position, kidGenes, dayNumber, 2*energyUsedWhileBreeding, energyProvidedByEatingGrass);
    }
}
