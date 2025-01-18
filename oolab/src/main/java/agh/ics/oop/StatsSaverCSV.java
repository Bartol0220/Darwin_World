package agh.ics.oop;

import agh.ics.oop.model.GlobeMap;
import agh.ics.oop.model.observers.NewDayObserver;
import agh.ics.oop.model.stats.Stats;
import agh.ics.oop.model.observers.MapChangeObserver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

public class StatsSaverCSV implements NewDayObserver {
    private final Stats stats;
    private final String fileName;
    private final File simulationCSV;

    public StatsSaverCSV(Stats stats, String fileName) throws IOException{
        this.stats = stats;
        this.fileName = fileName;
        this.simulationCSV = new File(makeFullFileName(fileName));
        new File(System.getProperty("user.dir") + File.separator + "stats").mkdirs();
        try (FileWriter writer = new FileWriter(simulationCSV, true)) {
            writer.append("Day;Animal count;Grass Count;Free space;Most common genes;Average energy;Average lifespan;Average children count\n");
        }
    }

        private void appendToCSV(String message) throws IOException {

        try (FileWriter writer = new FileWriter(simulationCSV, true)) {
            writer.append(makeLine(message));
        }
        catch (IOException _){
            throw new FileAlreadyExistsException(fileName + " already exists.");
        }
        //nie wiem czy go rzucac czy nie
        //throw new FileAlreadyExistsException(fileName + " already exists.");
    }

    private String makeFullFileName(String fileName){
        return System.getProperty("user.dir") +
                File.separator + "stats" +
                File.separator + fileName + ".csv";
    }

    private String makeLine(String message){
        return "%s;%d;%d;%d;%s;%.2f;%.2f;%.2f\n".formatted(
            message.split(" ")[1],
            stats.getCurrentAnimalCount(),
            stats.getGrassCount(),
            stats.getFreeSpace(),
            stats.getMostCommonGenes(),
            stats.getAverageEnergy(),
            stats.getAverageLifespan(),
            stats.getAverageBirthrate());
    }

    @Override
    public void newDay(int day) throws IOException {
        appendToCSV("Day " + day);
    }
}
