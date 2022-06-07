package mindswap.academy.urlcompress.functionalTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class FunctionalTestConfiguration {

    @Bean
    @Scope("cucumber-glue")
    public ScenarioContext scenarioContext(@Autowired TestRestTemplate testRestTemplate) {
        return new ScenarioContext(testRestTemplate);
    }

    @Bean
    public TestRestTemplate testRestTemplate() {
        return new TestRestTemplate();
    }
}
