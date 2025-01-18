package agh.ics.oop.model;

import agh.ics.oop.model.genes.Genes;
import agh.ics.oop.model.genes.GenesFactory;
import agh.ics.oop.statistics.Stats;

public class AnimalCreator {
    private final Stats stats;
    private final GenesFactory genesFactory;
    private final int startingEnergy;
    private final int energyUsedWhileBreeding;
    private final int energyProvidedByEatingGrass;

    public AnimalCreator(int startingEnergy, int energyUsedWhileBreeding, int energyProvidedByEatingGrass, GenesFactory genesFactory, Stats stats) {
        this.startingEnergy = startingEnergy;
        this.genesFactory = genesFactory;
        this.energyProvidedByEatingGrass = energyProvidedByEatingGrass;
        this.energyUsedWhileBreeding = energyUsedWhileBreeding;
        this.stats = stats;
    }

    public Animal createStartingAnimal(Vector2d position) {
        Genes genes = genesFactory.makeStartingGenes();
        Animal newAnimal = new Animal(position, genes, startingEnergy, energyProvidedByEatingGrass);
        stats.newAnimalPlaced(newAnimal);
        return newAnimal;
    }

    public Animal createAnimal(Animal stronger, Animal weaker) {
        Genes kidGenes = genesFactory.makeGenes(stronger, weaker);
        Vector2d position = stronger.getPosition();
        Animal kid = new Animal(position, kidGenes, 2*energyUsedWhileBreeding, energyProvidedByEatingGrass, stronger, weaker);
        stats.newAnimalBorn(kid);
        return kid;
    }
}
