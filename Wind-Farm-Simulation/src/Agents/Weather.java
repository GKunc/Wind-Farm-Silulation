package Agents;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import jdk.incubator.http.HttpResponse.BodyHandler;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.*;
import java.util.Base64.Encoder;

public class Weather {

    /*
       todo

       Pobranie pogody z zadanego okresu
       Pobranie wiatrów

       Pobiera dane z miasta podanego jako parametry zapytania. Jednak tylko z jednego dnia i prognoze na tydzien
    */

    private Double wind;
    private Double preassure;
    private Double temperature;
    private Double density;


    @Override
    public String toString() {
        return "Wind: " + wind + "\n" +
                "Preasure: " + preassure + "\n" +
                "Temperature: " + temperature + "\n" +
                "Density: " + density + "\n";
    }

    public Weather() {
        wind = 0.0;
        preassure = 0.0;
        temperature = 0.0;
        density = calculateDensity(preassure, temperature);
    }

    public Weather(Double _wind, Double _preassure, Double _temperature) {
        wind = _wind;
        preassure = _preassure;
        temperature = _temperature;
        density = calculateDensity(preassure, temperature);
    }

    public Weather(String _wind, String _preassure, String _temperature) {
        wind = (_wind == "") ? 0.0 : Double.parseDouble(_wind);
        preassure = (_preassure == "") ? 0.0 : Double.parseDouble(_preassure);
        temperature = (_temperature == "") ? 0.0 : Double.parseDouble(_temperature);
        density = calculateDensity(preassure, temperature);
    }

    public Double getWind()        { return wind; }
    public void setWind(Double _wind) { wind = _wind; }

    public Double getPreassure()   { return preassure; }
    public void setPresure(Double _preassure) { preassure = _preassure; }

    public Double getTemperature() { return temperature; }
    public void setTemperature(Double _temperature) { temperature = _temperature; }

    public Double getDensity() { return density; }

    public static Double calculateDensity(double preassure, double temperature) {
        return preassure * 100 / (287.05 * (Phisics.celciusToKelvin(temperature)));
    }

    public static Weather downloadWeather(String city) throws Exception { // pogoda z jednegodnia

        final String URL = "http://api.worldweatheronline.com/premium/v1/past-weather.ashx";
        final String key = "535406b1d5e648e2b28183352192604";

        Double _wind = 0.0;
        Double _pressure = 0.0;
        Double _temperature = 0.0;

        HttpClient client = HttpClient.newHttpClient(); // sunnyvale,ca
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + "?key=" + key
                        + "&q=" + city
                        + "&format=json"
                        + "&date=2018-01-01"
                        + "&enddate=2019-01-10"))
                .build();

        HttpResponse<String> response = client.send(request, BodyHandler.asString());
        String result = response.body();
        String data[] = result.split("\\{");

        /*
        todo
        sparsowanie wynikow
        wybranie wiatru, cisnienia i temperatury
        wstawienie do listy pogody
        zmienienie zwracanego typu
         */
        for(String s : data) System.out.println(s);
        //System.out.println("DANE -> " + _wind + _pressure + _temperature);
        return new Weather(_wind, _pressure, _temperature);
    }

    public static ArrayList<Weather> parseWeatherFromFile(String filePath) throws IOException { // Arraylist dla calego pliku
        String data[] = null;
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        ArrayList<Weather> weather = new ArrayList<>();

        try {
            String line;
            while ((line = br.readLine()) != null) {
                data = line.split(";");

                String _wind = data[7].substring(1, data[7].length() - 1);
                String _preassure = data[2].substring(1, data[2].length() - 1);
                String _temperature = data[1].substring(1, data[1].length() - 1);

                weather.add(new Weather(_wind, _preassure, _temperature));
            }

        } catch (Exception e) { // wywala na koncu pliku ( nie wiem czemu )

        } finally {
            br.close();
            return weather;
        }
    }

    public static ArrayList<Weather> setWind(String filePath, ArrayList<Weather> weathers) throws IOException {
        String line;
        String data[] = null;
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        ArrayList<String> wind = new ArrayList<>();

        try {
            while ((line = br.readLine()) != null) {
                data = line.split(";");
                String _wind = data[1].replace(",","."); // dane z pierwszej turbiny
                wind.add(_wind);
            }

        } catch (Exception e) { // wywala na koncu pliku ( nie wiem czemu )

        } finally {
            br.close();
            for(int i = 0; i < weathers.size(); ++i) { // ustawienie wiatru dla danych z Linowa
                Double _wind = Double.parseDouble(wind.get(i*6)); // bo w pliku sa dane co 10m
                weathers.get(i).setWind(_wind);
            }
            return weathers;
        }
    }

    public Weather weatherAtHeight(double turbinHeight) { // modyfikacja pogody dla dnia na odpowieniej wysokosci
        Double wind = Phisics.windAtHeight(turbinHeight, this.getWind());
        Double preassure = Phisics.preasureAtHeight(turbinHeight, this.getPreassure(), this.getTemperature());
        Double temperature = Phisics.temperatureAtHeight(turbinHeight, this.getTemperature());

        return new Weather(wind, preassure, temperature);
    }

   public static void main(String args[]) throws Exception {
       Weather w = new Weather();
       w = Weather.downloadWeather("Gdańsk");
       //ArrayList<Weather> weathers = w.parseWeatherFromFile("./res/weatherGdansk.csv");
       //System.out.println(w);

   }
}
