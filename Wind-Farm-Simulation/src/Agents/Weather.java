package Agents;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import jdk.incubator.http.HttpResponse.BodyHandler;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
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

    public Weather() {
        wind = 0.0;
        preassure = 0.0;
        temperature = 0.0;
    }
    public Weather(Double _wind, Double _preassure, Double _temperature) {
        wind = _wind;
        preassure = _preassure;
        temperature = _temperature;
    }
    public Weather(String _wind, String _preassure, String _temperature) {
        wind = (_wind == "") ? 0.0 : Double.parseDouble(_wind);
        preassure = (_preassure == "") ? 0.0 : Double.parseDouble(_preassure);
        temperature = (_temperature == "") ? 0.0 : Double.parseDouble(_temperature);
    }

    public Double getWind()        { return wind; }
    public Double getPreassure()   { return preassure; }
    public Double getTemperature() { return temperature; }

    public Weather downloadWeather() throws Exception {

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
        parameters.add("location=" + URLEncoder.encode("sunnyvale,ca", "UTF-8"));
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

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "?location=sunnyvale,ca&format=json&u=c"))
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
    public ArrayList<Weather> parseWeatherFromFile(String filePath) throws IOException { // Arraylist dla calego roku
        String line;
        String data[] = null;
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        ArrayList<Weather> oneYearWeather = new ArrayList<>();

        try {
            while ((line = br.readLine()) != null) {
                data = line.split(";");

                String _wind = data[7].substring(1, data[7].length() - 1);
                String _preassure = data[1].substring(1, data[1].length() - 1);
                String _temperature = data[2].substring(1, data[2].length() - 1);

                Weather oneHourWeather = new Weather(_wind, _preassure, _temperature);
                oneYearWeather.add(oneHourWeather);
            }

        } catch (Exception e) { // wywala na koncu pliku ( nie wiem czemu )

        } finally {
            return oneYearWeather;
        }
    }

   public static void main(String args[]) throws Exception {
       Weather w = new Weather();
       w.downloadWeather();
        /*ArrayList<Weather> weathers = w.parseWeatherFromFile("./res/weatherGdansk.csv");
        for(Weather we : weathers) {
            System.out.println("DANE");
            System.out.println("wind -> " + we.wind);
            System.out.println("preasure -> " + we.preassure);
            System.out.println("temperature -> " + we.temperature);
        }
    }*/
   }
}