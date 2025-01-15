package agh.ics.oop.model.observers;

import agh.ics.oop.model.GlobeMap;

import java.io.IOException;

public interface MapChangeObserver {
    void mapChanged(GlobeMap map, String message);
}
