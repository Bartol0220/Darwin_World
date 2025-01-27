package agh.ics.oop.fileManager;

import agh.ics.oop.simulation.SimulationConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

public class SimulationSaverCSV {
    public void saveToCSV(SimulationConfig simulationConfig, String fileName) throws IOException {
        File simulationConfigCSV = new File(makeFullFileName(fileName));
        new File(System.getProperty("user.dir") + File.separator + "simconfig").mkdirs();
        if (simulationConfigCSV.createNewFile()) {
            try (FileWriter writer = new FileWriter(simulationConfigCSV)) {
                writer.write(makeSimulationMessage(simulationConfig));
            }
        } else {
            throw new FileAlreadyExistsException("File \"" + fileName + "\" already exists.");
        }
    }

    private String makeFullFileName(String fileName) {
        return System.getProperty("user.dir") +
                File.separator + "simconfig" +
                File.separator + fileName + ".csv";
    }

    private String makeSimulationMessage(SimulationConfig simulationConfig) {
        return "%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d".formatted(
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
