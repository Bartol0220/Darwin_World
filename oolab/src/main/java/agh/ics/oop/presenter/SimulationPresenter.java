package agh.ics.oop.presenter;

import agh.ics.oop.SimulationEngine;
import agh.ics.oop.WorldElementBox;
import agh.ics.oop.WorldElementButton;
import agh.ics.oop.model.*;
import agh.ics.oop.model.util.Boundary;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
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
    private SimulationEngine simulationEngine;
    private boolean running = true;
    private static final String EMPTY_CELL = "";
    private int cellWidth;
    private int cellHeight;
    private GlobeMap map;
    private Stage stage;
    private Optional<Animal> selectedAnimal = Optional.empty();

    @FXML
    private GridPane mapGridPane;
    @FXML
    private Button playPauseButton;
    @FXML
    private Label info;
    @FXML
    private Label messageLabel;
    @FXML
    private Label currentAnimalCount;
    @FXML
    private Label maximumAnimalCount;
    @FXML
    private Label minimumAnimalCount ;
    @FXML
    private Label allAnimalCount;
    @FXML
    private Label grassCount;
    @FXML
    private Label freeSpace;

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
        this.simulationEngine = simulationEngine;
        setMapDimensions();

        setMap(map);
        drawMap();

        simulationEngine.runAsync();
    }

    public void onSimulationPlayPauseClicked() {
        if (running) {
            running = false;
            playPauseButton.textProperty().setValue("Play");
            info.setText("Simulation is paused");
            try {
                simulationEngine.pauseSimulations();
            } catch (InterruptedException e) {
                // TODO dokończyć catcha
                System.err.println("Interrupted while simulating play");
            }
        } else {
            running = true;
            playPauseButton.textProperty().setValue("Pause");
            info.setText("Simulation is running");
            simulationEngine.playSimulations();
        }
    }

    public void onSimulationAnimalClicked(WorldElementButton button) {
        if (!running) {
            selectedAnimal = button.getAnimal();
        }
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
                Optional<WorldElementButton> worldElementBox = drawObject(new Vector2d(currentBounds.lowerLeft().getX()+x-1, currentBounds.upperRight().getY()-y+1));
                if (worldElementBox.isPresent()) {
                    worldElementBox.get().setOnAction(e -> {onSimulationAnimalClicked(worldElementBox.get());});
                    GridPane.setHalignment(worldElementBox.get(), HPos.CENTER);
                    mapGridPane.add(worldElementBox.get(), x, y);
                } else {
                    mapGridPane.add(new Label(EMPTY_CELL), x, y);
                }
            }
        }
    }

    private Optional<WorldElementButton> drawObject(Vector2d currentPosition) {
        return map.objectAt(currentPosition).map(object -> new WorldElementButton(object, cellWidth));
    }

    @Override
    public void mapChanged(GlobeMap map, String message) {
        Platform.runLater(() -> {
            Stats stats = simulationEngine.getStats();
            messageLabel.setText(message);
            drawMap();
            currentAnimalCount.setText(String.valueOf(stats.getCurrentAnimalCount()));
            maximumAnimalCount.setText(String.valueOf(stats.getMaximumAnimalCount()));
            minimumAnimalCount.setText(String.valueOf(stats.getMinimumAnimalCount()));
            allAnimalCount.setText(String.valueOf(stats.getAllAnimalCount()));
            grassCount.setText(String.valueOf(stats.getGrassCount()));
            freeSpace.setText(String.valueOf(stats.getFreeSpace()));

            // TODO chyba nie freeSpace XD
            selectedAnimal.ifPresent(animal -> freeSpace.setText(animal.getAnimalStats().toString()));
        });
    }
}
