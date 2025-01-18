package agh.ics.oop;

import agh.ics.oop.fileManager.SimulationReaderCSV;
import agh.ics.oop.fileManager.SimulationSaverCSV;
import agh.ics.oop.model.*;
import agh.ics.oop.errors.*;
import agh.ics.oop.model.genes.*;
import agh.ics.oop.model.grass.AbstractGrassMaker;
import agh.ics.oop.model.grass.GrassMakerDeadAnimal;
import agh.ics.oop.model.grass.GrassMakerEquator;
import agh.ics.oop.model.observers.MapChangeObserver;
import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.simulation.SimulationConfig;
import agh.ics.oop.stats.Stats;

import java.io.IOException;

public class World {
    public static void main(String[] args) {

        // Wszystkie parametry smymulacji:
        int height = 6;
        int width = 6;
        int startGrassNumber = 5;
        int energyProvidedByEatingGrass = 10;
        int dayGrassNumber = 1;
        int grassMakerVariant = 1;
        int startNumberOfAnimals = 2;
        int startingEnergy = 50;
        int energyNeededForBreeding = 30;
        int energyUsedWhileBreeding = 15;
        int minimumNumberOfMutations = 0;
        int maximumNumberOfMutations = 3;
        int genesMutatorVariant = 0;
        int genesNumber = 20;


        try {
            SimulationConfig createdConfig = new SimulationConfig(
                    height, width, startGrassNumber, energyProvidedByEatingGrass,
                    dayGrassNumber, grassMakerVariant, startNumberOfAnimals, startingEnergy,
                    energyNeededForBreeding, energyUsedWhileBreeding, minimumNumberOfMutations,
                    maximumNumberOfMutations, genesMutatorVariant, genesNumber);

            if (false){
                SimulationSaverCSV simulationSaverCSV = new SimulationSaverCSV();
                SimulationReaderCSV simulationReaderCSV = new SimulationReaderCSV();
                SimulationConfig simConfig = simulationReaderCSV.readFromCSV("file3");
                simulationSaverCSV.saveToCSV(createdConfig, "file6");
            }

            GlobeMap map = new GlobeMap(createdConfig.getWidth(), createdConfig.getHeight(), 0);
            MapChangeObserver listener = new ConsoleMapDisplay();
            map.registerObserver(listener);
            AbstractGrassMaker grassMaker;
            if (createdConfig.getGrassMakerVariant()==1) {
                grassMaker = new GrassMakerDeadAnimal(createdConfig.getStartGrassNumber(), createdConfig.getDayGrassNumber(), map);
            } else {
                grassMaker = new GrassMakerEquator(createdConfig.getStartGrassNumber(), createdConfig.getDayGrassNumber(), map);
            }

            GeneMutator geneMutator;
            if (createdConfig.getGenesMutatorVariant()==0) {
                geneMutator = new ClassicMutation(createdConfig.getMinimumNumberOfMutations(), createdConfig.getMaximumNumberOfMutations());
            } else {
                geneMutator = new SlightCorrection(createdConfig.getMinimumNumberOfMutations(), createdConfig.getMaximumNumberOfMutations());
            }
            GenesFactory genesFactory = new GenesFactory(geneMutator, createdConfig.getGenesNumber());

            Stats stats = new Stats(map, grassMaker, createdConfig.getStartGrassNumber(), createdConfig.getStartingEnergy(), createdConfig.getStartNumberOfAnimals());
            AnimalCreator animalCreator = new AnimalCreator(createdConfig.getStartingEnergy(), createdConfig.getEnergyUsedWhileBreeding(), createdConfig.getEnergyProvidedByEatingGrass(), genesFactory, stats);
            Breeding breeding = new Breeding(createdConfig.getEnergyNeededForBreeding(), createdConfig.getEnergyUsedWhileBreeding(), map, animalCreator);

            Simulation simulation = new Simulation(map, grassMaker, breeding, animalCreator, createdConfig.getStartNumberOfAnimals(), stats);

//            StatsSaverCSV statsSaverCSV = new StatsSaverCSV(stats,"stats3");
//            simulation.registerNewDayObserver(statsSaverCSV);

            simulation.registerAnimalDiedObserver(stats);
            if (grassMaker instanceof GrassMakerDeadAnimal) {
                simulation.registerAnimalDiedObserver((GrassMakerDeadAnimal) grassMaker);
                simulation.registerNewDayObserver((GrassMakerDeadAnimal) grassMaker);
            }

            simulation.run();

        } catch (MinMaxGeneException | HasToBePositiveException | CanNotBeNegativeException | IOException |
                 MutationChangesCanNotExceedSize | FailedToReadConfig | HasToBeBit | BreedingCanNotKillAnimals exception) {
            System.err.println(exception.getMessage());
        }
    }
}
