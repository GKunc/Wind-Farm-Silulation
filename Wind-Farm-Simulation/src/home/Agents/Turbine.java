package home.Agents;

import java.util.ArrayList;

public class Turbine {

    private final double sweptArea = 6362; // m^2 dla naszego modelu
    public static double towerHeight= 105;
    private boolean status; // off == false / on == true
    private double efficiency = 0.45;
    private double condition; // 1 - 0 (procentowo)
    private int age; // w latach
    private boolean alert;
    private int annualMaintanance;
    private int halfYearMaintanance;
    public ArrayList<FailuresInfo> failuresList;

    /*
        todo

        Wprowadzenie stanow pracy wiatraka
        Stan zuzycia i wydajnosci wiatraka
        Obliczenie mocy wygenerowanej uzywajac wzorow z phisics
     */

    public Turbine () {
        status = true;
        condition = 1;
        age = 0;
        alert = false;
        annualMaintanance = 0;
        halfYearMaintanance = 0;
        failuresList = new ArrayList<FailuresInfo>();
    }

    public boolean getStatus() { return status; }

    public void    turnOn()    { status = true; }
    public void    turnOff()   { status = false; }

    public int getAnnualMaintanance() { return annualMaintanance; }
    public void setAnnualMaintanance(int value) { annualMaintanance = value; }

    public int getHalfYearMaintanance() { return halfYearMaintanance; }
    public void setHalfYearMaintanance(int value) { halfYearMaintanance = value; }

    public double  getCondition()               { return condition; }
    public void setCondition(double _condition) { condition = _condition; }

    public Double calculatePower(Weather weather) {
        if(Control.checkWind(this, weather)) {
            return (this.efficiency * this.sweptArea * Math.pow(weather.getWind(), 3) * weather.getDensity()) / (2 * 1000);
        }
        return 0.0;
    }

    public Double calculateEarnings(Weather weather) { // cena za MWh 227.4 zł
        if(this.getStatus() == true) return this.calculatePower(weather) * 0.405; // gwarantowana
        else return 0.0;
    }

    /*public Double calculateExpenses() { // 300zł na dzien ( na razie nie wiem ile, tak wpisalem)
        return 200.0;
    }*/

    public int  getAge() { return age; }
    public void setAge(int _age) { age = _age; }

    public void setAlert(boolean _alert) { alert = _alert; }

    public static void main(String [] argv) {
        Turbine tur = new Turbine();
        Weather w = new Weather(7.7,1000.0,10.0, "0000-00-00");
        System.out.println("Gestosc -> " + w.getDensity());
        System.out.println("ENERGIA -> " + tur.calculatePower(w)*24*365);
    }

}
