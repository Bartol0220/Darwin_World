package agh.ics.oop;

import agh.ics.oop.model.errors.*;

public class SimulationConfig {
    private final int height;
    private final int width;
    private final int startGrassNumber;
    private final int energyProvidedByEatingGrass;
    private final int dayGrassNumber;
    private final int grassMakerVariant;
    private final int startNumberOfAnimals;
    private final int startingEnergy;
    private final int energyNeededForBreeding;
    private final int energyUsedWhileBreeding;
    private final int minimumNumberOfMutations;
    private final int maximumNumberOfMutations;
    private final int genesMutatorVariant;
    private final int genesNumber;

    public SimulationConfig(int height,
                            int width,
                            int startGrassNumber,
                            int energyProvidedByEatingGrass,
                            int dayGrassNumber,
                            int grassMakerVariant,
                            int startNumberOfAnimals,
                            int startingEnergy,
                            int energyNeededForBreeding,
                            int energyUsedWhileBreeding,
                            int minimumNumberOfMutations,
                            int maximumNumberOfMutations,
                            int genesMutatorVariant,
                            int genesNumber) throws HasToBePositiveException, HasToBeBit, BreedingCanNotKillAnimals, CanNotBeNegativeException, MutationChangesCanNotExceedSize, MinMaxGeneException {

        this.height = height;
        if (height <= 0) throw new HasToBePositiveException("Height");

        this.width = width;
        if (width <= 0) throw new HasToBePositiveException("Width");

        this.startGrassNumber = startGrassNumber;
        if (startGrassNumber < 0) throw new CanNotBeNegativeException("Initial number of grass");

        this.energyProvidedByEatingGrass = energyProvidedByEatingGrass;
        if (energyProvidedByEatingGrass < 0) throw new CanNotBeNegativeException("Energy gained from eating");

        this.dayGrassNumber = dayGrassNumber;
        if (dayGrassNumber < 0) throw new CanNotBeNegativeException("Number of grass growing every day");

        this.grassMakerVariant = grassMakerVariant;
        if (grassMakerVariant != 0 && grassMakerVariant != 1) throw new HasToBeBit("Grass growing variant");

        this.startNumberOfAnimals = startNumberOfAnimals;
        if (startNumberOfAnimals <= 0) throw new HasToBePositiveException("Initial number of animals");

        this.startingEnergy = startingEnergy;
        if (startingEnergy <= 0) throw new HasToBePositiveException("Initial energy of animals");

        this.energyNeededForBreeding = energyNeededForBreeding;
        if (energyNeededForBreeding < 0) throw new CanNotBeNegativeException("Energy needed to consider the animal as ready to breed");

        this.energyUsedWhileBreeding = energyUsedWhileBreeding;
        if (energyUsedWhileBreeding < 0) throw new CanNotBeNegativeException("Energy used during reproduction");

        if (energyUsedWhileBreeding > energyNeededForBreeding) throw new BreedingCanNotKillAnimals();

        this.minimumNumberOfMutations = minimumNumberOfMutations;
        if (minimumNumberOfMutations < 0) throw new CanNotBeNegativeException("Minimum number of mutations");

        this.maximumNumberOfMutations = maximumNumberOfMutations;
        if (maximumNumberOfMutations < 0) throw new CanNotBeNegativeException("Maximum number of mutations");

        this.genesNumber = genesNumber;
        if (genesNumber <= 0) throw new HasToBePositiveException("Animal genome length");

        if (minimumNumberOfMutations > maximumNumberOfMutations) throw new MinMaxGeneException();
        if (maximumNumberOfMutations > genesNumber) throw new MutationChangesCanNotExceedSize();

        this.genesMutatorVariant = genesMutatorVariant;
        if (genesMutatorVariant != 0 && genesMutatorVariant != 1) throw new HasToBeBit("Mutation variant");
    }

    public SimulationConfig(int[] configArray) throws HasToBePositiveException, HasToBeBit, BreedingCanNotKillAnimals {
        this.height = configArray[0];
        if (height <= 0) throw new HasToBePositiveException("Height");

        this.width = configArray[1];
        if (width <= 0) throw new HasToBePositiveException("Width");

        this.startGrassNumber = configArray[2];
        if (startGrassNumber < 0) throw new CanNotBeNegativeException("Initial number of grass");

        this.energyProvidedByEatingGrass = configArray[3];
        if (energyProvidedByEatingGrass < 0) throw new CanNotBeNegativeException("Energy gained from eating");

        this.dayGrassNumber = configArray[4];
        if (dayGrassNumber < 0) throw new CanNotBeNegativeException("Number of grass growing every day");

        this.grassMakerVariant = configArray[5];
        if (grassMakerVariant != 0 && grassMakerVariant != 1) throw new HasToBeBit("Grass growing variant");

        this.startNumberOfAnimals = configArray[6];
        if (startNumberOfAnimals <= 0) throw new HasToBePositiveException("Initial number of animals");

        this.startingEnergy = configArray[7];
        if (startingEnergy <= 0) throw new HasToBePositiveException("Initial energy of animals");

        this.energyNeededForBreeding = configArray[8];
        if (energyNeededForBreeding < 0) throw new CanNotBeNegativeException("Energy needed to consider the animal as ready to breed");

        this.energyUsedWhileBreeding = configArray[9];
        if (energyUsedWhileBreeding < 0) throw new CanNotBeNegativeException("Energy used during reproduction");

        if (energyUsedWhileBreeding > energyNeededForBreeding) throw new BreedingCanNotKillAnimals();

        this.minimumNumberOfMutations = configArray[10];
        if (minimumNumberOfMutations < 0) throw new CanNotBeNegativeException("Minimum number of mutations");

        this.maximumNumberOfMutations = configArray[11];
        if (maximumNumberOfMutations < 0) throw new CanNotBeNegativeException("Maximum number of mutations");

        this.genesMutatorVariant = configArray[12];
        if (genesMutatorVariant != 0 && genesMutatorVariant != 1) throw new HasToBeBit("Mutation variant");

        this.genesNumber = configArray[13];
        if (genesNumber <= 0) throw new HasToBePositiveException("Animal genome length");

        if (minimumNumberOfMutations > maximumNumberOfMutations) throw new MinMaxGeneException();
        if (maximumNumberOfMutations > genesNumber) throw new MutationChangesCanNotExceedSize();

    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getStartGrassNumber() {
        return startGrassNumber;
    }

    public int getEnergyProvidedByEatingGrass() {
        return energyProvidedByEatingGrass;
    }

    public int getDayGrassNumber() {
        return dayGrassNumber;
    }

    public int getGrassMakerVariant() {
        return grassMakerVariant;
    }

    public int getStartNumberOfAnimals() {
        return startNumberOfAnimals;
    }

    public int getStartingEnergy() {
        return startingEnergy;
    }

    public int getEnergyNeededForBreeding() {
        return energyNeededForBreeding;
    }

    public int getEnergyUsedWhileBreeding() {
        return energyUsedWhileBreeding;
    }

    public int getMinimumNumberOfMutations() {
        return minimumNumberOfMutations;
    }

    public int getMaximumNumberOfMutations() {
        return maximumNumberOfMutations;
    }

    public int getGenesMutatorVariant() {
        return genesMutatorVariant;
    }

    public int getGenesNumber() {
        return genesNumber;
    }

    public String toString(){
        return "width: %d height: %d".formatted(width, height);

    }
}
