package efoodorder.service;

import com.google.gson.Gson;
import efoodorder.model.Order;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

public class SendData {

    public HttpResponse saveOrderToDb(Order order) throws IOException { // drugi nacin uspostave konekcije na bazu. U ovom slucaju se koristi da bi se spremila narudzba.
        Gson gson = new Gson();
        String json = gson.toJson(order); // pretvaranje objekta u JSON da bi se mogli podaci poslati na backend

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/order/save"); // postavljanje url-a za gadanje API-ja
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        StringEntity stringEntity = new StringEntity(json);
        httpPost.setEntity(stringEntity);
        return httpclient.execute(httpPost); // izvrsavanje POST requesta i slanje podataka
    }

}
