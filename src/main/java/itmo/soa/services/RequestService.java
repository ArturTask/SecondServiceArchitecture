package itmo.soa.services;

import com.google.gson.Gson;
import itmo.soa.dto.AllDragonsDto;
import itmo.soa.dto.DragonDto;
import itmo.soa.dto.ErrorDto;
import itmo.soa.entity.Coordinates;
import itmo.soa.entity.DragonCave;
import itmo.soa.enums.Color;
import itmo.soa.enums.DragonCharacter;
import itmo.soa.enums.DragonType;
import itmo.soa.exceptions.BadRequestException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class RequestService {

    public <T> T sendRequest(String url, HttpMethod method, Class<T> dtoClass) throws IOException, BadRequestException { // returns any dto (t extends object)
        HttpUriRequest req = null;
        switch (method){
            case GET:{req = new HttpGet(url); break;}
            case POST:{req = new HttpPost(url); break;}
            case PUT:{req = new HttpPut(url); break;}
            case DELETE:{req = new HttpDelete(url); break;}
            default:{break;}
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpClient.execute(req)) {
                int statusCode = response.getStatusLine().getStatusCode();
                System.out.println(statusCode);
                String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                if(statusCode!=200){
                    throw new BadRequestException(String.valueOf(statusCode));
                }
//                System.out.println("Response body: " + responseBody);
                return new Gson().fromJson(responseBody, dtoClass);
            }
        }

    }
}
