package mindswap.academy.urlcompress.functionalTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;

import mindswap.academy.urlcompress.UrlCompressApplication;

import static org.assertj.core.api.Assertions.assertThat;

@CucumberContextConfiguration
@SpringBootTest(classes = UrlCompressApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = {FunctionalTestConfiguration.class})
public class RequestResponseStep {

    private final ScenarioContext scenarioContext;

    public RequestResponseStep(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Given("^I have a (.*) (.*) request$")
    public void iHaveARequest(String method, String path) {
        scenarioContext.setMethod(HttpMethod.resolve(method));
        scenarioContext.setPath(path);
    }

    @When("^I perform the request$")
    public void performRequest() {
        scenarioContext.performRequest();
    }

    @Then("^I receive (.*) status code$")
    public void assertStatusCode(String expectedStatusCode) {
        assertThat(scenarioContext.getResponseEntity().getStatusCode().value()).isEqualTo(Integer.valueOf(expectedStatusCode));
    }

    @Then("^I receive a (.*) message$")
    public void assertExpectedMessage(String expectedMessage) {
        assertThat(scenarioContext.getResponseEntity().getBody()).isEqualTo(expectedMessage);
    }
}
