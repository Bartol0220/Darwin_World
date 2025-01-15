package agh.ics.oop;

import agh.ics.oop.model.stats.Stats;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {
    private final Simulation simulation;
    private final List<Thread> threads = new ArrayList<>();

    public SimulationEngine(Simulation simulation) {
        this.simulation = simulation;
    }

    public void awaitSimulationsEnd()  throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
    }

    public void runAsync() {
        Thread thread = new Thread(simulation);
        thread.start();
        threads.add(thread);

    }

    public void pauseSimulations() throws InterruptedException {
        simulation.pause();
        awaitSimulationsEnd();
    }

    public void playSimulations() {
        simulation.play();
        runAsync();
    }

    public Stats getStats() {
        return simulation.getStats();
    }

    public void changeSleepingTime(int time) { simulation.setThreadSleep(time); }
}
