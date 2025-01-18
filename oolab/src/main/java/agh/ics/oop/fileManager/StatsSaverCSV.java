package agh.ics.oop.fileManager;

import agh.ics.oop.model.observers.FailedToSaveObserver;
import agh.ics.oop.model.observers.NewDayObserver;
import agh.ics.oop.statistics.Stats;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

public class StatsSaverCSV implements NewDayObserver {
    private final Stats stats;
    private final File simulationStatsCSV;
    private final List<FailedToSaveObserver> failedToSaveObserver = new ArrayList<>();

    public StatsSaverCSV(Stats stats, String fileName) throws IOException{
        this.stats = stats;
        this.simulationStatsCSV = new File(makeFullFileName(fileName));
        new File(System.getProperty("user.dir") + File.separator + "stats").mkdirs();
        if (simulationStatsCSV.createNewFile()) {
            try (FileWriter writer = new FileWriter(simulationStatsCSV, true)) {
                writer.append("Day;Animal count;Grass Count;Free space;Most common genes;Average energy;Average lifespan;Average children count\n");
            }
        } else {
            throw new FileAlreadyExistsException("File \"" + fileName + "\" already exists.");
        }
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
    public void newDay(int day) {
        try (FileWriter writer = new FileWriter(simulationStatsCSV, true)) {
            writer.append(makeLine("Day " + day));
        } catch (IOException e) {
            notifyFailedToSaveObservers();
        }

    }

    public void registerFailedToSaveObserver(FailedToSaveObserver observer) { failedToSaveObserver.add(observer);}

    public void unregisterFailedToSaveObserver(FailedToSaveObserver observer) { failedToSaveObserver.remove(observer);}

    public void notifyFailedToSaveObservers() {
        for(FailedToSaveObserver observer : failedToSaveObserver){
            observer.failedToSave();
        }
    }
}
