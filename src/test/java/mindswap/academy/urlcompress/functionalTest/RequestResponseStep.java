package mindswap.academy.urlcompress.functionalTest;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;

import org.junit.Rule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import mindswap.academy.urlcompress.UrlCompressApplication;

import static org.assertj.core.api.Assertions.assertThat;


@CucumberContextConfiguration
@ContextConfiguration(classes = {FunctionalTestConfiguration.class})
@SpringBootTest(classes = UrlCompressApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RequestResponseStep {

    @Rule
    public static GenericContainer mariaDb = new FixedHostPortGenericContainer("mariadb:latest")
            .withFixedExposedPort(3306, 3306)
            .withEnv(Map.of("MARIADB_ROOT_PASSWORD", "mypass",
                            "MARIADB_DATABASE", "urlcompress"));
    private final ScenarioContext scenarioContext;

    public RequestResponseStep(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @BeforeAll
    public static void cenas() throws InterruptedException {
        if (!mariaDb.isRunning()) {
            mariaDb.start();

            while (!mariaDb.isRunning()) {
                System.out.println("Waiting for mariadb");
                Thread.sleep(4000);
            }

            Thread.sleep(5000);
        }
    }

    @AfterAll
    public static void afterAll() {
        mariaDb.stop();
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

   /* @Then("^I receive a message that contains (.*)$")
    public void assertContainsString(String expectedString) {
        assertThat(scenarioContext.getResponseEntity().getBody()).contains(expectedString);
    }*/

    @Then("^I should receive (.*)$")
    public void iShouldReceiveLongUrl(String url) throws Throwable {
        List<String> items = Arrays.asList(url.split(","));
        assertThat(scenarioContext.getResponseEntity().getBody()).contains(items);
    }

    @And("I wait {int} seconds")
    public void iWaitSeconds(int arg0) throws InterruptedException {
        Thread.sleep(arg0);
    }
}
