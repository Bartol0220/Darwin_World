package agh.ics.oop.presenter;

import agh.ics.oop.SimulationEngine;
import agh.ics.oop.WorldElementBox;
import agh.ics.oop.AnimalButton;
import agh.ics.oop.model.*;
import agh.ics.oop.model.observers.FailedToSaveObserver;
import agh.ics.oop.model.observers.MapChangeObserver;
import agh.ics.oop.model.stats.Stats;
import agh.ics.oop.model.util.Boundary;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Optional;

public class SimulationPresenter implements MapChangeObserver, FailedToSaveObserver {
    private double stageWidth;
    private double stageHeight;
    private SimulationEngine simulationEngine;
    private boolean running = true;
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
    @FXML
    private Label averageEnergy;
    @FXML
    private Label averageBirthrate;
    @FXML
    private Label mostCommonGenes;
    @FXML
    private Label deadAnimalCount;
    @FXML
    private Label averageLifespan;
    @FXML
    private Label threadSleepDisplay;
    @FXML
    private Slider threadSleep;
    @FXML
    private Label animalInfo;
    @FXML
    private Label animalStats;
    @FXML
    private ProgressBar animalEnergyProgressBar;
    @FXML
    private Label animalEnergyInfo;
    @FXML
    private Label errorLabel;

    private void setStageDimensions() {
        stageWidth = stage.getWidth() - 520;
        stageHeight = stage.getHeight() - 120;
    }

    private void setMap(GlobeMap map) {
        this.map = map;
    }

    public void newSimulation(GlobeMap map, SimulationEngine simulationEngine, Stage stage) {
        map.registerObserver(this);
        this.stage = stage;
        this.simulationEngine = simulationEngine;
        setStageDimensions();

        setMap(map);
        drawMap();

        simulationEngine.runAsync();

        threadSleep.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    threadSleepDisplay.setText(String.format("%d", newValue.intValue()));
                    simulationEngine.changeSleepingTime(newValue.intValue());
                });
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

    public void onSimulationAnimalClicked(AnimalButton button) {
        if (selectedAnimal.isPresent() && (button.getAnimal() == selectedAnimal.get())) {
            selectedAnimal = Optional.empty();
        } else {
            selectedAnimal = Optional.of(button.getAnimal());
        }
        selectedAnimal
                .ifPresentOrElse(
                        (_ -> animalInfo.setText("Statistics of the selected animal.")),
                        (() -> animalInfo.setText("To select an animal, stop the simulation and click on the chosen one."))
                );
        drawMap();
        drawStats();
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
        if (running) {
            drawAllObjects(currentBounds);
        } else {
            drawAllObjectsPaused(currentBounds);
        }
    }

    private void updateMapInfo(Boundary currentBounds) {
        int mapWidth = currentBounds.upperRight().getX() - currentBounds.lowerLeft().getX() + 1;
        int mapHeight = currentBounds.upperRight().getY() - currentBounds.lowerLeft().getY() + 1;
        cellWidth = (int) stageWidth / mapWidth;
        cellHeight = (int) stageHeight / mapHeight;
        cellHeight = Math.min(60, Math.min(cellWidth, cellHeight));
        cellWidth = cellHeight;
    }

    private void drawHeader(Boundary currentBounds) {
        Label label = new Label("y/x");
        label.setFont(new Font("Arial", cellHeight*0.65));
        mapGridPane.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
        mapGridPane.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        mapGridPane.getRowConstraints().add(new RowConstraints(cellHeight));

        int cnt = 1;
        for (int j = currentBounds.lowerLeft().getX(); j < currentBounds.upperRight().getX() + 1; j++) {
            mapGridPane.getColumnConstraints().add(new ColumnConstraints(cellWidth));
            label = new Label(String.format("%d", j));
            label.setFont(new Font("Arial", cellHeight*0.65));
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
            label.setFont(new Font("Arial", cellHeight*0.60));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGridPane.add(label, 0, cnt);
            cnt++;
        }
    }

    private void drawAllObjects(Boundary currentBounds) {
        for(int y = 1; y < currentBounds.upperRight().getY() - currentBounds.lowerLeft().getY() + 2; y++) {
            for(int x = 1; x < currentBounds.upperRight().getX() - currentBounds.lowerLeft().getX() + 2; x++) {
                Optional<WorldElementBox> worldElementBox = drawObject(new Vector2d(currentBounds.lowerLeft().getX()+x-1, currentBounds.upperRight().getY()-y+1));

                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle(x, y, cellWidth, cellHeight);
                rectangle.setFill(Color.GREEN);
                rectangle.setOpacity(0.2);
                stackPane.getChildren().add(rectangle);

                if (worldElementBox.isPresent()) {
                    GridPane.setHalignment(worldElementBox.get(), HPos.CENTER);
                    stackPane.getChildren().add(worldElementBox.get());
                    mapGridPane.add(stackPane, x, y);
                } else {
                    mapGridPane.add(stackPane, x, y);
                }
            }
        }
    }

    private void drawAllObjectsPaused(Boundary currentBounds) {
        Stats stats = simulationEngine.getStats();
        for(int y = 1; y < currentBounds.upperRight().getY() - currentBounds.lowerLeft().getY() + 2; y++) {
            for(int x = 1; x < currentBounds.upperRight().getX() - currentBounds.lowerLeft().getX() + 2; x++) {
                Vector2d position = new Vector2d(currentBounds.lowerLeft().getX()+x-1, currentBounds.upperRight().getY()-y+1);

                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle(x, y, cellWidth, cellHeight);
                rectangle.setFill(Color.GREEN);
                rectangle.setOpacity(0.2);
                if (map.isFieldBetter(position)) {
                    rectangle.setOpacity(0.4);
                }
                stackPane.getChildren().add(rectangle);

                Optional<WorldElement> worldElement = map.objectAt(position);
                if (worldElement.isPresent()) {
                    if (worldElement.get() instanceof Animal) {
                        Animal animal = (Animal) worldElement.get();
                        AnimalButton button = new AnimalButton(animal, selectedAnimal, cellWidth);
                        button.setOnAction(_ -> onSimulationAnimalClicked(button));
                        GridPane.setHalignment(button, HPos.CENTER);
//                        stackPane.getChildren().add(button);

                        ProgressBar progressBar = new ProgressBar();
                        progressBar.setProgress(animal.getAnimalStats().getEnergy()/ stats.getDayMaximumEnergy());
//                        stackPane.getChildren().add(progressBar);

                        VBox vBox = new VBox();
                        vBox.setStyle("-fx-background-color: transparent");
                        vBox.getChildren().add(progressBar);
                        vBox.getChildren().add(button);
                        stackPane.getChildren().add(vBox);

                        mapGridPane.add(stackPane, x, y);
                    } else {
                        WorldElementBox box = new WorldElementBox(worldElement.get(), selectedAnimal, cellWidth);
                        GridPane.setHalignment(box, HPos.CENTER);
                        stackPane.getChildren().add(box);
                        mapGridPane.add(stackPane, x, y);
                    }
                } else {
                    mapGridPane.add(stackPane, x, y);
                }
            }
        }
    }

    private Optional<WorldElementBox> drawObject(Vector2d currentPosition) {
        return map.objectAt(currentPosition).map(object -> new WorldElementBox(object, selectedAnimal, cellWidth));
    }

    @Override
    public void mapChanged(GlobeMap map, String message) {
        Platform.runLater(() -> {
            messageLabel.setText(message);
            drawMap();
            drawStats();
        });
    }

    private void drawStats() {
        Stats stats = simulationEngine.getStats();
        currentAnimalCount.setText(String.valueOf(stats.getCurrentAnimalCount()));
        maximumAnimalCount.setText(String.valueOf(stats.getMaximumAnimalCount()));
        minimumAnimalCount.setText(String.valueOf(stats.getMinimumAnimalCount()));
        allAnimalCount.setText(String.valueOf(stats.getAllAnimalCount()));
        grassCount.setText(String.valueOf(stats.getGrassCount()));
        freeSpace.setText(String.valueOf(stats.getFreeSpace()));
        averageEnergy.setText(String.format("%.1f", stats.getAverageEnergy()));
        averageBirthrate.setText(String.format("%.1f", stats.getAverageBirthrate()));
        mostCommonGenes.setText(String.valueOf(stats.getMostCommonGenes()));
        deadAnimalCount.setText(String.valueOf(stats.getDeadAnimalCount()));
        averageLifespan.setText(String.format("%.1f", stats.getAverageLifespan()));

        selectedAnimal
                .ifPresentOrElse(
                        (animal -> {
                            animalStats.setText(animal.getAnimalStats().toString());
                            animalEnergyInfo.setText("Energy:");
                            animalEnergyProgressBar.setVisible(true);
                            animalEnergyProgressBar.setProgress(animal.getAnimalStats().getEnergy()/stats.getDayMaximumEnergy());
                        }),
                        (() -> {
                            animalStats.setText("");
                            animalEnergyInfo.setText("");
                            animalEnergyProgressBar.setVisible(false);
                        })
                );
    }

    @Override
    public void failedToSave() {
        errorLabel.setText("Failed to save the file with statistics.");
    }
}
