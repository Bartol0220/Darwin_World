package agh.ics.oop.model.observers;

import agh.ics.oop.model.GlobeMap;

public interface MapChangeObserver {
    void mapChanged(GlobeMap map, String message);
}
