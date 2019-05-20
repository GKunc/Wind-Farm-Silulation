package home.Controllers;

import home.Agents.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
    public TextField startDate;
    public TextField endDate;
    public TextField turbineNumber;
    public TextArea windowConsole;

    ObservableList<String> dataFromFile = FXCollections.observableArrayList("Kielce", "Linowo", "Gdansk");

    final ToggleGroup group = new ToggleGroup();

    public void takeDataFromApi() {
        cityName.setDisable(false);
        chooseCity.setDisable(true);
        startButton.setDisable(false);
        startDate.setDisable(false);
        endDate.setDisable(false);
        turbineNumber.setDisable(false);
    }

    public void takeDataFromFile() {
        cityName.setDisable(true);
        chooseCity.setDisable(false);
        startButton.setDisable(false);
        startDate.setDisable(true);
        endDate.setDisable(true);
        turbineNumber.setDisable(false);
    }

    @FXML
    public void handleButtonClicks(javafx.event.ActionEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() == btnDashBoard) {
            loadStage(mouseEvent, "/home/fxml/DashBoard.fxml");
        }
        if (mouseEvent.getSource() == startButton) {
            try {
                startSimulation();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        takeFromApi.setToggleGroup(group);
        takeFromFile.setToggleGroup(group);

        chooseCity.setItems(dataFromFile);
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

        if (!cityName.isDisable()) {
            windowConsole.clear();
            windowConsole.setVisible(true);
            windowConsole.setDisable(false);
            windowConsole.appendText(Main.showSimulationResults(new String[]{"fromApi",  turbineNumber.getText(), cityName.getText(), startDate.getText(), endDate.getText()}));

            sumOfProfits = Main.getPeriodProfits();
            namesForXAxis = Main.getNamesForXAxis();

            Stage stage_chats = new Stage();
            stage_chats.setTitle("Wykres 1.");
            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Dzień");
            yAxis.setLabel("PLN");
            final BarChart<String, Number> lineChart =
                    new BarChart<String, Number>(xAxis, yAxis);

            lineChart.setTitle(cityName.getText() + "\n" + Main.startDate + "  " + Main.endDate);

            XYChart.Series series1 = new XYChart.Series();
            series1.setName("Dzienne dochody"); //comiesieczna suma zysków w ujęciu jednego roku


            //String[] months = {"1","2","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
            for (Integer i = 0; i < namesForXAxis.size(); i++) {
                series1.getData().add(new XYChart.Data(namesForXAxis.get(i), sumOfProfits.get(i)));
            }

            Scene scene = new Scene(lineChart, 1400, 600);
            lineChart.getData().addAll(series1);

            stage_chats.setScene(scene);
            stage_chats.show();
        } else if (!chooseCity.isDisable()) {
            windowConsole.clear();
            windowConsole.setVisible(true);
            windowConsole.setDisable(false);
            windowConsole.appendText(Main.showSimulationResults(new String[]{"fromFile", turbineNumber.getText(),chooseCity.getValue()}));

            sumOfProfits = Main.getPeriodProfits();
            namesForXAxis = Main.getNamesForXAxis();

            Stage stage_chats = new Stage();
            stage_chats.setTitle("Wykres 1.");
            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Miesiąc");
            yAxis.setLabel("PLN");
            final BarChart<String, Number> lineChart =
                    new BarChart<>(xAxis, yAxis);

            lineChart.setTitle(chooseCity.getValue() + "\n" + Main.startDate + "  " + Main.endDate);

            XYChart.Series series1 = new XYChart.Series();
            series1.setName("Miesięczne dochody"); //comiesieczna suma zysków w ujęciu jednego roku


            String[] months = {"Sty", "Lut", "Mar", "Kwi", "Maj", "Cze", "Lip", "Sie", "Wrz", "Paź", "Lis", "Gru"};
            for (int i = 0; i < sumOfProfits.size(); i++) {
                series1.getData().add(new XYChart.Data(months[(new Integer(namesForXAxis.get(i)) - 1) % 12], sumOfProfits.get(i)));
            }

            Scene scene = new Scene(lineChart, 800, 600);
            lineChart.getData().addAll(series1);

            stage_chats.setScene(scene);
            stage_chats.show();
        }
    }
}
