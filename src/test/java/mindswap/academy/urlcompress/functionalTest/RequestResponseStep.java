package mindswap.academy.urlcompress.functionalTest;

import io.cucumber.spring.CucumberContextConfiguration;

import org.springframework.boot.test.context.SpringBootTest;

import mindswap.academy.urlcompress.UrlCompressApplication;

@CucumberContextConfiguration
@SpringBootTest(classes = UrlCompressApplication.class)
public class RequestResponseStep {


}
