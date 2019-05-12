package Agents;

import java.io.IOException;
import java.util.ArrayList;

public class Maintanance {

    /*
        todo

        Wszystkie akcje zajmujące się naprawą wiatraków.
        Wyłaczenie wiatraka na czas konserwacje
        Przyblizone oszty naprawy
     */

    public static void preventiveMaintanance(Turbine turbine, double daysPassed) { // polroczny lub coroczny przeglad
        if(daysPassed % 365 == 0 || turbine.getAnnualMaintanance() != 0) {
            Maintanance.annualMaintanance(turbine);
        }
        else if((daysPassed % 180 == 0 && daysPassed % 360 != 0) || turbine.getHalfYearMaintanance() != 0) {
            Maintanance.halfYearMaintanance(turbine);
        }
    }

    public static void annualMaintanance(Turbine turbine) {
        if(turbine.getAnnualMaintanance() > 23) {
            turbine.setAnnualMaintanance(0);
            turbine.turnOn();
        } else {
            turbine.setAnnualMaintanance(turbine.getAnnualMaintanance() + 1);
            turbine.turnOff();
        }
    }

    public static void halfYearMaintanance(Turbine turbine) {
        if(turbine.getHalfYearMaintanance() > 7) {
            turbine.setHalfYearMaintanance(0);
            turbine.turnOn();
        } else {
            turbine.setHalfYearMaintanance(turbine.getHalfYearMaintanance() + 1);
            turbine.turnOff();
        }
    }

    public void alertMaintanance() { // naprawa jakiejs awarii

    }

    public void choseRepairTime(int time) { // wybranie czasu konserwacji

    }

    public static void main(String [] argv) throws IOException {
        /* float earnings = 0;

        ArrayList<Turbine> turbines = new ArrayList<>();

        for(int i = 0; i < 1; i ++) {
            Turbine turbine = new Turbine(); // stworzenie turbiny ( automatycznie wlaczona)
            turbines.add(turbine);
        }

        int count = 0;
        int year = 0;
        int hoursPassed = 1;
        String filePath = "./res/weatherKielce.csv";
        ArrayList<Weather> weathers = Weather.parseWeatherFromFile(filePath);
        while(year < 5) {
            for (Weather weather : weathers) {
                count++;
                System.out.println("DZIEN!!! --->>> " + count);
                hoursPassed++;
                for (Turbine turbine : turbines) {
                    Maintanance.preventiveMaintanance(turbine, (double) hoursPassed / 24);
                    earnings += turbine.calculateEarnings(weather);
                    if (turbine.getAnnualMaintanance() != 0)
                        System.out.println("NAPRAWA ROCZNA!!!!");
                    else if (turbine.getHalfYearMaintanance() != 0)
                        System.out.println("NAPRAWA POLLLLLL ROCZNA!!!!");
                    else
                        System.out.println(earnings);
                }
            }
            year++;
        }*/
    }

}
