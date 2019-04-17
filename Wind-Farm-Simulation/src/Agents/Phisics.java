package Agents;

public class Phisics {

    /*
        todo

        Przeliczenie pobranych parametrów pogodowych na panujące na odpowiedniej wysokości
        Obliczenie wygenerowanej mocy
        Przeliczenie na ilosc wyprodukowanej energii

     */
    public final static double terrainRoughness = 0.03;
    private final static Double weatherHeight = 10.0; // pogoda zmierzona na wysokosci 10m
    private final static Double mi = 0.0289644;
    private final static Double g = 9.80665;
    private final static Double R = 8.3144598;

    public static Double celciusToKelvin(double temperatureCelcius) {
        return temperatureCelcius + 273;
    }

    public static Double windAtHeight(double turbinHeight, Double windSpeed) { // przeliczenie predkosci wiatru na wysokosci turbiny
        return windSpeed * Math.pow((turbinHeight/weatherHeight), terrainRoughness);
    }

    public static Double preasureAtHeight(double turbinHeight, Double p0, Double temperature) { // przeliczenie cisnienia na wysokosci turbin
        return p0 * Math.exp((-mi*g*turbinHeight+weatherHeight)/(R*temperatureAtHeight(turbinHeight, celciusToKelvin(temperatureAtHeight(turbinHeight, temperature)))));
    }

    public static Double temperatureAtHeight(double turbinHeight, Double temperature) { // przeliczenie temperatury na wysokosci turbiny
        return temperature - ((turbinHeight - 10) / 100) * 0.6; // spadek o 0.6 celcujesza na 100m
    }


    public static void main(String [] argv) {

    }
}
