package home.Agents;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    private static ArrayList<Turbine> turbines;

    private static Double earnings = 0.0;
    private static Double turbineExpenses = 0.0;
    private static Double otherExpenses = 0.0;
    private static Double failuresExpenses = 0.0;
    private static ArrayList<Double> periodProfits = new ArrayList<Double>(); //miesieczny(z pliku) lub dzienny(z api) zysk
    //public static ArrayList<Double> monthlyExpenses = new ArrayList<Double>(); // to do użycia jak będą śmigać awarie
    private static ArrayList<String> namesForXAxis;
    public static String startDate;
    public static String endDate;
    private static ArrayList<FailuresInfo> listOfFailures;

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
        failuresExpenses = 0.0;
        periodProfits = new ArrayList<Double>();
        namesForXAxis = new ArrayList<String>();
        listOfFailures = new ArrayList<FailuresInfo>();


        for (int i = 0; i < numberOfTurbines; ++i) {
            Main.buildTurbine();
        }

        //weather.downloadWeather();
        Double windSum = 0.0;
        int count = 1;
        double oneMonthProfit = 0.0;
        ArrayList<Weather> weathers = Weather.parseWeatherFromFile(filePath);
//        Weather.setWind("./res/windLinowo.csv", weathers);
//        Weather.setWind("C:\\Users\\Zuzanna\\Desktop\\AGH\\Infa\\Semestr 4\\Wind-Farm-Simulation\\Wind-Farm-Simulation\\res\\windLinowo.csv", weathers);

        String prev_date = weathers.get(0).getDate();
        startDate = prev_date;
        endDate = weathers.get(weathers.size() - 1).getDate();
        for (Weather weather : weathers) { // dla kazdego zapisu z pogody
            //weather.setWind(8.5);
            windSum += weather.getWind();
            count++;
            if (!((prev_date.split("-")[1]).equals(weather.getDate().split("-")[1]))) { //przyjmuję tutaj, że każdy miesiąc ma 30 dni( dokładną liczbę dni można zrobić w np. Weather.parseWeatherFromFile())
                periodProfits.add(oneMonthProfit);
                namesForXAxis.add(prev_date.split("-")[1]);
                oneMonthProfit = 0;
            }
            for (Turbine turbine : turbines) { // osobno dla kazdej turbiny
                Maintanance.preventiveMaintanance(turbine, (double) count / 24);
                earnings += turbine.calculateEarnings(weather);
                oneMonthProfit += turbine.calculateEarnings(weather);
                //otherExpenses += 200/24;
            }
            prev_date = weather.getDate();
        }
        periodProfits.add(oneMonthProfit); //dodanie ostatniej sumy miesięcznych dochodów
        namesForXAxis.add(prev_date.split("-")[1]); //dodatnie znacznika ostatniego miesiąca
        System.out.println("Średnia wiatru -> " + (windSum / count));
        earnings = earnings * years;

        /*
        W tej częsci potrzebuję od długości czasu z jakiego mamy dane do symulacji.
        Przyjmuję, że miesiąc trwa 30 dni, a każdego dni mamy 24 pomiary pogody.
        */

        for (int i = 0; i <= (weathers.size() / (30 * 24)); i++) {
            for(int j=0; j<turbines.size();j++){
                failuresExpenses += failuresGenerator(turbines.get(j),j+1, earnings / count);
                listOfFailures.addAll((turbines.get(j)).failuresList);
            }
        }
        //otherExpenses = 1200000.0 * years; // z faktury 2546305.0
        otherExpenses = turbines.size() * turbineExpenses * 0.015 * years; // 1.5% na rok
        total = earnings - turbineExpenses - otherExpenses - failuresExpenses;


    }


    public static void startSimulation(double years, int numberOfTurbines, String location, String beginDate, String endingDate) throws Exception {
        turbines = new ArrayList<>();
        earnings = 0.0;
        turbineExpenses = 0.0;
        otherExpenses = 0.0;
        failuresExpenses = 0.0;
        periodProfits = new ArrayList<Double>();
        namesForXAxis = new ArrayList<String>();
        startDate = beginDate;
        endDate = endingDate;
        listOfFailures = new ArrayList<FailuresInfo>();


        for (int i = 0; i < numberOfTurbines; ++i) {
            Main.buildTurbine();
        }

        Double windSum = 0.0;
        int count = 1;
        double oneDayProfit = 0.0;

        ArrayList<Weather> weathers = Weather.downloadWeather(location, beginDate, endDate);

        for (Weather weather : weathers) { // dla kazdego zapisu z pogody
            windSum += weather.getWind();
            count++;
            if ((count - 1) % (24) == 0) { //pomiary są co  godzinę
                periodProfits.add(oneDayProfit);
                oneDayProfit = 0;
                namesForXAxis.add(weather.getDate().split("-")[2]);
            }
            for (Turbine turbine : turbines) { // osobno dla kazdej turbiny
                Maintanance.preventiveMaintanance(turbine, (double) count / 24);
                earnings += turbine.calculateEarnings(weather);
                oneDayProfit += turbine.calculateEarnings(weather);
            }
        }
        System.out.println("Średnia wiatru -> " + (windSum / count));
        earnings = earnings * years;

        /*
        W tej częsci potrzebuję od długości czasu z jakiego mamy dane do symulacji.
        Przyjmuję, że miesiąc trwa 30 dni, a każdego dni mamy 24 pomiary pogody.
        */

        for (int i = 0; i <= (weathers.size() / (30 * 24)); i++) {
            for (int j=0; j<turbines.size(); j++) {
                failuresExpenses += failuresGenerator(turbines.get(j),j+1, earnings / count);
                listOfFailures.addAll((turbines.get(j)).failuresList);
            }
        }
        //otherExpenses = 1200000.0 * years; // z faktury 2546305.0
        otherExpenses = turbines.size() * turbineExpenses * 0.015 * years; // 1.5% na rok
        total = earnings - turbineExpenses - otherExpenses - failuresExpenses;
    }

    public static void buildTurbine() {
        turbineExpenses += 8338000; // cena jednej turbiny
        Turbine turbine = new Turbine(); // stworzenie turbiny ( automatycznie wlaczona)
        turbines.add(turbine);
    }


    /*
     * Każda z wymienionych w dokumentacji awarii została uwzględniona
     * (te w których długość nie przekraczała godziny przyjmuję, że trwały najdłuższy przewidywany czas)
     * w pozostałych przypadkach czas będzie generowany losowo z uwzglednieniem minimalnej długości awarii.
     *
     * Losuję liczbę z przedziału 0-1,
     * która następnie jest porównywana z prawdopodobieństwem danej awarii dla jednej turbiny.
     *
     * Mozliwe ulepszenia:
     * - obiekty klasy Turbine będzie mieć zamiast listy String mapę <String, String> z nazwą awarii i jej datą
     * - obliczanie kosztów zwiazanych z awarią nie na podstawie średniej godzinowej stawki, ale dokładnych danych.
     *
     */
    public static double failuresGenerator(Turbine examineTurbine, int turbineNo, double averageHourlyProfit) {
        double failuresCost = 0.0;
        double tmp_probability;
        double hourlyDurationTime=0.0;

        tmp_probability = new Random().nextDouble();
        if (tmp_probability < (19.0 / 24)) {
            hourlyDurationTime = (5.0 / 6);
            examineTurbine.failuresList.add(new FailuresInfo(turbineNo, "Za duże napięcie (sieć)", Math.round(hourlyDurationTime*60) + " min"));
            failuresCost += hourlyDurationTime * averageHourlyProfit;
        }
        tmp_probability = new Random().nextDouble();
        if (tmp_probability < (1.0 / 24)) {
            hourlyDurationTime =(2.0 / 3);
            examineTurbine.failuresList.add(new FailuresInfo(turbineNo, "Awaryjne hamowanie (za duży wiatr)", Math.round(hourlyDurationTime*60) + " min"));
            failuresCost += hourlyDurationTime * averageHourlyProfit;
        }
        tmp_probability = new Random().nextDouble();
        if (tmp_probability < (18.0 / 24)) {
            hourlyDurationTime = (2 + Math.random() * 3.5);
            examineTurbine.failuresList.add(new FailuresInfo(turbineNo, "Pauza kliknięta na klawiaturze", Math.round(hourlyDurationTime*60) + " min"));
            failuresCost += hourlyDurationTime * averageHourlyProfit;
        }
        tmp_probability = new Random().nextDouble();
        if (tmp_probability < (2.0 / 24)) {
            hourlyDurationTime = (2.0 / 3);
            examineTurbine.failuresList.add(new FailuresInfo(turbineNo, "Wysoka temperatura", Math.round(hourlyDurationTime*60) + " min"));
            failuresCost += hourlyDurationTime * averageHourlyProfit;
        }
        tmp_probability = new Random().nextDouble();
        if (tmp_probability < (2.0 / 24)) {
            hourlyDurationTime = (1 + Math.random() * 0.5);
            examineTurbine.failuresList.add(new FailuresInfo(turbineNo, "Awaria konwertera napięcia", Math.round(hourlyDurationTime*60) + " min"));
            failuresCost += hourlyDurationTime * averageHourlyProfit;
        }
        tmp_probability = new Random().nextDouble();
        if (tmp_probability < (9.0 / 24)) {
            hourlyDurationTime = (5.0 / 60);
            examineTurbine.failuresList.add(new FailuresInfo(turbineNo, "Za wysoka moc", Math.round(hourlyDurationTime*60) + " min"));
            failuresCost += hourlyDurationTime * averageHourlyProfit;
        }
        tmp_probability = new Random().nextDouble();
        if (tmp_probability < (1.0 / 24)) {
            hourlyDurationTime =(4 + Math.random());
            examineTurbine.failuresList.add(new FailuresInfo(turbineNo, "Awaria skrzyni biegów", Math.round(hourlyDurationTime*60) + " min"));
            failuresCost += hourlyDurationTime * averageHourlyProfit;
        }
        tmp_probability = new Random().nextDouble();
        if (tmp_probability < (1.0 / 24)) {
            hourlyDurationTime = (3 + Math.random());
            examineTurbine.failuresList.add(new FailuresInfo(turbineNo, "Awaria łopat", Math.round(hourlyDurationTime*60) + " min"));
            failuresCost += hourlyDurationTime * averageHourlyProfit;
        }

        return failuresCost;
    }

    public static ArrayList<FailuresInfo> getListOfFailures() {
        return listOfFailures;
    }

    public static ArrayList<Double> getPeriodProfits() {
        return periodProfits;
    }

    public static ArrayList<String> getNamesForXAxis() {
        return namesForXAxis;
    }

    public static Double getTurbineExpenses() {
        return turbineExpenses;
    }

    public static Double getOtherExpenses() {
        return otherExpenses;
    }

    public static ArrayList<Turbine> getTurbines() {
        return turbines;
    }

    public static String showSimulationResults(String[] args) throws Exception {
        StringBuilder msgToReturn = new StringBuilder();
        if (args[0] == "fromApi") {
            Main.startSimulation(1.00 / 12, new Integer(args[1]), args[2], args[3], args[4]);

        } else if (args[0] == "fromFile") {
          //  Main.startSimulation(1, new Integer(args[1]), "C:\\Users\\Zuzanna\\Desktop\\AGH\\Infa\\Semestr 4\\Wind-Farm-Simulation\\Wind-Farm-Simulation\\res\\weather" + args[2] + ".csv");
           Main.startSimulation(1, new Integer(args[1]), "./res/weather"+args[2]+".csv");
        }
        msgToReturn.append("====================================\n");
        msgToReturn.append("         START SYMULACJI\n");
        msgToReturn.append("====================================\n");

        msgToReturn.append("Wydatki Turbiny: " + turbineExpenses + "\n");
        msgToReturn.append("Wydatki Inne: " + otherExpenses + "\n");
        msgToReturn.append("Straty z awarii: " + failuresExpenses + "\n");
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
        System.out.printf("Straty z awarii: %.2f %n", failuresExpenses );
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
