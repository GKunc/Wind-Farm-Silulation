package home.Controllers;

import home.Agents.FailuresInfo;
import home.Agents.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

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
    public String cityNameMonthlyBarTitle;

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
                e.printStackTrace();
            }
//            try {
//                startSimulation();
//                loadStage(mouseEvent, "/home/fxml/sideBar/Summary.fxml");
//            } catch (Exception e) {
//               // ExceptionScreen exceptionScreen = new ExceptionScreen("Uzupełnij wszystkie pola!");
//                System.out.println(e.getMessage());
//            }
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
            cityNameMonthlyBarTitle = cityName.getText();
            String firstData = startDataPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String lastData = endDataPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            windowConsole.appendText(Main.showSimulationResults(new String[]{"fromApi", turbineNumber.getText(), cityName.getText(), firstData, lastData}));

            if ((LocalDate.parse(firstData).until(LocalDate.parse(lastData), ChronoUnit.DAYS)) > 60) {
                displayChartMonthly();
                displayPieChartOfCosts();
            } else {
                displayChartDaily();
                displayPieChartOfCosts();
            }

        } else if (!chooseCity.isDisable()) {
            cityNameMonthlyBarTitle = chooseCity.getValue();
            windowConsole.appendText(Main.showSimulationResults(new String[]{"fromFile", turbineNumber.getText(), chooseCity.getValue()}));
            displayChartMonthly();
            displayPieChartOfCosts();
            displayBarChartQuantityOfEachFailureType();

        }
    }

    public void displayChartMonthly() {
        Stage stage_chats = new Stage();
        stage_chats.setTitle("Wykres zysków");

        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Miesiąc");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("PLN");

        final BarChart<String, Number> barChart =
                new BarChart<String, Number>(xAxis, yAxis);
        barChart.setTitle(cityNameMonthlyBarTitle + "\n" + Main.startDate + "  " + Main.endDate);

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Miesięczne dochody"); //comiesieczna suma zysków w ujęciu jednego roku

        ArrayList<Double> sumOfProfits = Main.getPeriodProfits();
        ArrayList<String> namesForXAxis = Main.getNamesForXAxis();


        String[] months = {"Sty", "Lut", "Mar", "Kwi", "Maj", "Cze", "Lip", "Sie", "Wrz", "Paź", "Lis", "Gru"};
        for (int i = 0; i < sumOfProfits.size(); i++) {
            series1.getData().add(new XYChart.Data((months[(new Integer(namesForXAxis.get(i).split("-")[1]) - 1) % 12] + " " + namesForXAxis.get(i).split("-")[0]), sumOfProfits.get(i)));

        }

        Scene scene = new Scene(barChart, 800, 600);
        barChart.getData().addAll(series1);

        stage_chats.setScene(scene);
        stage_chats.show();

        final ObservableList<FailuresInfo> data =
                FXCollections.observableArrayList(
                        Main.getListOfFailures()
                );
        failuresTable.setItems(data);
    }

    public void displayChartDaily() {
        Stage stage_chats = new Stage();
        stage_chats.setTitle("Wykres zysków");
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Dzień miesiąca");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("PLN");

        final BarChart<String, Number> barChart =
                new BarChart<String, Number>(xAxis, yAxis);
        barChart.setTitle(cityNameMonthlyBarTitle + "\n" + Main.startDate + "  " + Main.endDate);

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Dzienne dochody"); //comiesieczna suma zysków w ujęciu jednego roku

        ArrayList<Double> sumOfProfits = Main.getPeriodProfits();
        ArrayList<String> namesForXAxis = Main.getNamesForXAxis();


        for (Integer i = 0; i < namesForXAxis.size(); i++) {
            series1.getData().add(new XYChart.Data(namesForXAxis.get(i), sumOfProfits.get(i)));
        }

        Scene scene = new Scene(barChart, 800, 600);
        barChart.getData().addAll(series1);

        stage_chats.setScene(scene);
        stage_chats.show();

        final ObservableList<FailuresInfo> data =
                FXCollections.observableArrayList(
                        Main.getListOfFailures()
                );
        failuresTable.setItems(data);
    }

    public void displayPieChartOfCosts() {
        Scene scene = new Scene(new Group());
        Stage stage = new Stage();
        stage.setTitle("Koszty");
        stage.setWidth(600);
        stage.setHeight(600);

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Koszty awarii", (100 * Main.getFailuresExpenses() / (Main.getFailuresExpenses() + Main.getOtherExpenses()))),
                        new PieChart.Data("Koszty konserwacji", (100 * Main.getOtherExpenses() / (Main.getFailuresExpenses() + Main.getOtherExpenses()))));


        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle(cityNameMonthlyBarTitle + " Rozkład kosztów na kategorie");
        chart.setLabelLineLength(10);
        chart.setLegendSide(Side.LEFT);

        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");

        for (final PieChart.Data data : chart.getData())

        {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            caption.setTranslateX(e.getSceneX());
                            caption.setTranslateY(e.getSceneY());
                            caption.setText(String.valueOf(data.getPieValue()) + "%");
                        }
                    });
        }
        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }

    public void displayBarChartQuantityOfEachFailureType() {
        Stage stage_chats = new Stage();
        stage_chats.setTitle("Awarie");
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Nazwa awarii");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Ilość");

        final BarChart<String, Number> barChart =
                new BarChart<String, Number>(xAxis, yAxis);
        barChart.setTitle(cityNameMonthlyBarTitle + " - Wykres ilości awarii danego typu w okresie: " + Main.startDate + "   " + Main.endDate);

        XYChart.Series series1 = new XYChart.Series();


        String namesForXAxis[] = {"Za duże napięcie (sieć)",
                "Awaryjne hamowanie (za duży wiatr)",
                "Pauza kliknięta na klawiaturze",
                "Wysoka temperatura",
                "Awaria konwertera napięcia",
                "Za wysoka moc",
                "Awaria skrzyni biegów",
                "Awaria łopat"};


        for (Integer i = 0; i < 8; i++) {
            series1.getData().add(new XYChart.Data(namesForXAxis[i], Main.quantityOfEachFailureType[i]));
        }

        Scene scene = new Scene(barChart, 800, 600);
        barChart.getData().addAll(series1);

        stage_chats.setScene(scene);
        stage_chats.show();
    }
}
