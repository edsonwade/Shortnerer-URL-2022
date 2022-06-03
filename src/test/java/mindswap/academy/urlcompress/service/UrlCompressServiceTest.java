package mindswap.academy.urlcompress.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import mindswap.academy.urlcompress.Persistence.model.UrlCompress;
import mindswap.academy.urlcompress.Persistence.repository.UrlCompressRepository;
import mindswap.academy.urlcompress.exception.UrlCompressNotValidException;
import org.junit.Before;
import org.junit.jupiter.api.Test;


class UrlCompressServiceTest {
    private final UrlCompressRepository urlCompressRepository = mock(UrlCompressRepository.class);
    private final HashFunction hashing = mock(HashFunction.class);
    private final UrlCompressService urlCompressService = new UrlCompressService(urlCompressRepository, hashing);


    /**
     * Z
     * Method under test: {@link UrlCompressService#getAllUrl()}
     */
    @Test
    void findAllUrlsThenReturnTheListOfAllUrls() {
        ArrayList<UrlCompress> urlCompressList = new ArrayList<>();
        when(this.urlCompressRepository.findAll()).thenReturn(urlCompressList);
        List<UrlCompress> actualAllUrl = this.urlCompressService.getAllUrl();
        assertSame(urlCompressList, actualAllUrl);
        assertTrue(actualAllUrl.isEmpty());
        verify(this.urlCompressRepository).findAll();
    }

    /**
     * Method under test: {@link UrlCompressService#getLongUrl(String)}
     */
    @Test
    void findShortUrlThenReturnTheLongUrl() {
        when(this.urlCompressRepository.findByShortUrl((String) any())).thenReturn("https://example.org/example");
        assertEquals("https://example.org/example", this.urlCompressService.getLongUrl("https://example.org/example"));
        verify(this.urlCompressRepository).findByShortUrl((String) any());
    }

    /**
     * Method under test: {@link UrlCompressService#getLongUrl(String)}
     */
    @Test
    void findShortUrlThenReturnTheLongUrlIfTheShortUrlIsNotFoundThrowsRuntimeException() {
        when(this.urlCompressRepository.findByShortUrl((String) any()))
                .thenThrow(new UrlCompressNotValidException("https://example.org/example"));
        assertThrows(UrlCompressNotValidException.class,
                () -> this.urlCompressService.getLongUrl("https://example.org/example"));
        verify(this.urlCompressRepository).findByShortUrl((String) any());
    }

    @Test
    void GivenLongUrlCreateTheShortUrl() {
        //given
        UrlCompress urlCompress = new UrlCompress(
                "https://example.org/example",
                "cc70df7e");

        var hc = mock(HashCode.class);


        //when
        when(urlCompressRepository.save(any(UrlCompress.class))).then(returnsFirstArg());
        when(hc.toString()).thenReturn(urlCompress.getShortUrl());
        when(hashing.hashString(urlCompress.getLongUrl(), Charset.defaultCharset())).thenReturn(hc);
        UrlCompress urlCompress1 = this.urlCompressService.createShortUrl("https://example.org/example");
        //the
        assertThat(urlCompress1).isEqualTo(urlCompress);
    }

    /**
     * Method under test: {@link UrlCompressService#validateUrl(String)}
     */
    @Test
    void ValidateTheGivenUrlReturnTrueIfTheUrlIsValidIfIsNotReturnFalse() {
        assertTrue(this.urlCompressService.validateUrl("https://example.org/example"));
        assertTrue(this.urlCompressService.validateUrl("http://example.org/example"));
        assertFalse(this.urlCompressService.validateUrl("UU"));
        assertFalse(this.urlCompressService.validateUrl("[::FFFF:999.999.999.999]:9U"));
        assertFalse(this.urlCompressService.validateUrl("https://example.org/examplehttps://example.org/example"));
        assertFalse(this.urlCompressService.validateUrl("UUhttps://example.org/example"));
    }

    /**
     * Method under test: {@link UrlCompressService#getShortUrl(String)}
     */
    /*@Test
   /*void givenALongUrlGetTheShortUrl() {

       // verify(this.compressService).getShortUrl(GET_SHORT);
        //assertEquals("cc70df7e", this.urlCompressService.getShortUrl("https://example.org/example"));

    }*/


}

