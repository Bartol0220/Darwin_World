package agh.ics.oop.fileManager;

import agh.ics.oop.simulation.SimulationConfig;
import agh.ics.oop.errors.*;

import java.io.*;
import java.util.Arrays;

public class SimulationReaderCSV { // nazwa

    public SimulationConfig readFromCSV(String fileName) throws FailedToReadConfig {
        File file = new File(fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();

            int[] values = Arrays.stream(line.split(";"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            if (values.length < 14) throw new IOException("Za malo ustawien.");

            return new SimulationConfig(values);
        } catch (IOException | NumberFormatException | BreedingCanNotKillAnimals | HasToBeBit | HasToBePositiveException
                 | CanNotBeNegativeException | MutationChangesCanNotExceedSize | MinMaxGeneException e) {
            throw new FailedToReadConfig();
        }
    }
}
