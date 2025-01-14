package agh.ics.oop.presenter;

import agh.ics.oop.SimulationEngine;
import agh.ics.oop.WorldElementBox;
import agh.ics.oop.model.*;
import agh.ics.oop.model.util.Boundary;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Optional;

public class SimulationPresenter implements MapChangeListener {
    private double mapWidth;
    private double mapHeight;
    private static final String EMPTY_CELL = "";
    private int cellWidth;
    private int cellHeight;
    private GlobeMap map;
    private Stage stage;

    @FXML
    private GridPane mapGridPane;
    @FXML
    private Label currentMove;

    private void setMapDimensions() {
        mapWidth = stage.getWidth() * 0.85;
        mapHeight = stage.getHeight() * 0.85;
    }

    private void setMap(GlobeMap map) {
        this.map = map;
    }

    public void newSimulation(GlobeMap map, SimulationEngine simulationEngine, Stage stage) {
        map.registerObserver(this);
        this.stage = stage;
        setMapDimensions();

        setMap(map);
        drawMap();

        simulationEngine.runAsync();
    }

    private void clearGrid() {
        mapGridPane.getChildren().retainAll(mapGridPane.getChildren().getFirst()); // hack to retain visible grid lines
        mapGridPane.getColumnConstraints().clear();
        mapGridPane.getRowConstraints().clear();
    }

    private void drawMap() {
        clearGrid();
        Boundary currentBounds =  map.getCurrentBounds();

        updateMapInfo(currentBounds);
        drawHeader(currentBounds);
        drawFirstColumn(currentBounds);
        drawAllObjects(currentBounds);
    }

    private void updateMapInfo(Boundary currentBounds) {
        int mapWidth = currentBounds.upperRight().getX() - currentBounds.lowerLeft().getX() + 1;
        int mapHeight = currentBounds.upperRight().getY() - currentBounds.lowerLeft().getY() + 1;
        cellWidth = (int) this.mapWidth / mapWidth;
        cellHeight = (int) this.mapHeight / mapHeight;
        cellHeight = Math.min(cellHeight, Math.min(cellWidth, 40));
        cellWidth = cellHeight;
    }

    private void drawHeader(Boundary currentBounds) {
        Label label = new Label("y/x");
        mapGridPane.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
        mapGridPane.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        mapGridPane.getRowConstraints().add(new RowConstraints(cellHeight));

        int cnt = 1;
        for (int j = currentBounds.lowerLeft().getX(); j < currentBounds.upperRight().getX() + 1; j++) {
            mapGridPane.getColumnConstraints().add(new ColumnConstraints(cellWidth));
            label = new Label(String.format("%d", j));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGridPane.add(label, cnt, 0);
            cnt++;
        }
    }

    private void drawFirstColumn(Boundary currentBounds) {
        int cnt = 1;
        for (int j = currentBounds.upperRight().getY(); j > currentBounds.lowerLeft().getY() - 1; j--) {
            mapGridPane.getRowConstraints().add(new RowConstraints(cellHeight));
            Label label = new Label(String.format("%d", j));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGridPane.add(label, 0, cnt);
            cnt++;
        }
    }

    private void drawAllObjects(Boundary currentBounds) {
        for(int y = 1; y < currentBounds.upperRight().getY() - currentBounds.lowerLeft().getY() + 2; y++) {
            for(int x = 1; x < currentBounds.upperRight().getX() - currentBounds.lowerLeft().getX() + 2; x++) {
                Optional<WorldElementBox> worldElementBox = drawObject(new Vector2d(currentBounds.lowerLeft().getX()+x-1, currentBounds.upperRight().getY()-y+1));
                if (worldElementBox.isPresent()) {
                    GridPane.setHalignment(worldElementBox.get(), HPos.CENTER);
                    mapGridPane.add(worldElementBox.get(), x, y);
                } else {
                    mapGridPane.add(new Label(), x, y);
                }
            }
        }
    }

    private Optional<WorldElementBox> drawObject(Vector2d currentPosition) {
        return map.objectAt(currentPosition).map(object -> new WorldElementBox(object, cellWidth));
    }

    @Override
    public void mapChanged(GlobeMap map, String message) {
        Platform.runLater(() -> {
            currentMove.setText(message);
            drawMap();
        });
    }
}
