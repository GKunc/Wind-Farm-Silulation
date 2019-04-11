package Agents;

public class Turbine {

    //private double sweptArea; // zalezna od modelu wiatraka
    private boolean status;
    private double efficiency; // 1 - 0 (procentowo)
    private int condition; // 1 - 0 (procentowo)
    private double power;
    private double earnings;
    /*
        todo

        Wprowadzenie stanow pracy wiatraka
        Stan zuzycia i wydajnosci wiatraka
        Obliczenie mocy wygenerowanej uzywajac wzorow z phisics
     */

    public Turbine () {
        status = false;
        efficiency = 1;
        condition = 1;
        power = 0;
        earnings = 0;
    }

    public boolean getStatus() { return status; }
    public void    turnOn()    { status = true; }
    public void    turnOff()   { status = false; }

    public double getEfficiency()                   { return efficiency; }
    public void   setEfficiency(double _efficiency) { efficiency = _efficiency; }

    public int  getCondition()               { return condition; }
    public void setCondition(int _condition) { condition = _condition; }

    public double getPower() { return power; }
    public void calculatePower(Weather weather) {

    }

    public double getEarnings() { return earnings; }




}
