package efoodorder.service;

import efoodorder.DatabaseConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class GetOnConstruct {

    private DatabaseConnection databaseConnection;

    public StringBuilder getOrderItems() throws IOException { // kreiranje konekcije na bazu i dohvacanje liste OrderItem-a.
        // Dohvaceni podaci su spremljeni u StringBuilder i salju se dalje na pretvaranje u pravi objekt tipa OrderItem u klasu App (konstruktor).
        databaseConnection = new DatabaseConnection();
        HttpURLConnection httpURLConnection = databaseConnection.createNewConnection("8080", "orderitem","get", "all");
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        int responseCode = httpURLConnection.getResponseCode();
        System.out.println("Response code: " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream())
        );
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();
        return response;
    }



}
