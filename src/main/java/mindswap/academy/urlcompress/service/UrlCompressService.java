package mindswap.academy.urlcompress.service;


import com.google.common.hash.HashFunction;

import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mindswap.academy.urlcompress.Persistence.model.UrlCompress;
import mindswap.academy.urlcompress.Persistence.repository.UrlCompressRepository;
import mindswap.academy.urlcompress.exception.UrlCompressNotValidException;

@Service
@RequiredArgsConstructor
@Slf4j
@Component
public class UrlCompressService {


    private final Logger logger = LoggerFactory.getLogger(UrlCompressService.class);
    private final UrlCompressRepository compressRepo;
    private final HashFunction hashing;


    /**
     * @return list of all urls
     */
    public List<UrlCompress> getAllUrl() {
        return compressRepo.findAll();
    }

    public String getLongUrl(String shortUrl) {
        return Optional.of(compressRepo.findByShortUrl(shortUrl)).
                orElseThrow(() -> new RuntimeException(
                        ("the  shortUrl sadlkhbnaks,jhbdnfound " + shortUrl)));
    }

    /**
     * create a new shortUrl
     *
     * @param url
     * @return shortUrl
     */

    public UrlCompress createShortUrl(String url) {
        if (!validateUrl(url)) {
            logger.error("the given url {} is not valid", url);
            throw new UrlCompressNotValidException("The given URL, is not valid");
        }
        UrlCompress urlCompress = new UrlCompress();
        urlCompress.setLongUrl(url);
        urlCompress.setShortUrl(getShortUrl(url));
        return compressRepo.save(urlCompress);
    }

    /**
     * check the validation of the given url.
     *
     * @param longUrl
     * @return true or false
     */
    public boolean validateUrl(String longUrl) {
        return UrlValidator.
                getInstance().
                isValid(longUrl);
    }

    public String getShortUrl(String fullUrl) {

        return hashing
                    .hashString(fullUrl, Charset.defaultCharset())
                    .toString();
    }

}
