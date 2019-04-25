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

       App ID: nDC9AG4e
       Client ID: dj0yJmk9RXQyMXI0RmNSU2pqJnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PTRl
       Client secret: bc45a4094dd5b093b47b6fa167266c52740e0b1e
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

        final String appId = "nDC9AG4e";
        final String consumerKey = "dj0yJmk9RXQyMXI0RmNSU2pqJnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PTRl";
        final String consumerSecret = "bc45a4094dd5b093b47b6fa167266c52740e0b1e";
        final String url = "https://weather-ydn-yql.media.yahoo.com/forecastrss";

        Double _wind = 0.0;
        Double _pressure = 0.0;
        Double _temperature = 0.0;

        long timestamp = new Date().getTime() / 1000;
        byte[] nonce = new byte[32];
        Random rand = new Random();
        rand.nextBytes(nonce);
        String oauthNonce = new String(nonce).replaceAll("\\W", "");

        List<String> parameters = new ArrayList<>();
        parameters.add("oauth_consumer_key=" + consumerKey);
        parameters.add("oauth_nonce=" + oauthNonce);
        parameters.add("oauth_signature_method=HMAC-SHA1");
        parameters.add("oauth_timestamp=" + timestamp);
        parameters.add("oauth_version=1.0");
        // Make sure value is encoded
        parameters.add("location=" + URLEncoder.encode(city, "UTF-8"));
        parameters.add("format=json");
        parameters.add("u=c");
        Collections.sort(parameters);

        StringBuffer parametersList = new StringBuffer();
        for (int i = 0; i < parameters.size(); i++) {
            parametersList.append(((i > 0) ? "&" : "") + parameters.get(i));
        }

        String signatureString = "GET&" +
                URLEncoder.encode(url, "UTF-8") + "&" +
                URLEncoder.encode(parametersList.toString(), "UTF-8");

        String signature = null;
        try {
            SecretKeySpec signingKey = new SecretKeySpec((consumerSecret + "&").getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHMAC = mac.doFinal(signatureString.getBytes());
            Encoder encoder = Base64.getEncoder();
            signature = encoder.encodeToString(rawHMAC);
        } catch (Exception e) {
            System.err.println("Unable to append signature");
            System.exit(0);
        }

        String authorizationLine = "OAuth " +
                "oauth_consumer_key=\"" + consumerKey + "\", " +
                "oauth_nonce=\"" + oauthNonce + "\", " +
                "oauth_timestamp=\"" + timestamp + "\", " +
                "oauth_signature_method=\"HMAC-SHA1\", " +
                "oauth_signature=\"" + signature + "\", " +
                "oauth_version=\"1.0\"";

        HttpClient client = HttpClient.newHttpClient(); // sunnyvale,ca
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "?location=" + city + "&format=json&u=c"))
                .header("Authorization", authorizationLine)
                .header("X-Yahoo-App-Id", appId)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, BodyHandler.asString());
        String result = response.body();
        String data[] = result.split("\\{");

        for(int i = 0; i < data.length; ++i) {
            if(data[i].contains("wind")) {
                String[] info = data[i+1].split(",");
                String str_w = info[2].split(":")[1];
                String detail_wind = str_w.substring(0, str_w.length() - 1);
                _wind = Double.parseDouble(detail_wind);

                String detail_temperature = info[0].split(":")[1];
                _temperature = Double.parseDouble(detail_temperature);
            }
            else if(data[i].contains("pressure")) {
                String[] pressure_info = data[i].split(",");
                String detail = pressure_info[2].split(":")[1];
                _pressure = Double.parseDouble(detail);
            }
        }
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
       w = Weather.downloadWeather("Gdansk");
       //ArrayList<Weather> weathers = w.parseWeatherFromFile("./res/weatherGdansk.csv");
       System.out.println(w);

   }
}