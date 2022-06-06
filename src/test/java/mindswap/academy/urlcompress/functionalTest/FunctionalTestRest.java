package mindswap.academy.urlcompress.functionalTest;


import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.web.client.RestTemplate;
import java.io.IOException;

/**
 * Not done yet,
 *
 */

public class FunctionalTestRest {
    @LocalServerPort
    Integer port = 8000;
    RestTemplate restTemplate = new RestTemplate();


    @When("the client calls version")
    public void theClientCallsVersion() {
    }

    @Then("the client receives status code of {int}")
    public void theClientReceivesStatusCodeOf(int arg0) {

    }


    // GET

    @Test
    void givenResourceUrlCompress_whenSendGetForRequestEntity_thenStatusOk() throws IOException {

    }


    @When("I fetch urls at {string}")
    public void iFetchUrlsAt(String arg0) {

    }

    @Then("I should find all urls")
    public void iShouldFindAllUrls() {
    }
}
