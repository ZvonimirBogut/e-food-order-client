package efoodorder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DatabaseConnection {

    public HttpURLConnection createNewConnection(String port, String model, String requestType, String position) throws IOException { // metoda koja izvrsava spajanje na bazu prilikom podizanja programa (koristim je za dohvacanje OrderItem-a)
        URL url = new URL("http://localhost:" + port + "/" + model + "/" + requestType + "/" + position);
        return (HttpURLConnection) url.openConnection();
    }
}
