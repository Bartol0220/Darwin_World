package agh.ics.oop.model;

import agh.ics.oop.model.genes.Genes;
import agh.ics.oop.model.genes.GenesFactory;
import agh.ics.oop.model.stats.Stats;

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
        Animal newAnimal = new Animal(position, genes, startingEnergy, energyProvidedByEatingGrass, null, null);
        stats.newAnimalPlaced(newAnimal);
        return newAnimal;
    }

    public Animal createAnimal(int dayNumber, Animal stronger, Animal weaker) {
        Genes kidGenes = genesFactory.makeGenes(stronger, weaker);
        Vector2d position = stronger.getPosition();
        Animal kid = new Animal(position, kidGenes, 2*energyUsedWhileBreeding, energyProvidedByEatingGrass, stronger, weaker);
        stats.newAnimalBorn(kid);
        return kid;
    }
}
