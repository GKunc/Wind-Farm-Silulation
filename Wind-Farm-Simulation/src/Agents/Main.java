package Agents;

import java.util.ArrayList;

public class Main {
    private static ArrayList<Turbine> turbines;

    private static Double earnings = 0.0;
    private static Double turbineExpenses = 0.0;
    private static Double otherExpenses = 0.0;
    //private static ArrayList<Double> dailyExpenses;
    //private static ArrayList<Double> monthlyExpenses;

    private static double total = 0;

    // stworzyc zadana ilosc turbin
    // inne wydatki 36% ceny turbin
    // zaladowac pogode
    // zaczac symulacje

    public static void startSimulation(int years, int numberOfTurbines) throws Exception {
        turbines = new ArrayList<>();

        for(int i = 0; i < numberOfTurbines; ++i) {
            Main.buildTurbine();
        }

        Weather w = new Weather();
        //weather.downloadWeather();
        ArrayList<Weather> weathers = w.parseWeatherFromFile("./res/weatherKielce.csv");
        Weather.setWind("./res/windLinowo.csv", weathers);
        for(Weather weather : weathers) { // dla kazdego zapisu z pogody
            System.out.println(weather.getWind());
            for(Turbine turbine : turbines) { // osobno dla kazdej turbiny
                earnings += turbine.calculateEarnings(weather);
            }
        }

        earnings = earnings * years / 2; // bo symulacja dla pogody z 2 lat
        otherExpenses = 1200000.0 * years / 2;
        total =  earnings - turbineExpenses - otherExpenses;
    }

    public static void buildTurbine() {
        turbineExpenses += 8338000; // cena jednej turbiny
        Turbine turbine = new Turbine(); // stworzenie turbiny ( automatycznie wlaczona
        turbines.add(turbine);
    }

    public static void main(String [] argv) throws Exception { // glowna klasa ktora bedzie przeliczac sume kostow i zarobkow

        System.out.println("====================================");
        System.out.println("         START SYMULACJI");
        System.out.println("====================================");

        Main.startSimulation(200,24);
        System.out.printf("Wydatki Turbiny: %.2f %n", turbineExpenses);
        System.out.printf("Wydatki Inne: %.2f %n", otherExpenses);
        System.out.printf("Zarobione: %.2f %n", earnings);
        System.out.printf("Soldo: %.2f %n", Main.total);

        System.out.println("====================================");

        System.out.printf("    PROCENT ZWROCONY: %.2f", 100 * (earnings / (turbineExpenses + otherExpenses)));
        System.out.println("%");

        System.out.println("====================================");
        System.out.println("         KONIEC SYMULACJI");
        System.out.println("====================================");
    }
}
