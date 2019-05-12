package Agents;

import java.util.ArrayList;

public class Main {
    private static ArrayList<Turbine> turbines;

    private static Double earnings = 0.0;
    private static Double turbineExpenses = 0.0;
    private static Double otherExpenses = 0.0;
    private static ArrayList<Double> monthlyProfits = new ArrayList<Double>(); //miesieczny(z pliku) lub dzienny(z api) zysk
    //public static ArrayList<Double> monthlyExpenses = new ArrayList<Double>(); // to do użycia jak będą śmigać awarie


    private static double total = 0;

    // stworzyc zadana ilosc turbin
    // inne wydatki 36% ceny turbin
    // zaladowac pogode
    // zaczac symulacje

    /*
    - tutaj modyfikacje zliczanie miescięcznych zysków z produkcji pradu dla danych w plikach .csv
    - wywołanie funkcji pobierającej pogdodę z api dla danego miasta (w okreresie i jednego roku)
     i symulacja po jakim czasie farma się zwróci + jeśli się uda to tez to co robi dla danych z pliku
     */

    public static void startSimulation(int years, int numberOfTurbines, String filePath) throws Exception {

        turbines = new ArrayList<>();
        earnings = 0.0;
        turbineExpenses = 0.0;
        otherExpenses = 0.0;
        monthlyProfits = new ArrayList<Double>();

        for (int i = 0; i < numberOfTurbines; ++i) {
            Main.buildTurbine();
        }

        //weather.downloadWeather();
        Double windSum = 0.0;
        int count = 1;
        double oneMonthProfit = 0.0;
        ArrayList<Weather> weathers = Weather.parseWeatherFromFile(filePath);
        //Weather.setWind("./res/windLinowo.csv", weathers);
        /* C:\Users\Zuzanna\Desktop\AGH\Infa\Semestr 4\Wind-Farm-Simulation\Wind-Farm-Simulation\res\windLinowo.csv */
        Weather.setWind("./res/windLinowo.csv", weathers);
        for (Weather weather : weathers) { // dla kazdego zapisu z pogody
            //weather.setWind(8.5);
            windSum += weather.getWind();
            count++;
            if (count % (30 * 24) == 0) { //przyjmuję tutaj, że każdy miesiąc ma 30 dni( dokładną liczbę dni można zrobić w np. Weather.parseWeatherFromFile())
                monthlyProfits.add(oneMonthProfit);
                oneMonthProfit = 0;
            }
            for (Turbine turbine : turbines) { // osobno dla kazdej turbiny
                Maintanance.preventiveMaintanance(turbine,(double) count/24);
                earnings += turbine.calculateEarnings(weather);
                oneMonthProfit += turbine.calculateEarnings(weather);
                //otherExpenses += 200/24;
            }
        }
        System.out.println("Średnia wiatru -> " + (windSum / count));
        earnings = earnings * years;
        //otherExpenses = 1200000.0 * years; // z faktury 2546305.0
        otherExpenses = turbines.size() * turbineExpenses * 0.015 * years; // 1.5% na rok
        total = earnings - turbineExpenses - otherExpenses;
    }


    public static void startSimulation(double years, int numberOfTurbines, String location) throws Exception {
        turbines = new ArrayList<>();
        earnings = 0.0;
        turbineExpenses = 0.0;
        otherExpenses = 0.0;
        monthlyProfits = new ArrayList<Double>();

        for (int i = 0; i < numberOfTurbines; ++i) {
            Main.buildTurbine();
        }

        Double windSum = 0.0;
        int count = 1;
        double oneDayProfit = 0.0;

        ArrayList<Weather> weathers = Weather.downloadWeather(location);
        for (Weather weather : weathers) { // dla kazdego zapisu z pogody
            windSum += weather.getWind();
            count++;
            if ((count-1) % (8) == 0) { //pomiary są co 3 godziny więc 8 pomiarów na dzień
                monthlyProfits.add(oneDayProfit);
                oneDayProfit = 0;
            }
            for (Turbine turbine : turbines) { // osobno dla kazdej turbiny
                Maintanance.preventiveMaintanance(turbine, (double)count/24);
                earnings += turbine.calculateEarnings(weather);
                oneDayProfit += turbine.calculateEarnings(weather);
            }
        }
        System.out.println("Średnia wiatru -> " + (windSum / count));
        earnings = earnings * years;
        //otherExpenses = 1200000.0 * years; // z faktury 2546305.0
        otherExpenses = turbines.size() * turbineExpenses * 0.015 * years; // 1.5% na rok
        total = earnings - turbineExpenses - otherExpenses;
    }

    public static void buildTurbine() {
        turbineExpenses += 8338000; // cena jednej turbiny
        Turbine turbine = new Turbine(); // stworzenie turbiny ( automatycznie wlaczona)
        turbines.add(turbine);
    }

    public static ArrayList<Double> getMonthlyProfits() {
        return monthlyProfits;
    }

    public static Double getTurbineExpenses() {
        return turbineExpenses;
    }

    public static Double getOtherExpenses() {
        return otherExpenses;
    }

    public static String showSimulationResults(String[] args) throws Exception {
        StringBuilder msgToReturn = new StringBuilder();
        if (args[1] == "fromApi") {
            Main.startSimulation(1.00 / 12, 1, args[0]);
//
        } else if (args[1] == "fromFile") {
            /* C:\Users\Zuzanna\Desktop\AGH\Infa\Semestr 4\Wind-Farm-Simulation\Wind-Farm-Simulation\res\weatherKielce.csv */
            Main.startSimulation(1, 1, "./res/weatherKielce.csv");
        }
        msgToReturn.append("====================================\n");
        msgToReturn.append("         START SYMULACJI\n");
        msgToReturn.append("====================================\n");

        msgToReturn.append("Wydatki Turbiny: " + turbineExpenses + "\n");
        msgToReturn.append("Wydatki Inne: " + otherExpenses + "\n");
        msgToReturn.append("Zarobione: " + earnings + "\n");
        msgToReturn.append("Saldo: " + Main.total + "\n");

        msgToReturn.append("====================================\n");

        msgToReturn.append("    PROCENT ZWROCONY: " + 100 * (earnings / (turbineExpenses + otherExpenses)) + "%\n");

        msgToReturn.append("====================================\n");
        msgToReturn.append("         KONIEC SYMULACJI\n");
        msgToReturn.append("====================================\n");


        return msgToReturn.toString();
    }

    /*
    public static void main(String[] argv) throws Exception { // glowna klasa ktora bedzie przeliczac sume kosztow i zarobkow

        System.out.println("====================================");
        System.out.println("         START SYMULACJI");
        System.out.println("====================================");

        Main.startSimulation(1, 1, "./res/weatherKielce.csv");
        System.out.printf("Wydatki Turbiny: %.2f %n", turbineExpenses);
        System.out.printf("Wydatki Inne: %.2f %n", otherExpenses);
        System.out.printf("Zarobione: %.2f %n", earnings);
        System.out.printf("Saldo: %.2f %n", Main.total);

        System.out.println("====================================");

        System.out.printf("    PROCENT ZWROCONY: %.2f", 100 * (earnings / (turbineExpenses + otherExpenses)));
        System.out.println("%");

        System.out.println("====================================");
        System.out.println("         KONIEC SYMULACJI");
        System.out.println("====================================");

        //Visualization.main(); // odpalenie wizualizacji dla jednego roku (Kielce - 2018)

        System.out.println("====================================");
        System.out.println("         START SYMULACJI");
        System.out.println("====================================");

        Main.startSimulation(1.00 / 12, 1, "Zakopane");
        System.out.printf("Wydatki Turbiny: %.2f %n", turbineExpenses);
        System.out.printf("Wydatki Inne: %.2f %n", otherExpenses);
        System.out.printf("Zarobione: %.2f %n", earnings);
        System.out.printf("Saldo: %.2f %n", Main.total);

        System.out.println("====================================");

        System.out.printf("    PROCENT ZWROCONY: %.2f", 100 * (earnings / (turbineExpenses + otherExpenses)));
        System.out.println("%");

        System.out.println("====================================");
        System.out.println("         KONIEC SYMULACJI");
        System.out.println("====================================");

        //Visualization.main(); // odpalenie wizualizacji dla jednego roku (Kielce - 2018)
    }
     */
}
