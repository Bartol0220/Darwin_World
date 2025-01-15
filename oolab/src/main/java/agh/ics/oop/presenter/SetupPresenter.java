package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationApp;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.StatsSaverCSV;
import agh.ics.oop.model.*;
import agh.ics.oop.model.genes.ClassicMutation;
import agh.ics.oop.model.genes.GeneMutator;
import agh.ics.oop.model.genes.GenesFactory;
import agh.ics.oop.model.genes.SlightCorrection;
import agh.ics.oop.model.grass.AbstractGrassMaker;
import agh.ics.oop.model.grass.GrassMakerDeadAnimal;
import agh.ics.oop.model.grass.GrassMakerEquator;
import agh.ics.oop.model.stats.Stats;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;

import java.io.IOException;

public class SetupPresenter {
    @FXML
    private Spinner<Integer> widthSpinner;
    @FXML
    private Spinner<Integer> heightSpinner;
    @FXML
    private Spinner<Integer> startGrassNumberSpinner;
    @FXML
    private Spinner<Integer> dayGrassNumberSpinner;
    @FXML
    private Spinner<Integer> energyProvidedByEatingGrassSpinner;
    @FXML
    private ComboBox<String> grassMakerBox;
    @FXML
    private Spinner<Integer> startNumberOfAnimalsSpinner;
    @FXML
    private Spinner<Integer> startingEnergySpinner;
    @FXML
    private Spinner<Integer> energyNeededForBreedingSpinner;
    @FXML
    private Spinner<Integer> energyUsedWhileBreedingSpinner;
    @FXML
    private Spinner<Integer> genesNumberSpinner;
    @FXML
    private Spinner<Integer> minimumNumberOfMutationsSpinner;
    @FXML
    private Spinner<Integer> maximumNumberOfMutationsSpinner;
    @FXML
    private ComboBox<String> genesMutatorBox;
    @FXML
    private CheckBox saveStatsBox;

    public void initialize() {
        String [] grassMakerVariants = {"Forested equator", "Life-giving corpses"};
        grassMakerBox.getItems().clear();
        grassMakerBox.getItems().addAll(grassMakerVariants);
        grassMakerBox.getSelectionModel().select(0);

        String [] genesMutatorVariants = {"Complete randomness", "Slight correction"};
        genesMutatorBox.getItems().clear();
        genesMutatorBox.getItems().addAll(genesMutatorVariants);
        genesMutatorBox.getSelectionModel().select(0);
    }

    public void onSetupResetClicked() {
        initialize();

        widthSpinner.getValueFactory().setValue(25);
        heightSpinner.getValueFactory().setValue(25);
        startGrassNumberSpinner.getValueFactory().setValue(15);
        dayGrassNumberSpinner.getValueFactory().setValue(5);
        energyProvidedByEatingGrassSpinner.getValueFactory().setValue(10);
        startNumberOfAnimalsSpinner.getValueFactory().setValue(5);
        startingEnergySpinner.getValueFactory().setValue(30);
        energyNeededForBreedingSpinner.getValueFactory().setValue(15);
        energyUsedWhileBreedingSpinner.getValueFactory().setValue(10);
        genesNumberSpinner.getValueFactory().setValue(10);
        minimumNumberOfMutationsSpinner.getValueFactory().setValue(0);
        maximumNumberOfMutationsSpinner.getValueFactory().setValue(5);
    }

    public void onSetupStartClicked() throws IOException {
        int width = widthSpinner.getValue();
        int height = heightSpinner.getValue();
        int startGrassNumber = startGrassNumberSpinner.getValue();
        int dayGrassNumber = dayGrassNumberSpinner.getValue();
        int energyProvidedByEatingGrass = energyProvidedByEatingGrassSpinner.getValue();
        int grassMakerVariant = grassMakerBox.getSelectionModel().getSelectedIndex();
        int startNumberOfAnimals = startNumberOfAnimalsSpinner.getValue();
        int startingEnergy = startingEnergySpinner.getValue();
        int energyNeededForBreeding = energyNeededForBreedingSpinner.getValue();
        int energyUsedWhileBreeding = energyUsedWhileBreedingSpinner.getValue();
        int genesNumber = genesNumberSpinner.getValue();
        int minimumNumberOfMutations = minimumNumberOfMutationsSpinner.getValue();
        int maximumNumberOfMutations = maximumNumberOfMutationsSpinner.getValue();
        int genesMutatorVariant = genesMutatorBox.getSelectionModel().getSelectedIndex();
        boolean saveStats = saveStatsBox.isSelected();

        // TODO config

        GlobeMap map = new GlobeMap(width, height, 0);

        AbstractGrassMaker grassMaker;
        if (grassMakerVariant == 0) {
            grassMaker = new GrassMakerEquator(startGrassNumber, dayGrassNumber, map);
        } else {
            grassMaker = new GrassMakerDeadAnimal(startGrassNumber, dayGrassNumber, map);
        }

        Stats stats = new Stats(map, grassMaker, startGrassNumber, startingEnergy, startNumberOfAnimals);

        if (saveStats) {
            // TODO ADI zrób coś:((
            StatsSaverCSV statsSaverCSV = new StatsSaverCSV(stats, "stats1");
            map.registerObserver(statsSaverCSV);
        }

        GeneMutator geneMutator;
        if (genesMutatorVariant == 0) {
            geneMutator = new ClassicMutation(minimumNumberOfMutations, maximumNumberOfMutations);
        } else {
            geneMutator = new SlightCorrection(minimumNumberOfMutations, maximumNumberOfMutations);
        }

        GenesFactory genesFactory = new GenesFactory(geneMutator, genesNumber);

        AnimalCreator animalCreator = new AnimalCreator(startingEnergy, energyUsedWhileBreeding, energyProvidedByEatingGrass, genesFactory, stats);
        Breeding breeding = new Breeding(energyNeededForBreeding, energyUsedWhileBreeding, map, animalCreator);

        Simulation simulation = new Simulation(map, grassMaker, breeding, animalCreator, startNumberOfAnimals, stats);

        simulation.registerAnimalDiedObserver(stats);
        if (grassMaker instanceof GrassMakerDeadAnimal) {
            simulation.registerAnimalDiedObserver((GrassMakerDeadAnimal) grassMaker);
            simulation.registerNewDayObserver((GrassMakerDeadAnimal) grassMaker);
        }

        SimulationEngine simulationEngine = new SimulationEngine(simulation);
        SimulationApp newSimulationApp = new SimulationApp();

        try {
            Stage stage = new Stage();
            SimulationPresenter presenter = newSimulationApp.showSimulation(stage);
            presenter.newSimulation(map, simulationEngine, stage);
        } catch (IOException e) {
            e.printStackTrace();
        }


//        List<MoveDirection> directions = OptionsParser.parse(moves.getText().split(" "));
//        GrassField map = new GrassField(4, 0);
//        List<Vector2d> positions = List.of(new Vector2d(1,1), new Vector2d(3,3));
//        Simulation simulation = new Simulation(positions, map, directions, 10, 3, new GrassMakerEquator(1, 1, 1, 1));
//        SimulationEngine simulationEngine = new SimulationEngine(List.of(simulation));
//
//        SimulationApp newSimulationApp = new SimulationApp();
//
//        try {
//            SimulationPresenter presenter = newSimulationApp.showSimulation(new Stage());
//            presenter.newSimulation(map, simulationEngine);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }
}
