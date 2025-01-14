package agh.ics.oop.model;

import agh.ics.oop.model.errors.*;

public record SimulationConfig(
        int height,
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
        int genesNumber
) {


    public static final class Builder {
        int height = 5;
        int width = 5;
        int startGrassNumber = 1;
        int energyProvidedByEatingGrass = 1;
        int dayGrassNumber = 1;
        int grassMakerVariant = 0;
        int startNumberOfAnimals = 2;
        int startingEnergy = 10;
        int energyNeededForBreeding = 3;
        int energyUsedWhileBreeding = 2;
        int minimumNumberOfMutations = 0;
        int maximumNumberOfMutations = 0;
        int genesMutatorVariant = 0;
        int genesNumber = 5;

        public Builder height(int height) throws HasToBePositiveException {
            this.height = height;
            if (height <= 0) throw new HasToBePositiveException("Height");
            return this;
        }

        public Builder width(int width) throws HasToBePositiveException {
            this.width = width;
            if (width <= 0) throw new HasToBePositiveException("Width");
            return this;
        }

        public Builder startGrassNumber(int startGrassNumber) throws CanNotBeNegativeException {
            this.startGrassNumber = startGrassNumber;
            if (startGrassNumber < 0) throw new CanNotBeNegativeException("Initial number of grass");
            return this;
        }

        public Builder energyProvidedByEatingGrass(int energyProvidedByEatingGrass) throws CanNotBeNegativeException {
            this.energyProvidedByEatingGrass = energyProvidedByEatingGrass;
            if (energyProvidedByEatingGrass < 0) throw new CanNotBeNegativeException("Energy gained from eating");
            return this;
        }

        public Builder dayGrassNumber(int dayGrassNumber) throws CanNotBeNegativeException {
            this.dayGrassNumber = dayGrassNumber;
            if (dayGrassNumber < 0) throw new CanNotBeNegativeException("Number of grass growing every day");
            return this;
        }

        public Builder isDeathGivingLife(int isDeathGivingLife) throws HasToBeBit {
            this.grassMakerVariant = isDeathGivingLife;
            if (isDeathGivingLife != 0 && isDeathGivingLife != 1) throw new HasToBeBit("Grass growing variant");
            return this;
        }

        public Builder startNumberOfAnimals(int startNumberOfAnimals) throws HasToBePositiveException {
            this.startNumberOfAnimals = startNumberOfAnimals;
            if (startNumberOfAnimals <= 0) throw new HasToBePositiveException("Initial number of animals");
            return this;
        }

        public Builder startingEnergy(int startingEnergy) throws HasToBePositiveException {
            this.startingEnergy = startingEnergy;
            if (startingEnergy <= 0) throw new HasToBePositiveException("Initial energy of animals");
            return this;
        }

        private void energyNeededForBreeding(int energyNeededForBreeding) throws CanNotBeNegativeException {
            this.energyNeededForBreeding = energyNeededForBreeding;
            if (energyNeededForBreeding < 0) throw new CanNotBeNegativeException("Energy needed to consider the animal as ready to breed");
        }

        private void energyUsedWhileBreeding(int energyUsedWhileBreeding) throws CanNotBeNegativeException {
            this.energyUsedWhileBreeding = energyUsedWhileBreeding;
            if (energyUsedWhileBreeding < 0) throw new CanNotBeNegativeException("Energy used during reproduction");
        }

        public Builder breedingInfo(int energyNeededForBreeding, int energyUsedWhileBreeding) throws CanNotBeNegativeException, BreedingCanNotKillAnimals{
            energyNeededForBreeding(energyNeededForBreeding);
            energyUsedWhileBreeding(energyUsedWhileBreeding);
            if (energyUsedWhileBreeding > energyNeededForBreeding){
                throw new BreedingCanNotKillAnimals();
            }
            return this;
        }


        private void minimumNumberOfMutations(int minimumNumberOfMutations) throws CanNotBeNegativeException{
            this.minimumNumberOfMutations = minimumNumberOfMutations;
            if (minimumNumberOfMutations < 0) throw new CanNotBeNegativeException("Minimum number of mutations");
        }

        private void maximumNumberOfMutations(int maximumNumberOfMutations) throws CanNotBeNegativeException{
            this.maximumNumberOfMutations = maximumNumberOfMutations;
            if (maximumNumberOfMutations < 0) throw new CanNotBeNegativeException("Maximum number of mutations");
        }

        private void genesNumber(int genesNumber) throws HasToBePositiveException {
            this.genesNumber = genesNumber;
            if (genesNumber <= 0) throw new HasToBePositiveException("Animal genome length");
        }

        public Builder mutationsInfo(int minimumNumberOfMutations, int maximumNumberOfMutations, int genesNumber) throws HasToBePositiveException, CanNotBeNegativeException, MinMaxGeneException, MutationChangesCanNotExceedSize {
            minimumNumberOfMutations(minimumNumberOfMutations);
            maximumNumberOfMutations(maximumNumberOfMutations);
            genesNumber(genesNumber);
            if (minimumNumberOfMutations > maximumNumberOfMutations) throw new MinMaxGeneException();
            if (maximumNumberOfMutations > genesNumber) throw new MutationChangesCanNotExceedSize();
            return this;
        }

        public Builder isSlightCorrection(int isSlightCorrection) throws HasToBeBit {
            this.genesMutatorVariant = isSlightCorrection;
            if (isSlightCorrection != 0 && isSlightCorrection != 1) throw new HasToBeBit("Mutation variant");
            return this;
        }

        public SimulationConfig build() {
            return new SimulationConfig(height,
            width,
            startGrassNumber,
            energyProvidedByEatingGrass,
            dayGrassNumber,
                    grassMakerVariant,
            startNumberOfAnimals,
            startingEnergy,
            energyNeededForBreeding,
            energyUsedWhileBreeding,
            minimumNumberOfMutations,
            maximumNumberOfMutations,
                    genesMutatorVariant,
            genesNumber
);
        }
    }
}
