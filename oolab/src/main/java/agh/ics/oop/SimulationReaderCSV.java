package agh.ics.oop;

import agh.ics.oop.model.errors.*;

import java.io.*;

public class SimulationReaderCSV {
    public SimulationConfig readFromCSV(String fileName) {
        File file = new File(makeFullFileName(fileName));

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line = reader.readLine();
            String[] args = line.split(", ");
            if (args.length < 14) throw new IOException("Za malo ustawien.");
            return createSimulationConfig(args);
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

    private SimulationConfig createSimulationConfig(String[] config) throws HasToBePositiveException, HasToBeBit,
            BreedingCanNotKillAnimals, CanNotBeNegativeException, MutationChangesCanNotExceedSize, MinMaxGeneException  {

        return new SimulationConfig(
                Integer.parseInt(config[0]),
                Integer.parseInt(config[1]),
                Integer.parseInt(config[2]),
                Integer.parseInt(config[3]),
                Integer.parseInt(config[4]),
                Integer.parseInt(config[5]),
                Integer.parseInt(config[6]),
                Integer.parseInt(config[7]),
                Integer.parseInt(config[8]),
                Integer.parseInt(config[9]),
                Integer.parseInt(config[10]),
                Integer.parseInt(config[11]),
                Integer.parseInt(config[12]),
                Integer.parseInt(config[13])
        );
    }

}
