package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.errors.*;
import agh.ics.oop.model.genes.*;
import agh.ics.oop.model.grass.AbstractGrassMaker;
import agh.ics.oop.model.grass.GrassMakerDeadAnimal;
import agh.ics.oop.model.grass.GrassMakerEquator;

public class World {
    public static void main(String[] args) {

        // Wszystkie parametry smymulacji:
        int height = 3; // TODO wyjątek jeśli < 0
        int width = 3; // TODO wyjątek jeśli < 0
        int startGrassNumber = 5; // TODO wyjątek jeśli < 0
        int energyProvidedByEatingGrass = 10; // TODO wyjątek jeśli < 0
        int dayGrassNumber = 1; // TODO wyjątek jeśli < 0
        int grassMakerVariant = 0;
        int startNumberOfAnimals = 2; // TODO wyjątek jeśli < 0
        int startingEnergy = 10; // TODO wyjątek jeśli < 0
        int energyNeededForBreeding = 3; // TODO wyjątek jeśli < 0
        int energyUsedWhileBreeding = 2; // TODO wyjątek jeśli < energyNeededForBreeding
        int minimumNumberOfMutations = 0; // TODO wyjątek jeśli < 0
        int maximumNumberOfMutations = 3; // TODO wyjątek jeśli < minimumNumberOfMutations i jeśli > genesNumber
        int genesMutatorVariant = 0;
        int genesNumber = 5; // TODO wyjątek jeśli < 0

        SimulationConfig simConfig = new SimulationConfig.Builder().build();
        ;
        try {
            simConfig = new SimulationConfig.Builder()
                    .height(height)
                    .width(width)
                    .startGrassNumber(startGrassNumber)
                    .energyProvidedByEatingGrass(energyProvidedByEatingGrass)
                    .dayGrassNumber(dayGrassNumber)
                    .isDeathGivingLife(grassMakerVariant)
                    .startNumberOfAnimals(startNumberOfAnimals)
                    .startingEnergy(startingEnergy)
                    .breedingInfo(energyNeededForBreeding, energyUsedWhileBreeding)
                    .mutationsInfo(minimumNumberOfMutations, maximumNumberOfMutations, genesNumber)
                    .isSlightCorrection(genesMutatorVariant)
                    .build();

        } catch (HasToBePositiveException | MinMaxGeneException | BreedingCanNotKillAnimals |
                 CanNotBeNegativeException | MutationChangesCanNotExceedSize | HasToBeBit exception) {
            System.err.println(exception.getMessage());
            //jak wywali blad, to uzyj defaultowych ustawien
        } finally {
            GlobeMap map = new GlobeMap(simConfig.width(), simConfig.height(), 0);
            MapChangeListener listener = new ConsoleMapDisplay();
            map.registerObserver(listener);
            AbstractGrassMaker grassMaker;
            if (simConfig.grassMakerVariant()==1) {
                grassMaker = new GrassMakerDeadAnimal(simConfig.startGrassNumber(), simConfig.dayGrassNumber(), map);
            } else {
                grassMaker = new GrassMakerEquator(simConfig.startGrassNumber(), simConfig.dayGrassNumber(), map);
            }

            GeneMutator geneMutator;
            if (simConfig.genesMutatorVariant()==0) {
                geneMutator = new ClassicMutation(simConfig.minimumNumberOfMutations(), simConfig.maximumNumberOfMutations());
            } else {
                geneMutator = new SlightCorrection(simConfig.minimumNumberOfMutations(), simConfig.maximumNumberOfMutations());
            }
            GenesFactory genesFactory = new GenesFactory(geneMutator, simConfig.genesNumber());

            Stats stats = new Stats(map, grassMaker, simConfig.startGrassNumber(), simConfig.startingEnergy(), simConfig.startNumberOfAnimals());
            AnimalCreator animalCreator = new AnimalCreator(simConfig.startingEnergy(), simConfig.energyUsedWhileBreeding(), simConfig.energyProvidedByEatingGrass(), genesFactory, stats);
            Breeding breeding = new Breeding(simConfig.energyNeededForBreeding(), simConfig.energyUsedWhileBreeding(), map, animalCreator);

            Simulation simulation = new Simulation(map, grassMaker, breeding, animalCreator, simConfig.startNumberOfAnimals(), stats);
            simulation.run();
        }
    }
}
