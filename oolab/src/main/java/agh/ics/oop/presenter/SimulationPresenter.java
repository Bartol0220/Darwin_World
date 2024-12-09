package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import agh.ics.oop.model.util.Boundary;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    private static final int MAX_MAP_WIDTH = 350;
    private static final int MAX_MAP_HEIGHT = 350;
    private static final String EMPTY_CELL = "";
    private int cellWidth;
    private int cellHeight;
    private WorldMap worldMap;
//    @FXML
//    private Label infoLabel;
    @FXML
    private TextField moves;
    @FXML
    private GridPane mapGridPane;
    @FXML
    private Label currentMove;

    private void setWorldMap(WorldMap map) {
        worldMap = map;
    }

    public void onSimulationStartClicked() {
        List<MoveDirection> directions = OptionsParser.parse(moves.getText().split(" "));
//        RectangularMap map = new RectangularMap(10, 10, 0);
        GrassField map = new GrassField(2, 0);
        List<Vector2d> positions = List.of(new Vector2d(1,1), new Vector2d(3,3));
        Simulation simulation = new Simulation(positions, map, directions);
        SimulationEngine simulationEngine = new SimulationEngine(List.of(simulation));

        map.registerObserver(this);

        setWorldMap(map);
        drawMap();

        simulationEngine.runAsyncInThreadPool();
    }

    private void clearGrid() {
        mapGridPane.getChildren().retainAll(mapGridPane.getChildren().getFirst()); // hack to retain visible grid lines
        mapGridPane.getColumnConstraints().clear();
        mapGridPane.getRowConstraints().clear();
    }

    private void drawMap() {
//        infoLabel.setText(worldMap.toString());
        clearGrid();
        Boundary currentBounds =  worldMap.getCurrentBounds();

        updateMapInfo(currentBounds);
        drawHeader(currentBounds);
        drawFirstColumn(currentBounds);
        drawAllObjects(currentBounds);
    }

    private void updateMapInfo(Boundary currentBounds) {
        int mapWidth = currentBounds.upperRight().getX() - currentBounds.lowerLeft().getX();
        int mapHeight = currentBounds.upperRight().getY() - currentBounds.lowerLeft().getY();
        cellWidth = MAX_MAP_WIDTH/ mapWidth;
        cellHeight = MAX_MAP_HEIGHT/ mapHeight;
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
                Label label = new Label(drawObject(new Vector2d(currentBounds.lowerLeft().getX()+x-1, currentBounds.upperRight().getY()-y+1)));
                GridPane.setHalignment(label, HPos.CENTER);
                mapGridPane.add(label, x, y);
            }
        }
    }

    private String drawObject(Vector2d currentPosition) {
        if (worldMap.isOccupied(currentPosition)) {
            Object object = worldMap.objectAt(currentPosition);
            if (object != null) {
                return object.toString();
            }
        }
        return EMPTY_CELL;
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            currentMove.setText(message);
            drawMap();
        });
    }
}
