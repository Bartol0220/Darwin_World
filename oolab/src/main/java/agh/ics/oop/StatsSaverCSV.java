package agh.ics.oop;

import agh.ics.oop.model.GlobeMap;
import agh.ics.oop.model.observers.MapChangeObserver;
import agh.ics.oop.model.stats.Stats;

public class StatsSaverCSV implements MapChangeObserver {
    private final Stats stats;

    public StatsSaverCSV(Stats stats) {
        this.stats = stats;
    }

    @Override
    public void mapChanged(GlobeMap map, String message) {
        // TODO zapisz w pliku
    }
}
