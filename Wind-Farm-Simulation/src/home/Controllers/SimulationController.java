package home.Controllers;

import home.Agents.FailuresInfo;
import home.Agents.Main;
import home.ExceptionScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SimulationController implements Initializable {

    @FXML
    public AnchorPane rootPane;

    @FXML
    public Button btnDashBoard;

    public RadioButton takeFromApi;
    public RadioButton takeFromFile;
    public TextField cityName;
    public ChoiceBox<String> chooseCity;
    public Button startButton;
    public TextField turbineNumber;
    public TextArea windowConsole;
    public DatePicker startDataPicker;
    public DatePicker endDataPicker;
    public TableView<FailuresInfo> failuresTable;

    public Button optionsBtn;
    public Button summaryBtn;
    public Button failuresBtn;
    public Button weatherBtn;
    public Button realTimeBtn;

    public static String firstData;
    public static String lastData;

    ObservableList<String> dataFromFile = FXCollections.observableArrayList("Kielce", "Linowo", "Gdansk");

    final ToggleGroup group = new ToggleGroup();

    public void takeDataFromApi() {
        cityName.setDisable(false);
        chooseCity.setDisable(true);
        startButton.setDisable(false);
        startDataPicker.setDisable(false);
        endDataPicker.setDisable(false);
        turbineNumber.setDisable(false);
    }

    public void takeDataFromFile() {
        cityName.setDisable(true);
        chooseCity.setDisable(false);
        startButton.setDisable(false);
        startDataPicker.setDisable(true);
        endDataPicker.setDisable(true);
        turbineNumber.setDisable(false);
    }

    @FXML
    public void handleButtonClicks(javafx.event.ActionEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() == btnDashBoard) {
            loadStage(mouseEvent, "/home/fxml/DashBoard.fxml");
        } else if (mouseEvent.getSource() == optionsBtn) {
            loadStage(mouseEvent, "/home/fxml/Wind_Farm_Simulation.fxml");
        } else if (mouseEvent.getSource() == summaryBtn) {
            loadStage(mouseEvent, "/home/fxml/sideBar/Summary.fxml");
        } else if (mouseEvent.getSource() == failuresBtn) {
            loadStage(mouseEvent, "/home/fxml/sideBar/FailuresList.fxml");
        } else if (mouseEvent.getSource() == realTimeBtn) {
            loadStage(mouseEvent, "/home/fxml/sideBar/RealTimeSimulation.fxml");
        } else if (mouseEvent.getSource() == weatherBtn) {
            loadStage(mouseEvent, "/home/fxml/sideBar/Weather.fxml");
        } else if (mouseEvent.getSource() == startButton) {
            Main.numberOfTurbines = turbineNumber.getText();
            Main.cityName = chooseCity.getValue();

            try {
                startSimulation();
                loadStage(mouseEvent, "/home/fxml/sideBar/Summary.fxml");
            } catch (Exception e) {
                ExceptionScreen exceptionScreen = new ExceptionScreen("Uzupełnij wszystkie pola!");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        takeFromApi.setToggleGroup(group);
        takeFromFile.setToggleGroup(group);

        chooseCity.setItems(dataFromFile);

        TableColumn turbineNo = new TableColumn<FailuresInfo, Integer>("Nr turbiny");
        TableColumn failureDescription = new TableColumn<FailuresInfo, String>("Opis awarii");
        TableColumn failureTime = new TableColumn<FailuresInfo, String>("Czas trwania");

        failureTime.setCellValueFactory(
                new PropertyValueFactory<FailuresInfo, Integer>("time"));

        turbineNo.setCellValueFactory(
                new PropertyValueFactory<FailuresInfo, String>("turbineNo"));

        failureDescription.setCellValueFactory(
                new PropertyValueFactory<FailuresInfo, String>("description"));

        //failuresTable.setItems(data);
        failuresTable.getColumns().addAll(turbineNo, failureDescription, failureTime);
    }

    @FXML
    public void loadStage(ActionEvent event, String fxml) throws IOException {
        Parent screenToLoad = FXMLLoader.load(getClass().getResource(fxml));
        Scene loadedScene = new Scene(screenToLoad);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(loadedScene);
        window.show();
    }

    public void startSimulation() throws Exception {
        ArrayList<Double> sumOfProfits;
        ArrayList<String> namesForXAxis;

        //private ArrayList<Double> monthlySumExpenses = Main.getPeriodProfits();
        windowConsole.clear();
        windowConsole.setVisible(false);
        windowConsole.setDisable(false);


        if (!cityName.isDisable()) {
            Main.cityNameMonthlyBarTitle = cityName.getText();
            firstData = startDataPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            lastData = endDataPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            windowConsole.appendText(Main.showSimulationResults(new String[]{"fromApi", turbineNumber.getText(), cityName.getText(), firstData, lastData}));

            if ((LocalDate.parse(firstData).until(LocalDate.parse(lastData), ChronoUnit.DAYS)) > 60) {

            } else {

            }

        } else if (!chooseCity.isDisable()) {
            Main.cityNameMonthlyBarTitle = chooseCity.getValue();
            windowConsole.appendText(Main.showSimulationResults(new String[]{"fromFile", turbineNumber.getText(), chooseCity.getValue()}));

        }
    }


}
