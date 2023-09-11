package mindswap.academy.urlcompress.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import mindswap.academy.urlcompress.Persistence.model.UrlCompress;
import mindswap.academy.urlcompress.service.UrlCompressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.Charset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(UrlCompressController.class)
class UrlCompressControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UrlCompressService urlCompressService;

    @MockBean
    private HashFunction hashing;

    /**
     * Method under test: {@link UrlCompressController#findAllUrl()}
     */
    @Test
    void testGetFindAllUrl() throws Exception {

        when(urlCompressService.getAllUrl()).thenReturn(
                List.of(new UrlCompress(1, "short", "www.example.com"))
        );

        this.mockMvc
                .perform(get("/api/v1/url"))
                //Validate the response code and content
                .andExpect(status().isOk())
                .andExpect((content().contentType(MediaType.APPLICATION_JSON_VALUE)))
                //Validate the return fields
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].longUrl").value("short"))
                .andExpect(jsonPath("$[0].shortUrl").value("www.example.com"));

        verify(urlCompressService, times(1)).getAllUrl();
    }

    /**
     * Method under test: {@link UrlCompressController#generateShortUrl(String)}
     */
    @Test
    void testPostGenerateShortUrl() throws Exception {

        UrlCompress urlCompress = new UrlCompress();
        urlCompress.setId(1);
        urlCompress.setLongUrl("www.example.com");
        urlCompress.setShortUrl("short");
        when(urlCompressService.createShortUrl(urlCompress.getLongUrl())).thenReturn(urlCompress);

        mockMvc.perform(post("/api/v1/url/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON) // Set the Content-Type header
                        .content(asJsonString(urlCompress)))
                .andExpect(status().isCreated());


    }

    @Test
    @DisplayName("Get /api/v1/hello -Success")
    void testGetWelcomeReturnSuccess() throws Exception {

        ResultActions ra = mockMvc.perform(get("/api/v1/hello"))
                .andExpect(status().isOk())
                .andExpect((content().contentType("text/plain;charset=UTF-8")));

        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        assertEquals("Hello world", result);

    }

    @Test
    @DisplayName("Get /api/v1/hello -Bad Request")
    void testGetWelcomeReturnNotSuccess() throws Exception {

        ResultActions ra = mockMvc.perform(get("/api/v1/welcome"))
                .andExpect(status().isNotFound());

        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        assertNotEquals("Hello world", result);

    }

    /**
     * Method under test: {@link UrlCompressController#getOriginalUrl(String)}
     */
    @Test
    void testGetOriginalUrlById() throws Exception {
        UrlCompress urlCompress = new UrlCompress(
                "https://example.org/example",
                "cc70df7e");

        var hc = mock(HashCode.class);
        when(hc.toString()).thenReturn(urlCompress.getShortUrl());
        when(hashing.hashString(urlCompress.getLongUrl(), Charset.defaultCharset())).thenReturn(hc);

        ResultActions ra = mockMvc.perform(get("/api/v1/url/{shortUrl}", "shortUrl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        assertEquals("cc70df7", result);

    }

    public String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}

