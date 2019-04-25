package Agents;

public class OneTurbineSimulation {
    Turbine turbine;
    Weather weather;
    /*
        todo

        Mozliwosc symulacji pracy jednego wiatraka
        Symulacja z mozliwoscia ogladania poszczegolnych szesci
        Mozliwosc symulacji pracy real - time. Zmiana wiatru za pomoca suwaka
        Wlaczenie / wylaczenie przy zlym wietrze
        Mozliwosc wlaczenia alarmu
        Mozliwosc konserwacji
        Wydajnosc
        Koszty
        Szybkosc obracania smigla
        Produkowana energia
     */

    public OneTurbineSimulation(String city) throws Exception {
        turbine = new Turbine();
        weather = weather.downloadWeather(city); // pobrana z Yahho z dzisiejszego dnia

    }

    public void setWind(Double wind) {
        weather.setWind(wind);
    }

    public void setTemperature(Double temperature) {
        weather.setTemperature(temperature);
    }

    public void setPreassure(Double preassure) {
        weather.setPresure(preassure);
    }

    public void showParts() { // moze dodac nowa klase z czesciami

    }

    public void setAlarm(boolean _alert) {
        turbine.setAlert(_alert);
    }

    public static void main(String [] argv) throws Exception {
        OneTurbineSimulation sim = new OneTurbineSimulation("Gdańsk");
    }
}
