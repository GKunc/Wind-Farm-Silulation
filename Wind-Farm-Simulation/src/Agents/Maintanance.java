package Agents;

public class Maintanance {

    /*
        todo

        Wszystkie akcje zajmujące się naprawą wiatraków.
        Wyłaczenie wiatraka na czas konserwacje
        Przyblizone oszty naprawy
     */

    public void preventiveMaintanance(Turbine turbine, int daysPassed) { // polroczny lub coroczny przeglad
        if(daysPassed % 365 == 0)
            Maintanance.annualMaintanance();
        else if(daysPassed % 180 == 0 && daysPassed % 360 != 0)
            Maintanance.halfYearMaintanance();
    }

    public void alertMaintanance() { // naprawa jakiejs awarii

    }

    public void choseRepairTime(int time) { // wybranie czasu konserwacji

    }

    public static void main(String [] argv) {

    }

}
