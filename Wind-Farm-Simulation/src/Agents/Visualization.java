package Agents;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Visualization extends Application {
    private ArrayList<Double> monthlySumProfits = Main.getMonthlyProfits();
    //private ArrayList<Double> monthlySumExpenses = Main.getMonthlyProfits();
    private Double allExpences = Main.getOtherExpenses() + Main.getTurbineExpenses();
    @Override public void start(Stage stage) {
        stage.setTitle("Wykres 1.");
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
            series1.getData().add(new XYChart.Data(months[i], monthlySumProfits.get(i)));
            series2.getData().add(new XYChart.Data(months[i], allExpences));
            allExpences -= monthlySumProfits.get(i);
        }

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().addAll(series1, series2);

        stage.setScene(scene);
        stage.show();
    }

    public static void main() {
        launch();
    }
}