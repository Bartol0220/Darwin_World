package agh.ics.oop;

import agh.ics.oop.model.GlobeMap;
import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.Stats;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StatsSaverCSV implements MapChangeListener {
    private final Stats stats;
    private final String fileName;

    public StatsSaverCSV(Stats stats, String fileName) {
        this.stats = stats;
        this.fileName = fileName;
    }

    private void appendToCSV(String headline) throws IOException {
        File simulationCSV = new File(makeFullFileName(fileName));
        try (FileWriter writer = new FileWriter(simulationCSV, true)) {
            writer.append(headline);
            writer.append("\n");
            writer.append(stats.toString());
            writer.append("\n");
            writer.append("\n");
        }
        //nie wiem czy go rzucac czy nie
        //throw new FileAlreadyExistsException(fileName + " already exists.");
    }

    private String makeFullFileName(String fileName){
        return System.getProperty("user.dir") +
                File.separator + "stats" +
                File.separator + fileName + ".csv";
    }


    @Override
    public void mapChanged(GlobeMap map, String message) throws IOException {
        appendToCSV(message);
    }
}
