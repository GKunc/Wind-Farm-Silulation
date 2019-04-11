package Agents;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import jdk.incubator.http.HttpResponse.BodyHandler;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.URLEncoder;

import java.nio.file.Watchable;
import java.util.*;
import java.util.Base64.Encoder;

/**
 *
 * <pre>
 * % java --version
 * % java 11.0.1 2018-10-16 LTS
 *
 * % javac WeatherYdnJava.java && java -ea WeatherYdnJava
 * </pre>
 *
 */
public class Weather {

    /*
       todo

       Pobranie pogody z zadanego okresu
       Pobranie wiatrów
       Wysyłanie parametrów do agenta zajmującego się kontrolą

       Pobiera dane z miasta podanego jako parametry zapytania. Jednak tylko z jednego dnia i prognoze na tydzien

       App ID: nDC9AG4e
       Client ID: dj0yJmk9RXQyMXI0RmNSU2pqJnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PTRl
       Client secret: bc45a4094dd5b093b47b6fa167266c52740e0b1e
    */

    private double wind;
    private double preassure;
    private double temperature;
    private double airDensity;


    public void downloadWeather() throws Exception {

        final String appId = "nDC9AG4e";
        final String consumerKey = "dj0yJmk9RXQyMXI0RmNSU2pqJnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PTRl";
        final String consumerSecret = "bc45a4094dd5b093b47b6fa167266c52740e0b1e";
        final String url = "https://weather-ydn-yql.media.yahoo.com/forecastrss";

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
        System.out.println(response.body());
    }

    public static void main(String args[]) throws Exception {
        Weather w = new Weather();
        w.downloadWeather();
    }
}