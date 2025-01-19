package agh.ics.oop.simulation;

import agh.ics.oop.errors.*;
import agh.ics.oop.model.Breeding;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationConfigTest {

    @Test
    void heightHasToBePositive(){
        int[] arguments = {0,35,9,19,13,0,11,30,15,10,0,5,0,10};
        assertThrows(HasToBePositiveException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void widthHasToBePositive(){
        int[] arguments = {4,0,9,19,13,0,11,30,15,10,0,5,0,10};
        assertThrows(HasToBePositiveException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void startGrassNumberCannotBeNegative(){
        int[] arguments = {5,35,-3,19,13,0,11,30,15,10,0,5,0,10};
        assertThrows(CanNotBeNegativeException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void energyProvidedByEatingGrassCannotBeNegative(){
        int[] arguments = {2,35,9,-5,13,0,11,30,15,10,0,5,0,10};
        assertThrows(CanNotBeNegativeException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void dayGrassNumberCannotBeNegative(){
        int[] arguments = {2,35,9,19,-3,0,11,30,15,10,0,5,0,10};
        assertThrows(CanNotBeNegativeException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void grassMakerVariantHasToBeBit(){
        int[] arguments = {2,35,9,19,13,3,11,30,15,10,0,5,0,10};
        assertThrows(HasToBeBit.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void startNumberOfAnimalsHasToBePositive(){
        int[] arguments = {2,35,9,19,13,0,-5,30,15,10,0,5,0,10};
        assertThrows(HasToBePositiveException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void startingEnergyHasToBePositive(){
        int[] arguments = {2,35,9,19,13,0,11,-6,15,10,0,5,0,10};
        assertThrows(HasToBePositiveException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void energyNeededForBreedingCannotBeNegative(){
        int[] arguments = {2,35,9,19,13,0,11,30,-12,10,0,5,0,10};
        assertThrows(CanNotBeNegativeException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void energyUsedWhileBreedingCannotBeNegative(){
        int[] arguments = {2,35,9,19,13,0,11,30,15,-12,0,5,0,10};
        assertThrows(CanNotBeNegativeException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void minimumNumberOfMutationsCannotBeNegative(){
        int[] arguments = {2,35,9,19,13,0,11,30,15,10,-3,5,0,10};
        assertThrows(CanNotBeNegativeException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void maximumNumberOfMutationsCannotBeNegative(){
        int[] arguments = {2,35,9,19,13,0,11,30,15,10,0,-3,0,10};
        assertThrows(CanNotBeNegativeException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void genesMutatorVariantHasToBeBit(){
        int[] arguments = {2,35,9,19,13,0,11,30,15,10,0,5,3,10};
        assertThrows(HasToBeBit.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void genesNumberHasToBePositive(){
        int[] arguments = {2,35,9,19,13,0,11,30,15,10,0,5,0,-4};
        assertThrows(HasToBePositiveException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void minimumNumberOfMutationsCannotBeLessThanMaximumNumberOfMutations(){
        int[] arguments = {2,35,9,19,13,0,11,30,15,10,5,0,0,10};
        assertThrows(MinMaxGeneException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void maximumNumberOfMutationsCannotExceedNumberOfGenes(){
        int[] arguments = {2,35,9,19,13,0,11,30,15,10,0,12,0,10};
        assertThrows(MutationChangesCanNotExceedSize.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void energyUsedWhileBreedingCannotExceedEnergyNeededForItToHappen(){
        int[] arguments = {2,35,9,19,13,0,11,30,10,15,0,5,0,10};
        assertThrows(BreedingCanNotKillAnimals.class, ()->{new SimulationConfig(arguments);});
    }
}