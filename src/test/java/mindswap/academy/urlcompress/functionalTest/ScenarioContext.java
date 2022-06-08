package mindswap.academy.urlcompress.functionalTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class ScenarioContext {

    private final TestRestTemplate testRestTemplate;

    private String path;
    private HttpMethod method;
    private ResponseEntity<String> responseEntity;

    public ScenarioContext(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }


    public void performRequest() {
        var headers = new HttpEntity<>(new HttpHeaders());
        Object requestBody = new Object(); //TODO

        HttpEntity<Object> objectHttpEntity = new HttpEntity<>(headers);
        responseEntity = testRestTemplate.exchange(URI.create(path), method, objectHttpEntity, String.class);
    }

    public void setPath(String path) {
        this.path = "http://localhost:8000" + path;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public ResponseEntity<String> getResponseEntity() {
        return responseEntity;
    }
}
