package agh.ics.oop;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

public class SimulationSaverCSV {
    public void saveToCSV(SimulationConfig simulationConfig, String fileName) throws IOException {
        File simulationCSV = new File(makeFullFileName(fileName));
        if (simulationCSV.createNewFile()) {
            try (FileWriter writer = new FileWriter(simulationCSV)) {
                writer.write(makeSimulationMessage(simulationConfig));
            }
            return;
        }
        //nie wiem czy go rzucac czy nie
        //throw new FileAlreadyExistsException(fileName + " already exists.");
    }

    private String makeFullFileName(String fileName){
        return System.getProperty("user.dir") +
                File.separator + "src" +
                File.separator + "main" +
                File.separator + "java" +
                File.separator + "agh" +
                File.separator + "ics" +
                File.separator + "oop" +
                File.separator + "simconfig" +
                File.separator + fileName + ".csv";
    }

    private String makeSimulationMessage(SimulationConfig simulationConfig){
        return "%d, %d, %d, %d, %d, %d, %d, %d, %d, %d, %d, %d, %d, %d".formatted(
                simulationConfig.getHeight(),
                simulationConfig.getWidth(),
                simulationConfig.getStartGrassNumber(),
                simulationConfig.getEnergyProvidedByEatingGrass(),
                simulationConfig.getDayGrassNumber(),
                simulationConfig.getGrassMakerVariant(),
                simulationConfig.getStartNumberOfAnimals(),
                simulationConfig.getStartingEnergy(),
                simulationConfig.getEnergyNeededForBreeding(),
                simulationConfig.getEnergyUsedWhileBreeding(),
                simulationConfig.getMinimumNumberOfMutations(),
                simulationConfig.getMaximumNumberOfMutations(),
                simulationConfig.getGenesMutatorVariant(),
                simulationConfig.getGenesNumber()
        );
    }
}
