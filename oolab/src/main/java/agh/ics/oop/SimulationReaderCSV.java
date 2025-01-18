package agh.ics.oop;

import agh.ics.oop.model.errors.*;

import java.io.*;
import java.util.Arrays;

public class SimulationReaderCSV {
    public SimulationConfig readFromCSV(String fileName) throws FailedToReadConfig {
        File file = new File(makeFullFileName(fileName));

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line = reader.readLine();
            int[] values = Arrays.stream(line.split(";"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            if (values.length < 14) throw new IOException("Za malo ustawien.");
            return createSimulationConfig(values);
        } catch (IOException | BreedingCanNotKillAnimals | HasToBeBit | HasToBePositiveException
                 | CanNotBeNegativeException | MutationChangesCanNotExceedSize | MinMaxGeneException e) {
            throw new FailedToReadConfig();
        }
    }

    private String makeFullFileName(String fileName){
        return System.getProperty("user.dir") +
                File.separator + "simconfig" +
                File.separator + fileName + ".csv";
    }

    private SimulationConfig createSimulationConfig(int[] config) throws HasToBePositiveException, HasToBeBit,
            BreedingCanNotKillAnimals, CanNotBeNegativeException, MutationChangesCanNotExceedSize, MinMaxGeneException  {

        return new SimulationConfig(config);
    }

}
