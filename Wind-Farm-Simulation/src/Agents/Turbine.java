package Agents;

public class Turbine {

    private final double sweptArea = 6362; // m^2 dla naszego modelu
    private final double towerHeight= 105;
    private boolean status; // off == false / on == true
    private double efficiency; // 1 - 0 (procentowo)
    private double condition; // 1 - 0 (procentowo)
    private double power;
    private double expenses;
    private int age; // w latach
    private boolean alert;
    /*
        todo

        Wprowadzenie stanow pracy wiatraka
        Stan zuzycia i wydajnosci wiatraka
        Obliczenie mocy wygenerowanej uzywajac wzorow z phisics
     */

    public Turbine () {
        status = true;
        efficiency = 1;
        condition = 1;
        power = 0;
        expenses = 0;
        age = 0;
        alert = false;
    }

    public boolean getStatus() { return status; }

    public void    turnOn()    { status = true; }
    public void    turnOff()   { status = false; }

    public double getEfficiency()                   { return efficiency; }
    public void   setEfficiency(double _efficiency) { efficiency = _efficiency; }

    public double  getCondition()               { return condition; }
    public void setCondition(double _condition) { condition = _condition; }

    public double getPower() { return power; }

    public Double calculatePower(Weather weather) {
        if(Control.checkWind(this, weather)) {
            return (1.2759 * this.efficiency * this.sweptArea * Math.pow(weather.getWind(), 3)) / (2 * 1000);
        }
        return 0.0;
    }

    public Double calculateEarnings(Weather weather) { // cena za MWh 227.4 zł
        return this.calculatePower(weather) * 227.4;
    }

    public Double calculateExpenses() { // 300zł na dzien ( na razie nie wiem ile, tak wpisalem)
        return 300.0;
    }

    public int  getAge() { return age; }
    public void setAge(int _age) { age = _age; }

    public void setAlert(boolean _alert) { alert = _alert; }

    public double getExpenses() { return expenses; }

    public static void main(String [] argv) {

    }


}
