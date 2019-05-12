package Agents;

public class Maintanance {

    /*
        todo

        Wszystkie akcje zajmujące się naprawą wiatraków.
        Wyłaczenie wiatraka na czas konserwacje
        Przyblizone oszty naprawy
     */

    public void preventiveMaintanance(Turbine turbine, int daysPassed) { // polroczny lub coroczny przeglad
        if(daysPassed % 365 == 0 || turbine.getAnnualMaintanance() != 0) {
            Maintanance.annualMaintanance(turbine);
        }
        else if((daysPassed % 180 == 0 && daysPassed % 360 != 0) || turbine.getHalfYearMaintanance() != 0) {
            Maintanance.halfYearMaintanance(turbine);
        }
    }

    public static void annualMaintanance(Turbine turbine) {
        if(turbine.getAnnualMaintanance() > 24) {
            turbine.setAnnualMaintanance(0);
            turbine.turnOn();
        } else {
            turbine.setAnnualMaintanance(turbine.getAnnualMaintanance() + 1);
            turbine.turnOff();
        }
    }

    public static void halfYearMaintanance(Turbine turbine) {
        if(turbine.getHalfYearMaintanance() > 8) {
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

    public static void main(String [] argv) {

    }

}
