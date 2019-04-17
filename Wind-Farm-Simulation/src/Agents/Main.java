package Agents;

import java.util.ArrayList;

public class Main {
    private static ArrayList<Turbine> turbines;

    private static Double earnings = 0.0;
    private static Double turbineExpenses = 0.0;
    private static Double otherExpenses = 0.0;
    private static ArrayList<Double> dailyExpenses;
    private static ArrayList<Double> monthlyExpenses;

    private static double total = 0;

    // stworzyc zadana ilosc turbin
    // inne wydatki 36% ceny turbin
    // zaladowac pogode
    // zaczac symulacje

    public static void startSimulation(int numberOfTurbines) throws Exception {
        turbines = new ArrayList<>();

        for(int i = 0; i < numberOfTurbines; ++i) {
            Main.buildTurbine();
        }

        otherExpenses = 0.36 * turbineExpenses;
        Weather w = new Weather();
        //weather.downloadWeather();
        ArrayList<Weather> weathers = w.parseWeatherFromFile("./res/weatherGdansk.csv");
        for(Weather weather : weathers) { // dla kazdego zapisu z pogody
             for(Turbine turbine : turbines) { // osobno dla kazdej turbiny
                 earnings += turbine.calculateEarnings(weather);
                 turbineExpenses += turbine.calculateExpenses();
             }
        }
        total = earnings - turbineExpenses - otherExpenses;
    }

    public static void buildTurbine() {
        turbineExpenses += 8338000; // cena jednej turbiny
        Turbine turbine = new Turbine(); // stworzenie turbiny ( automatycznie wlaczona
        turbines.add(turbine);
    }

    public static void main(String [] argv) throws Exception { // glowna klasa ktora bedzie przeliczac sume kostow i zarobkow
        Main.startSimulation(10);
        System.out.println("Turbiny: " + turbineExpenses);
        System.out.println("Inne: " + otherExpenses);
        System.out.println("Zarobione: " + earnings);
        System.out.println("Suma: " + Main.total);
    }
}
