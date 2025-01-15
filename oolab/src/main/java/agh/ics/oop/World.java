package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.errors.*;
import agh.ics.oop.model.genes.*;
import agh.ics.oop.model.grass.AbstractGrassMaker;
import agh.ics.oop.model.grass.GrassMakerDeadAnimal;
import agh.ics.oop.model.grass.GrassMakerEquator;

import java.io.IOException;

public class World {
    public static void main(String[] args) {

        // Wszystkie parametry smymulacji:
        int height = 6;
        int width = 6;
        int startGrassNumber = 5;
        int energyProvidedByEatingGrass = 10;
        int dayGrassNumber = 1;
        int grassMakerVariant = 0;
        int startNumberOfAnimals = 2;
        int startingEnergy = 20;
        int energyNeededForBreeding = 3;
        int energyUsedWhileBreeding = 2;
        int minimumNumberOfMutations = 0;
        int maximumNumberOfMutations = 3;
        int genesMutatorVariant = 0;
        int genesNumber = 5;


        try {
            SimulationSaverCSV simulationSaverCSV = new SimulationSaverCSV();
            SimulationReaderCSV simulationReaderCSV = new SimulationReaderCSV();
            SimulationConfig simConfig = simulationReaderCSV.readFromCSV("file3");

            SimulationConfig createdConfig = new SimulationConfig(
                    height, width, startGrassNumber, energyProvidedByEatingGrass,
                    dayGrassNumber, grassMakerVariant, startNumberOfAnimals, startingEnergy,
                    energyNeededForBreeding, energyUsedWhileBreeding, minimumNumberOfMutations,
                    maximumNumberOfMutations, genesMutatorVariant, genesNumber);

            simulationSaverCSV.saveToCSV(createdConfig, "file4");


            GlobeMap map = new GlobeMap(simConfig.getWidth(), simConfig.getHeight(), 0);
            MapChangeListener listener = new ConsoleMapDisplay();
            map.registerObserver(listener);
            AbstractGrassMaker grassMaker;
            if (simConfig.getGrassMakerVariant()==1) {
                grassMaker = new GrassMakerDeadAnimal(simConfig.getStartGrassNumber(), simConfig.getDayGrassNumber(), map);
            } else {
                grassMaker = new GrassMakerEquator(simConfig.getStartGrassNumber(), simConfig.getDayGrassNumber(), map);
            }

            GeneMutator geneMutator;
            if (simConfig.getGenesMutatorVariant()==0) {
                geneMutator = new ClassicMutation(simConfig.getMinimumNumberOfMutations(), simConfig.getMaximumNumberOfMutations());
            } else {
                geneMutator = new SlightCorrection(simConfig.getMinimumNumberOfMutations(), simConfig.getMaximumNumberOfMutations());
            }
            GenesFactory genesFactory = new GenesFactory(geneMutator, simConfig.getGenesNumber());

            Stats stats = new Stats(map, grassMaker, simConfig.getStartGrassNumber(), simConfig.getStartingEnergy(), simConfig.getStartNumberOfAnimals());
            AnimalCreator animalCreator = new AnimalCreator(simConfig.getStartingEnergy(), simConfig.getEnergyUsedWhileBreeding(), simConfig.getEnergyProvidedByEatingGrass(), genesFactory, stats);
            Breeding breeding = new Breeding(simConfig.getEnergyNeededForBreeding(), simConfig.getEnergyUsedWhileBreeding(), map, animalCreator);

            StatsSaverCSV statsSaverCSV = new StatsSaverCSV(stats,"stats0");
            map.registerObserver(statsSaverCSV);

            Simulation simulation = new Simulation(map, grassMaker, breeding, animalCreator, simConfig.getStartNumberOfAnimals(), stats);
            simulation.run();

        } catch (MinMaxGeneException | HasToBePositiveException | CanNotBeNegativeException | IOException |
                 MutationChangesCanNotExceedSize | FailedToReadConfig | HasToBeBit | BreedingCanNotKillAnimals exception) {
            System.err.println(exception.getMessage());
        }
    }
}
