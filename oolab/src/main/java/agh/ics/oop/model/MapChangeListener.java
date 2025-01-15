package agh.ics.oop.model;

import java.io.IOException;

public interface MapChangeListener {
    void mapChanged(GlobeMap map, String message) throws IOException;
}
