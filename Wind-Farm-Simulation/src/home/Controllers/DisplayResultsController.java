package home.Controllers;

import home.Agents.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DisplayResultsController {
    // lista dostepnych miast dla których mamy dane historyczne
    ObservableList<String> dataFromFile = FXCollections.observableArrayList("Kielce", "Linowo", "Gdańsk");

    // wszytskie elemety okna

    public RadioButton takeFromApi;
    public RadioButton takeFromFile;
    public TextField cityName;
    public ChoiceBox<String> chooseCity;
    public Button startButton;
    public TextArea windowConsole;

    //grupa dla radioButtons (zapobiega wybraniu ubu opcji)
    final ToggleGroup group = new ToggleGroup();

    @FXML
    public void initialize() {

        takeFromApi.setToggleGroup(group);
        takeFromFile.setToggleGroup(group);

        chooseCity.setItems(dataFromFile);
    }

    public void takeDataFromApi() {
        cityName.setDisable(false);
        chooseCity.setDisable(true);
        startButton.setDisable(false);
    }

    public void takeDataFromFile() {
        cityName.setDisable(true);
        chooseCity.setDisable(false);
        startButton.setDisable(false);

    }

    public void startSimulation() throws Exception {
        ArrayList<Double> sumOfProfits ;
        //private ArrayList<Double> monthlySumExpenses = Main.getMonthlyProfits();
        Double allExpences ;

        if(!cityName.isDisable()) {
            windowConsole.clear();
            windowConsole.setVisible(true);
            windowConsole.setDisable(false);
            windowConsole.appendText(Main.showSimulationResults(new String[]{cityName.getText(), "fromApi"}));

            sumOfProfits = Main.getMonthlyProfits();
            allExpences = Main.getOtherExpenses() + Main.getTurbineExpenses();

            Stage stage_chats = new Stage();
            stage_chats.setTitle("Wykres 1.");
            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Day");
            yAxis.setLabel("PLN");
            final LineChart<Number,Number> lineChart =
                    new LineChart<Number, Number>(xAxis,yAxis);

            lineChart.setTitle(cityName.getText()+" - kwiecień 2018");

            XYChart.Series series1 = new XYChart.Series();
            series1.setName("Daily profits"); //comiesieczna suma zysków w ujęciu jednego roku

            XYChart.Series series2 = new XYChart.Series();
            series2.setName("Daily expenses");

            XYChart.Series series3 = new XYChart.Series(); //to będzie chyba bardziej przydatne jeśli wprowadzimy awaryjność do modelu
            series3.setName("Daily balance of profits and expenses");

            //String[] months = {"1","2","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
            for (int i= 0; i<30; i++ ) {
                series1.getData().add(new XYChart.Data(i, sumOfProfits.get(i)));
                series2.getData().add(new XYChart.Data(i, allExpences));
                allExpences -= sumOfProfits.get(i);
            }

            Scene scene  = new Scene(lineChart,800,600);
            lineChart.getData().addAll(series1, series2);

            stage_chats.setScene(scene);
            stage_chats.show();
        }
        else if(!chooseCity.isDisable()) {
            windowConsole.clear();
            windowConsole.setVisible(true);
            windowConsole.setDisable(false);
            windowConsole.appendText(Main.showSimulationResults(new String[]{chooseCity.getValue(), "fromFile"}));

            sumOfProfits = Main.getMonthlyProfits();
            allExpences = Main.getOtherExpenses() + Main.getTurbineExpenses();

            Stage stage_chats = new Stage();
            stage_chats.setTitle("Wykres 1.");
            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Month");
            yAxis.setLabel("PLN");
            final LineChart<String,Number> lineChart =
                    new LineChart<String,Number>(xAxis,yAxis);

            lineChart.setTitle("Kielce - 2018");

            XYChart.Series series1 = new XYChart.Series();
            series1.setName("Monthly profits"); //comiesieczna suma zysków w ujęciu jednego roku

            XYChart.Series series2 = new XYChart.Series();
            series2.setName("Monthly expenses");

            XYChart.Series series3 = new XYChart.Series(); //to będzie chyba bardziej przydatne jeśli wprowadzimy awaryjność do modelu
            series3.setName("Monthly balance of profits and expenses");

            String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
            for (int i= 0; i<12; i++ ) {
                series1.getData().add(new XYChart.Data(months[i], sumOfProfits.get(i)));
                series2.getData().add(new XYChart.Data(months[i], allExpences));
                allExpences -= sumOfProfits.get(i);
            }

            Scene scene  = new Scene(lineChart,800,600);
            lineChart.getData().addAll(series1, series2);

            stage_chats.setScene(scene);
            stage_chats.show();
        }
    }

    @FXML
    public AnchorPane rootPane;

    @FXML
    public Button btnDashBoard;

    @FXML
    public void handleButtonClicks(javafx.event.ActionEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() == btnDashBoard) {
            loadStage(mouseEvent,"/home/fxml/DashBoard.fxml");
        }
    }

    @FXML
    public void loadStage(ActionEvent event, String fxml) throws IOException {
        Parent screenToLoad = FXMLLoader.load(getClass().getResource(fxml));
        Scene loadedScene = new Scene(screenToLoad);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(loadedScene);
        window.show();
    }
}
