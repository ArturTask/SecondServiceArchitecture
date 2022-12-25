package itmo.soa.services;

import com.google.gson.Gson;
import itmo.soa.dto.ErrorDto;
import itmo.soa.exceptions.DragonsServiceException;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class RequestService {

    public <T> T sendRequest(String url, HttpMethod method, Class<T> dtoClass) throws IOException, DragonsServiceException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException { // returns any dto (t extends object)

        HttpUriRequest req = null;
        /*SSL*/
//        KeyStore keyStore;
//        keyStore = KeyStore.getInstance("jks");
//        ClassPathResource classPathResource = new ClassPathResource("soa2.jks");
//        File f = new File("/Users/artur/Desktop/soa2.jks");
//        InputStream inputStream = new FileInputStream(f);
//        InputStream inputStream = classPathResource.getInputStream();
//        keyStore.load(inputStream, "password".toCharArray());

//        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(new SSLContextBuilder()
//                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
//                .loadKeyMaterial(keyStore, "password".toCharArray()).build(),
//                NoopHostnameVerifier.INSTANCE);

        switch (method){
            case GET:{req = new HttpGet(url); break;}
            case POST:{req = new HttpPost(url); break;}
            case PUT:{req = new HttpPut(url); break;}
            case DELETE:{req = new HttpDelete(url); break;}
            default:{break;}
        }

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) { // here include our prepared ssl
            try (CloseableHttpResponse response = httpClient.execute(req)) {
                int statusCode = response.getStatusLine().getStatusCode();
                System.out.println(statusCode);
                String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                if(statusCode!=200){
                    String myErrMessage = "";
                    try {
                        ErrorDto errorDto = getEntityFromJson(responseBody, ErrorDto.class);
                        myErrMessage = errorDto.getError();
                    }
                    catch (Exception ignored){
                    }

                    throw new DragonsServiceException("status code: " + statusCode + " message: " + myErrMessage);
                }
//                System.out.println("Response body: " + responseBody);
                return getEntityFromJson(responseBody, dtoClass);
            }
        }

    }

    private <T> T getEntityFromJson(String jsonEntity, Class<T> entityClass){
        return new Gson().fromJson(jsonEntity, entityClass);
    }


}
