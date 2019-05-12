package Agents;

public class Maintanance {

    /*
        todo

        Wszystkie akcje zajmujące się naprawą wiatraków.
        Wyłaczenie wiatraka na czas konserwacje
        Przyblizone oszty naprawy
     */

    public void preventiveMaintanance(Turbine turbine, int daysPassed) { // polroczny lub coroczny przeglad
        if(daysPassed % 365 == 0 || turbine.annualMaintanance != 0) {
            Maintanance.annualMaintanance(turbine);
        }
        else if((daysPassed % 180 == 0 && daysPassed % 360 != 0) || turbine.halfYearMaintanance != 0) {
            Maintanance.halfYearMaintanance(turbine);

        }
    }

    public static void annualMaintanance(Turbine turbine) {
        if(turbine.annualMaintanance > 24) {
            turbine.annualMaintanance = 0;
            turbine.turnOn();
        } else {
            turbine.annualMaintanance++;
            turbine.turnOff();
        }
    }

    public static void halfYearMaintanance(Turbine turbine) {
        if(turbine.halfYearMaintanance > 8) {
            turbine.halfYearMaintanance = 0;
            turbine.turnOn();
        } else {
            turbine.annualMaintanance++;
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
