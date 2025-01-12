package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener{
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
