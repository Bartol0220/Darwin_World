package agh.ics.oop.presenter;

import agh.ics.oop.*;
import agh.ics.oop.fileManager.SimulationReaderCSV;
import agh.ics.oop.fileManager.SimulationSaverCSV;
import agh.ics.oop.fileManager.StatsSaverCSV;
import agh.ics.oop.model.*;
import agh.ics.oop.errors.*;
import agh.ics.oop.model.genes.ClassicMutation;
import agh.ics.oop.model.genes.GeneMutator;
import agh.ics.oop.model.genes.GenesFactory;
import agh.ics.oop.model.genes.SlightCorrection;
import agh.ics.oop.model.grass.AbstractGrassMaker;
import agh.ics.oop.model.grass.GrassMakerDeadAnimal;
import agh.ics.oop.model.grass.GrassMakerEquator;
import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.simulation.SimulationConfig;
import agh.ics.oop.simulation.SimulationEngine;
import agh.ics.oop.stats.Stats;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SetupPresenter {
    private static int simulationCounter = 0;
    static final Map<SimulationEngine, Stage> simulationsMap = new HashMap<>();
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
    @FXML
    private Label errorLabel;
    @FXML
    private Label savedLabel;
    @FXML
    private TextField fileNameField;
    @FXML
    private TextField saverNameField;
    @FXML
    private TextField saveStatsFileName;

    public boolean stopAllSimulations() throws InterruptedException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit dialogs");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("All simulations will be terminated.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.filter(presentRes -> presentRes == ButtonType.OK).isPresent()) {
            for (SimulationEngine simulationEngine : simulationsMap.keySet()) {
                simulationEngine.pauseSimulations();
                simulationsMap.get(simulationEngine).close();
            }
            return true;
        }
        return false;
    }

    public void onSetupSaveClicked() {
        savedLabel.setText("");
        try {
            SimulationSaverCSV saver = new SimulationSaverCSV();
            SimulationConfig simulationConfig = configurateSimulation();
            saver.saveToCSV(simulationConfig, saverNameField.getText());
            savedLabel.setText("File saved.");
        } catch (FileAlreadyExistsException | HasToBePositiveException | HasToBeBit | BreedingCanNotKillAnimals |
                 CanNotBeNegativeException | MutationChangesCanNotExceedSize | MinMaxGeneException exception) {
            errorLabel.setText(exception.getMessage());
        } catch (IOException exception) {
            errorLabel.setText("The system cannot find the specified path.");
        }
    }

    public void onSetupReadClicked() {
        savedLabel.setText("");
        try {
            String fileName = fileNameField.getText();
            SimulationReaderCSV simulationReader = new SimulationReaderCSV();
            SimulationConfig simulationConfig = simulationReader.readFromCSV(fileName);
            prepareSimulation(simulationConfig);
        } catch (FailedToReadConfig exception) {
            errorLabel.setText(exception.getMessage());
        }
    }

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

        errorLabel.setText("");
        savedLabel.setText("");
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
        saveStatsBox.setSelected(false);
    }

    public SimulationConfig configurateSimulation() throws HasToBePositiveException, HasToBeBit,
            BreedingCanNotKillAnimals, CanNotBeNegativeException, MutationChangesCanNotExceedSize, MinMaxGeneException {

        errorLabel.setText("");
        savedLabel.setText("");
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

        return new SimulationConfig(
                height, width, startGrassNumber, energyProvidedByEatingGrass, dayGrassNumber, grassMakerVariant,
                startNumberOfAnimals, startingEnergy, energyNeededForBreeding, energyUsedWhileBreeding,
                minimumNumberOfMutations, maximumNumberOfMutations, genesMutatorVariant, genesNumber
        );
    }

    public void onSetupStartClicked() {
        try {
            SimulationConfig simulationConfig = configurateSimulation();
            prepareSimulation(simulationConfig);

        } catch (HasToBePositiveException | HasToBeBit | BreedingCanNotKillAnimals | CanNotBeNegativeException |
                MutationChangesCanNotExceedSize | MinMaxGeneException exception) {
            errorLabel.setText(exception.getMessage());
        }
    }

    private void prepareSimulation(SimulationConfig simulationConfig) {
        try {
            GlobeMap map = new GlobeMap(simulationConfig.getWidth(), simulationConfig.getHeight(), simulationCounter);
            simulationCounter++;

            AbstractGrassMaker grassMaker = chooseGrassMaker(simulationConfig, map);

            Stats stats = new Stats(
                    map, grassMaker, simulationConfig.getStartGrassNumber(), simulationConfig.getStartingEnergy(),
                    simulationConfig.getStartNumberOfAnimals()
            );

            AnimalCreator animalCreator = prepareAnimalCreator(simulationConfig, stats);

            Breeding breeding = new Breeding(
                    simulationConfig.getEnergyNeededForBreeding(), simulationConfig.getEnergyUsedWhileBreeding(), map, animalCreator
            );

            Simulation simulation = new Simulation(
                    map, grassMaker, breeding, animalCreator, simulationConfig.getStartNumberOfAnimals(), stats
            );

            Optional<StatsSaverCSV> statsSaverCSV = prepareStatsSaverCSV(stats);

            simulation.registerAnimalDiedObserver(stats);
            if (grassMaker instanceof GrassMakerDeadAnimal) {
                simulation.registerAnimalDiedObserver((GrassMakerDeadAnimal) grassMaker);
                simulation.registerNewDayObserver((GrassMakerDeadAnimal) grassMaker);
            }

            SimulationEngine simulationEngine = new SimulationEngine(simulation);
            SimulationApp newSimulationApp = new SimulationApp();

            Stage stage = new Stage();
            SimulationPresenter presenter = newSimulationApp.showSimulation(stage);

            simulation.registerSimulationErrorObserver(presenter);

            simulationsMap.put(simulationEngine, stage);

            statsSaverCSV.ifPresent(simulation::registerNewDayObserver);
            statsSaverCSV.ifPresent(presentSaver -> presentSaver.registerFailedToSaveObserver(presenter));

            presenter.newSimulation(map, simulationEngine, stage);

            stage.setOnCloseRequest(_ -> {
                try {
                    simulationEngine.pauseSimulations();
                    simulationsMap.remove(simulationEngine);
                } catch (InterruptedException e) {
                    simulation.notifySimulationErrorObserver("The simulation could not be closed properly.");
                }
            });
        } catch (FileAlreadyExistsException exception) {
            errorLabel.setText(exception.getMessage());
        } catch (IOException exception) {
            errorLabel.setText(
                    "The system cannot find the specified path. Please try again or run the program without saving statistics."
            );
        }
    }

    private Optional<StatsSaverCSV> prepareStatsSaverCSV(Stats stats) throws IOException {
        Optional<StatsSaverCSV> statsSaverCSV = Optional.empty();
        if (saveStatsBox.isSelected()) {
            statsSaverCSV = Optional.of(new StatsSaverCSV(stats, saveStatsFileName.getText()));
        }
        return statsSaverCSV;
    }

    private AbstractGrassMaker chooseGrassMaker(SimulationConfig simulationConfig, GlobeMap map) {
        if (simulationConfig.getGrassMakerVariant() == 0) {
            return new GrassMakerEquator(simulationConfig.getStartGrassNumber(), simulationConfig.getDayGrassNumber(), map);
        }
        return new GrassMakerDeadAnimal(simulationConfig.getStartGrassNumber(), simulationConfig.getDayGrassNumber(), map);
    }

    private AnimalCreator prepareAnimalCreator(SimulationConfig simulationConfig, Stats stats) {
        GeneMutator geneMutator;
        if (simulationConfig.getGenesMutatorVariant() == 0) {
            geneMutator = new ClassicMutation(
                    simulationConfig.getMinimumNumberOfMutations(), simulationConfig.getMaximumNumberOfMutations()
            );
        } else {
            geneMutator = new SlightCorrection(
                    simulationConfig.getMinimumNumberOfMutations(), simulationConfig.getMaximumNumberOfMutations()
            );
        }

        GenesFactory genesFactory = new GenesFactory(geneMutator, simulationConfig.getGenesNumber());

        return new AnimalCreator(
                simulationConfig.getStartingEnergy(), simulationConfig.getEnergyUsedWhileBreeding(),
                simulationConfig.getEnergyProvidedByEatingGrass(), genesFactory, stats
        );
    }
}
