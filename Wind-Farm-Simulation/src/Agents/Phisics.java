package Agents;

public class Phisics {

    /*
        todo

        Przeliczenie pobranych parametrów pogodowych na panujące na odpowiedniej wysokości
        Obliczenie wygenerowanej mocy
        Przeliczenie na ilosc wyprodukowanej energii

     */

    public Double windAtHeight(double height, Double wind) {
        return 0.0;
    }

    public Double preasureAtHeight(double height, Double wind) {
        return 0.0;
    }

    public Double temperatureAtHeight(double height, Double temperature) {
        return 0.0;
    }

    public Weather weatherAtHeight(double height, Weather weather) {
        Double wind = windAtHeight(height, weather.getWind());
        Double preassure = preasureAtHeight(height, weather.getPreassure());
        Double temperature = temperatureAtHeight(height, weather.getTemperature());

        return new Weather(wind, preassure, temperature);
    }

    public static void main(String[] args) {

    }
}
