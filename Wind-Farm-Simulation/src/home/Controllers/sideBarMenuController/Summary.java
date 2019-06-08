package home.Controllers.sideBarMenuController;

import home.Agents.FailuresInfo;
import home.Agents.Main;
import home.Controllers.SimulationController;
import home.ExceptionScreen;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Summary implements Initializable {

    @FXML
    public AnchorPane rootPane;


    @FXML
    public Button btnDashBoard;
    public Button optionsBtn;
    public Button summaryBtn;
    public Button failuresBtn;
    public Button weatherBtn;
    public Button realTimeBtn;

    public Label turbiny_wydatki_txt;
    public Label naprawy_wydatki_txt;
    public Label inne_wydatki_txt;
    public Label zarobki_txt;
    public Label suma_txt;
    public Label turbiny_txt;
    public Label wiatr_txt;
    public Label zwrot_txt;

    public Button zarobki_wykres;
    public Button awarie_wykres;
    public Button elementy_wykres;
    public Button zwrot_wykres;

    public TableView<FailuresInfo> failuresTable;

    @FXML
    public void handleButtonClicks(javafx.event.ActionEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() == btnDashBoard) {
            loadStage(mouseEvent, "/home/fxml/DashBoard.fxml");
        }
        else if (mouseEvent.getSource() == optionsBtn) {
            loadStage(mouseEvent, "/home/fxml/Wind_Farm_Simulation.fxml");
        }
        else if (mouseEvent.getSource() == summaryBtn) {
            loadStage(mouseEvent, "/home/fxml/sideBar/Summary.fxml");
        }
        else if (mouseEvent.getSource() == failuresBtn) {
            loadStage(mouseEvent, "/home/fxml/sideBar/FailuresList.fxml");
        }
        else if (mouseEvent.getSource() == realTimeBtn) {
            loadStage(mouseEvent, "/home/fxml/sideBar/RealTimeSimulation.fxml");
        }
        else if (mouseEvent.getSource() == weatherBtn) {
            loadStage(mouseEvent, "/home/fxml/sideBar/Weather.fxml");
        }
        else if (mouseEvent.getSource() == zarobki_wykres) {
            try {
                displayChartMonthly();
            } catch (Exception e) {
            ExceptionScreen exceptionScreen = new ExceptionScreen("Brak danych!");
            }
        }
        else if (mouseEvent.getSource() == awarie_wykres) {
            try {
                displayBarChartQuantityOfEachFailureType();
            } catch (Exception e) {
                ExceptionScreen exceptionScreen = new ExceptionScreen("Brak danych!");
            }
        }
        else if (mouseEvent.getSource() == elementy_wykres) {
            try {
                displayPieChartOfCosts();
            } catch (Exception e) {
                ExceptionScreen exceptionScreen = new ExceptionScreen("Brak danych!");
            }
        }
        else if (mouseEvent.getSource() == zwrot_wykres) {
            try {
                String firstData = SimulationController.firstData;
                String lastData = SimulationController.lastData;

                if (SimulationController.fromFile == true) {
                    displayMonthlyBalance();
                }
                 else if ((LocalDate.parse(firstData).until(LocalDate.parse(lastData), ChronoUnit.DAYS)) > 60) {
                    displayMonthlyBalance();
                }  else {
                    displayChartDaily();
                }
            } catch (Exception e) {
                ExceptionScreen exceptionScreen = new ExceptionScreen("Brak danych!");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // todo
        // zainicjalizowac danymi z ekranu opcji symulacji


        turbiny_wydatki_txt.setText("" + String.format ("%.4f", Main.turbineExpenses)+ " PLN");
        naprawy_wydatki_txt.setText("" + String.format("%.4f", Main.failuresExpenses)+ " PLN");
        inne_wydatki_txt.setText("" + String.format("%.4f", Main.otherExpenses)+ " PLN");
        zarobki_txt.setText("" + String.format("%.4f", Main.earnings)+ " PLN");
        suma_txt.setText("" + String.format("%.4f", Main.total)+ " PLN");
        zwrot_txt.setText("" + Main.yearlyRateOfReturn);

        turbiny_txt.setText("" + Main.numberOfTurbines);
        wiatr_txt.setText("" + (double)Math.round(Main.averageWind * 100000d) / 100000d);

        try {
           // windowConsole.appendText(Main.showSimulationResults(new String[]{"fromApi", Main.numberOfTurbines, Main.cityName, Main.startDate, Main.endDate}));
        } catch (Exception e) {
            // e.printStackTrace();
            // todo
            // dodac otwerajace sie okno aby wybrac opcje wymulacji
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

    public void displayChartMonthly() throws Exception {

        if(Main.monthlyExpenses == null) {
            throw new Exception("NULL");
        }
        Stage stage_chats = new Stage();
        stage_chats.setTitle("Wykres zysków");

        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Miesiąc");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("PLN");

        final BarChart<String, Number> barChart =
                new BarChart<String, Number>(xAxis, yAxis);
        barChart.setTitle(Main.cityNameMonthlyBarTitle + "\n" + Main.startDate + "  " + Main.endDate);

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

    public void displayChartDaily() throws Exception {
        if(Main.monthlyExpenses == null) {
            throw new Exception("NULL");
        }
        Stage stage_chats = new Stage();
        stage_chats.setTitle("Wykres zysków");
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Dzień miesiąca");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("PLN");

        final BarChart<String, Number> barChart =
                new BarChart<String, Number>(xAxis, yAxis);
        barChart.setTitle(Main.cityNameMonthlyBarTitle + "\n" + Main.startDate + "  " + Main.endDate);

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

    public void displayPieChartOfCosts() throws Exception {
        if(Main.monthlyExpenses == null) {
            throw new Exception("NULL");
        }
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
        chart.setTitle(Main.cityNameMonthlyBarTitle + " Rozkład kosztów na kategorie");
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

    public void displayBarChartQuantityOfEachFailureType() throws Exception {
        if(Main.monthlyExpenses == null) {
            throw new Exception("NULL");
        }
        Stage stage_chats = new Stage();
        stage_chats.setTitle("Awarie");
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Nazwa awarii");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Ilość");

        final BarChart<String, Number> barChart =
                new BarChart<String, Number>(xAxis, yAxis);
        barChart.setTitle(Main.cityNameMonthlyBarTitle + " - Wykres ilości awarii danego typu w okresie: " + Main.startDate + "   " + Main.endDate);

        XYChart.Series series1 = new XYChart.Series();


        String namesForXAxis[] = {"Za duże napięcie (sieć)",
                "Awaryjne hamowanie (za duży wiatr)",
                "Zatrzymanie manualne",
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

    public void displayMonthlyBalance() throws Exception {
        if(Main.monthlyExpenses == null) {
            throw new Exception("NULL");
        }
        Stage stage_chats = new Stage();
        stage_chats.setTitle("Bilans przychodów i kosztów");

        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Miesiąc");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("PLN");

        final LineChart<String, Number> barChart =
                new LineChart<String, Number>(xAxis, yAxis);
        barChart.setTitle(Main.cityNameMonthlyBarTitle + "\n" + Main.startDate + "  " + Main.endDate);

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Miesięczny bilans"); //comiesieczna suma zysków w ujęciu jednego roku

        ArrayList<Double> sumOfBalance = Main.monthlyExpenses;
        ArrayList<String> namesForXAxis = Main.getNamesForXAxis();


        String[] months = {"Sty", "Lut", "Mar", "Kwi", "Maj", "Cze", "Lip", "Sie", "Wrz", "Paź", "Lis", "Gru"};
        for (int i = 0; i < sumOfBalance.size(); i++) {
            series1.getData().add(new XYChart.Data((months[(new Integer(namesForXAxis.get(i).split("-")[1]) - 1) % 12] + " " + namesForXAxis.get(i).split("-")[0]), sumOfBalance.get(i)));

        }

        Scene scene = new Scene(barChart, 800, 600);
        barChart.getData().addAll(series1);

        stage_chats.setScene(scene);
        stage_chats.show();
    }
}
