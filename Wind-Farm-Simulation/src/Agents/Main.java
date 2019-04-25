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

    public static void startSimulation(int years, int numberOfTurbines, String filePath) throws Exception {
        turbines = new ArrayList<>();

        for(int i = 0; i < numberOfTurbines; ++i) {
            Main.buildTurbine();
        }

        //weather.downloadWeather();
        Double windSum = 0.0;
        int count = 0;
        ArrayList<Weather> weathers = Weather.parseWeatherFromFile(filePath);
        Weather.setWind("./res/windLinowo.csv", weathers);
        for(Weather weather : weathers) { // dla kazdego zapisu z pogody
            //weather.setWind(8.5);
            windSum += weather.getWind();
            count ++;
            for(Turbine turbine : turbines) { // osobno dla kazdej turbiny
                earnings += turbine.calculateEarnings(weather);
                //otherExpenses += 200/24;
            }
        }
        System.out.println("Średnia wiatru -> " + (windSum/count));
        earnings = earnings * years;
        //otherExpenses = 1200000.0 * years; // z faktury 2546305.0
        otherExpenses = turbines.size() * turbineExpenses * 0.015 * years; // 1.5% na rok
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

        Main.startSimulation(1,1, "./res/weatherKielce.csv");
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
