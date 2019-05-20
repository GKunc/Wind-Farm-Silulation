package home.Agents;

import java.io.IOException;
import java.util.ArrayList;

public class OneTurbineSimulation {
    Turbine turbine;
    Weather weather;    /*
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
        //weather = Weather.downloadWeather(city).get(0); // pobrana z API z pierwszego dnia
        // tutaj jeszcze nie agrnełam gdzie to wykorzystujemy ale trzeab dodać datę pocztąku i końca symulacji

    }

    public void setWind(String filePath, ArrayList<Weather> weather) throws IOException {
        Weather.setWind(filePath, weather);
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
        OneTurbineSimulation sim = new OneTurbineSimulation("Warszawa");
        System.out.println(sim.weather);
    }
}
