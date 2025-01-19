package agh.ics.oop.simulation;

import agh.ics.oop.errors.CanNotBeNegativeException;
import agh.ics.oop.errors.HasToBeBit;
import agh.ics.oop.errors.HasToBePositiveException;
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
        int[] arguments = {0,35,9,-5,13,0,11,30,15,10,0,5,0,10};
        assertThrows(CanNotBeNegativeException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void dayGrassNumberCannotBeNegative(){
        int[] arguments = {0,35,9,19,-3,0,11,30,15,10,0,5,0,10};
        assertThrows(CanNotBeNegativeException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void grassMakerVariantHasToBeBit(){
        int[] arguments = {0,35,9,19,13,3,11,30,15,10,0,5,0,10};
        assertThrows(HasToBeBit.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void startNumberOfAnimalsHasToBePositive(){
        int[] arguments = {0,35,9,19,13,0,11,30,15,10,0,5,0,10};
        assertThrows(HasToBePositiveException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void startingEnergyHasToBePositive(){
        int[] arguments = {0,35,9,19,13,0,11,30,15,10,0,5,0,10};
        assertThrows(HasToBePositiveException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void energyNeededForBreedingCannotBeNegative(){
        int[] arguments = {0,35,9,19,13,0,11,30,15,10,0,5,0,10};
        assertThrows(CanNotBeNegativeException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void energyUsedWhileBreedingCannotBeNegative(){
        int[] arguments = {0,35,9,19,13,0,11,30,15,10,0,5,0,10};
        assertThrows(CanNotBeNegativeException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void maximumNumberOfMutationsCannotBeNegative(){
        int[] arguments = {0,35,9,19,13,0,11,30,15,10,0,5,0,10};
        assertThrows(CanNotBeNegativeException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void genesMutatorVariantHasToBeBit(){
        int[] arguments = {0,35,9,19,13,0,11,30,15,10,0,5,0,10};
        assertThrows(HasToBeBit.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void genesNumberHasToBePositive(){
        int[] arguments = {0,35,9,19,13,0,11,30,15,10,0,5,0,10};
        assertThrows(HasToBePositiveException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void minimumNumberOfMutationsCannotLessThanMaximumNumberOfMutations(){
        int[] arguments = {0,35,9,19,13,0,11,30,15,10,0,5,0,10};
        assertThrows(HasToBePositiveException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void minimumNumberOfMutationsCannotBeNegative(){
        int[] arguments = {0,35,9,19,13,0,11,30,15,10,0,5,0,10};
        assertThrows(HasToBePositiveException.class, ()->{new SimulationConfig(arguments);});
    }

    @Test
    void minimumNumberOfMutationsCannotBeNegative(){
        int[] arguments = {0,35,9,19,13,0,11,30,15,10,0,5,0,10};
        assertThrows(HasToBePositiveException.class, ()->{new SimulationConfig(arguments);});
    }
}