package agh.ics.oop.model;

import agh.ics.oop.model.errors.*;

public record SimulationConfig(
        int height,
        int width,
        int startGrassNumber,
        int energyProvidedByEatingGrass,
        int dayGrassNumber,
        boolean isDeathGivingLife,
        int startNumberOfAnimals,
        int startingEnergy,
        int energyNeededForBreeding,
        int energyUsedWhileBreeding,
        int minimumNumberOfMutations,
        int maximumNumberOfMutations,
        boolean isSlightCorrection,
        int genesNumber
) {


    public static final class Builder {
        int height = 5;
        int width = 5;
        int startGrassNumber = 1;
        int energyProvidedByEatingGrass = 1;
        int dayGrassNumber = 1;
        boolean isDeathGivingLife = false;
        int startNumberOfAnimals = 2;
        int startingEnergy = 10;
        int energyNeededForBreeding = 3;
        int energyUsedWhileBreeding = 2;
        int minimumNumberOfMutations = 0;
        int maximumNumberOfMutations = 0;
        boolean isSlightCorrection = false;
        int genesNumber = 5;

        public Builder height(int height) throws HasToBePositiveException {
            this.height = height;
            if (height <= 0) throw new HasToBePositiveException("height");
            return this;
        }

        public Builder width(int width) throws HasToBePositiveException {
            this.width = width;
            if (width <= 0) throw new HasToBePositiveException("width");
            return this;
        }

        public Builder startGrassNumber(int startGrassNumber) throws CanNotBeNegativeException {
            this.startGrassNumber = startGrassNumber;
            if (startGrassNumber < 0) throw new CanNotBeNegativeException("startGrassNumber");
            return this;
        }

        public Builder energyProvidedByEatingGrass(int energyProvidedByEatingGrass) throws CanNotBeNegativeException {
            this.energyProvidedByEatingGrass = energyProvidedByEatingGrass;
            if (energyProvidedByEatingGrass < 0) throw new CanNotBeNegativeException("energyProvidedByEatingGrass");
            return this;
        }

        public Builder dayGrassNumber(int dayGrassNumber) throws CanNotBeNegativeException {
            this.dayGrassNumber = dayGrassNumber;
            if (dayGrassNumber < 0) throw new CanNotBeNegativeException("dayGrassNumber");
            return this;
        }

        public Builder isDeathGivingLife(boolean isDeathGivingLife) {
            this.isDeathGivingLife = isDeathGivingLife;
            return this;
        }

        public Builder startNumberOfAnimals(int startNumberOfAnimals) throws HasToBePositiveException {
            this.startNumberOfAnimals = startNumberOfAnimals;
            if (startNumberOfAnimals <= 0) throw new HasToBePositiveException("startNumberOfAnimals");
            return this;
        }

        public Builder startingEnergy(int startingEnergy) throws HasToBePositiveException {
            this.startingEnergy = startingEnergy;
            if (startingEnergy <= 0) throw new HasToBePositiveException("startingEnergy");
            return this;
        }

        private void energyNeededForBreeding(int energyNeededForBreeding) throws CanNotBeNegativeException {
            this.energyNeededForBreeding = energyNeededForBreeding;
            if (energyNeededForBreeding < 0) throw new CanNotBeNegativeException("energyNeededForBreeding");
        }

        private void energyUsedWhileBreeding(int energyUsedWhileBreeding) throws CanNotBeNegativeException {
            this.energyUsedWhileBreeding = energyUsedWhileBreeding;
            if (energyUsedWhileBreeding < 0) throw new CanNotBeNegativeException("energyUsedWhileBreeding");
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
            if (minimumNumberOfMutations < 0) throw new CanNotBeNegativeException("minimumNumberOfMutations");
        }

        private void maximumNumberOfMutations(int maximumNumberOfMutations) throws CanNotBeNegativeException{
            this.maximumNumberOfMutations = maximumNumberOfMutations;
            if (maximumNumberOfMutations < 0) throw new CanNotBeNegativeException("maximumNumberOfMutations");
        }

        public Builder mutationsInfo(int minimumNumberOfMutations, int maximumNumberOfMutations, int genesNumber) throws HasToBePositiveException, CanNotBeNegativeException, MinMaxException, MutationChangesCanNotExceedSize {
            minimumNumberOfMutations(minimumNumberOfMutations);
            maximumNumberOfMutations(maximumNumberOfMutations);
            genesNumber(genesNumber);
            if (minimumNumberOfMutations > maximumNumberOfMutations) throw new MinMaxException();
            if (maximumNumberOfMutations > genesNumber) throw new MutationChangesCanNotExceedSize();
            return this;
        }

        public Builder isSlightCorrection(boolean isSlightCorrection) {
            this.isSlightCorrection = isSlightCorrection;
            return this;
        }

        private void genesNumber(int genesNumber) throws HasToBePositiveException {
            this.genesNumber = genesNumber;
            if (genesNumber <= 0) throw new HasToBePositiveException("genesNumber");
        }

        public SimulationConfig build() {
            return new SimulationConfig(height,
            width,
            startGrassNumber,
            energyProvidedByEatingGrass,
            dayGrassNumber,
            isDeathGivingLife,
            startNumberOfAnimals,
            startingEnergy,
            energyNeededForBreeding,
            energyUsedWhileBreeding,
            minimumNumberOfMutations,
            maximumNumberOfMutations,
            isSlightCorrection,
            genesNumber
);
        }
    }
}
