package mindswap.academy.urlcompress.service.easymock;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import mindswap.academy.urlcompress.Persistence.model.UrlCompress;
import mindswap.academy.urlcompress.Persistence.repository.UrlCompressRepository;
import mindswap.academy.urlcompress.exception.UrlCompressNotValidException;
import mindswap.academy.urlcompress.service.UrlCompressService;
import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UrlCompressServiceTest extends EasyMockSupport {

    private static final String HTTPS_WWW_EXAMPLE_COM = "https://www.example.com/";
    private static final String ABC_123 = "abc123";
    private UrlCompressRepository urlCompressRepositoryMock;
    private HashFunction hashFunctionMock;
    private UrlCompressService urlCompressService;

    @BeforeEach
    void setUp() {
        urlCompressRepositoryMock = EasyMock.mock(UrlCompressRepository.class);
        hashFunctionMock = EasyMock.mock(HashFunction.class);
        urlCompressService = new UrlCompressService(urlCompressRepositoryMock, hashFunctionMock);
    }

    @Test
    void testGetAllUrl() {
        List<UrlCompress> urlList = new ArrayList<>();
        urlList.add(new UrlCompress(HTTPS_WWW_EXAMPLE_COM, ABC_123));
        urlList.add(new UrlCompress("https://www.example.com/page1", "def456"));

        EasyMock.expect(urlCompressRepositoryMock.findAll()).andReturn(urlList);
        EasyMock.replay(urlCompressRepositoryMock);

        List<UrlCompress> result = urlCompressService.getAllUrl();

        assertEquals(urlList.size(), result.size());
        assertEquals(urlList.get(0), result.get(0));
        assertEquals(urlList.get(1), result.get(1));

        EasyMock.verify(urlCompressRepositoryMock);
    }

    @Test
    void testGetLongUrl() {
        String shortUrl = ABC_123;
        String longUrl = HTTPS_WWW_EXAMPLE_COM;

        UrlCompress urlCompress = new UrlCompress(longUrl, shortUrl);
        EasyMock.expect(urlCompressRepositoryMock.findByShortUrl(shortUrl)).andReturn(String.valueOf(urlCompress));
        EasyMock.replay(urlCompressRepositoryMock);

        String result = urlCompressService.getLongUrl(shortUrl);

        assertNotEquals(longUrl, result);

        EasyMock.verify(urlCompressRepositoryMock);
    }

    @Test
    void testCreateShortUrl_validUrl() {
        String longUrl = HTTPS_WWW_EXAMPLE_COM;

        String hash = ABC_123;
        UrlCompress urlCompress = new UrlCompress(longUrl, hash);

        EasyMock.expect(hashFunctionMock.hashString(longUrl, Charset.defaultCharset()))
                .andReturn(HashCode.fromString(hash));
        EasyMock.expect(urlCompressRepositoryMock.save(urlCompress)).andReturn(urlCompress);
        EasyMock.replay(hashFunctionMock, urlCompressRepositoryMock);

        UrlCompress result = urlCompressService.createShortUrl(longUrl);

        assertEquals(urlCompress, result);

        EasyMock.verify(hashFunctionMock, urlCompressRepositoryMock);
    }

    @Test
    void testCreateShortUrl_invalidUrl() {
        String longUrl = "not-a-valid-url";

        EasyMock.replay(hashFunctionMock, urlCompressRepositoryMock);

        Assertions.assertThrows(UrlCompressNotValidException.class, () -> {
            urlCompressService.createShortUrl(longUrl);
        });

        EasyMock.verify(hashFunctionMock, urlCompressRepositoryMock);
    }

    @Test
    void testValidateUrl() {
        String invalidUrl = "invalid url";

        boolean validResult = urlCompressService.validateUrl(HTTPS_WWW_EXAMPLE_COM);
        boolean invalidResult = urlCompressService.validateUrl(invalidUrl);

        assertTrue(validResult);
        assertFalse(invalidResult);
    }
}


