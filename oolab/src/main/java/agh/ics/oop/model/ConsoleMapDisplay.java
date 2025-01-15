package agh.ics.oop.model;

import agh.ics.oop.model.observers.MapChangeObserver;

public class ConsoleMapDisplay implements MapChangeObserver {
    private int updateNumber = 0;

    @Override
    public void mapChanged(GlobeMap map, String message) {
        synchronized (System.out) {
            System.out.println("\nUpdateNumber: " + updateNumber);
            System.out.println("Map " + map.getId());
            System.out.println(message);
            System.out.println(map);
            updateNumber++;
        }
    }
}
