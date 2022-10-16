package itmo.soa;

import com.google.gson.Gson;
import itmo.soa.dto.DragonDto;
import itmo.soa.entity.Coordinates;
import itmo.soa.entity.DragonCave;
import itmo.soa.enums.Color;
import itmo.soa.enums.DragonCharacter;
import itmo.soa.enums.DragonType;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class test {

    public static void main(String[] args) throws IOException {
//
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            final HttpGet httpGet = new HttpGet("http://localhost:8080/dragons");
//
//            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
//                int statusCode = response.getStatusLine().getStatusCode();
//                System.out.println(statusCode);
//                String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
//                AllDragonsDto obj = new Gson().fromJson(responseBody, AllDragonsDto.class);
//                System.out.println("Response body: " + responseBody);
//            }
//        }


        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String postUrl = "http://localhost:8080/dragons";// put in your url
            HttpPost post = new HttpPost(postUrl);
            StringEntity postingString = new StringEntity(new Gson().toJson(new DragonDto("snoopie", new Coordinates(1, 1), "13.10.2022", -2L, Color.BLACK, DragonType.AIR, DragonCharacter.CHAOTIC, new DragonCave(2))));//gson.tojson() converts your pojo to json
            post.setEntity(postingString);
            post.setHeader("Content-type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(post);) {
                String textBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                int statusCode = response.getStatusLine().getStatusCode();
                DragonDto sdoid = new Gson().fromJson(textBody, DragonDto.class);
            }
        }


//        post.setEntity(postingString);
//        post.setHeader("Content-type", "application/json");
//        HttpResponse  response = httpClient.execute(post);

    }
}
