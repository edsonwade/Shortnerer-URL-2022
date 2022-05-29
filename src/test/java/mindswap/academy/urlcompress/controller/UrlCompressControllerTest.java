package mindswap.academy.urlcompress.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mindswap.academy.urlcompress.service.UrlCompressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UrlCompressController.class})
@ExtendWith(SpringExtension.class)
class UrlCompressControllerTest {
    @Autowired
    private UrlCompressController urlCompressController;

    @MockBean
    private UrlCompressService urlCompressService;

    /**
     * Method under test: {@link UrlCompressController#findAllUrl()}
     */
    @Test
    void testFindAllUrl() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/url");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.urlCompressController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link UrlCompressController#generateShortUrl(String)}
     */
    @Test
    void testGenerateShortUrl() throws Exception {
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/url/create")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new String()));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.urlCompressController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link UrlCompressController#getOriginalUrl(String)}
     */
    @Test
    void testGetOriginalUrl() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/url/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.urlCompressController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link UrlCompressController#welcome()}
     */
    @Test
    void testWelcome() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hello");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.urlCompressController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

