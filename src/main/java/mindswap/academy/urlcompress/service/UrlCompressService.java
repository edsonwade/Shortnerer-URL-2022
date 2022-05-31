package mindswap.academy.urlcompress.service;


import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mindswap.academy.urlcompress.Persistence.model.UrlCompress;
import mindswap.academy.urlcompress.Persistence.repository.UrlCompressRepository;
import mindswap.academy.urlcompress.exception.UrlCompressNotFoundException;
import mindswap.academy.urlcompress.exception.UrlCompressNotValidException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlCompressService {


    private final Logger logger = LoggerFactory.getLogger(UrlCompressService.class);
    private final UrlCompressRepository compressRepo;


    /**
     * @return list of all urls
     */
    public List<UrlCompress> getAllUrl() {
        List<UrlCompress> urlCompresses = compressRepo.findAll();
        logger.info("list of url {} ", urlCompresses);
        return Optional.of(urlCompresses).
                orElseThrow(() -> new RuntimeException("askdjhbasjkmd"));
    }

    public String getFullUrl(String id) {
        return Optional.of(compressRepo.findByShortUrl(id)).
                orElseThrow(() -> new RuntimeException(
                        ("the Url id sadlkhbnaks,jhbdnfound " + id)));
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
     * @param fullUrl
     * @return true or false
     */
    public boolean validateUrl(String fullUrl) {
        return UrlValidator.
                getInstance().
                isValid(fullUrl);
    }

    public String getShortUrl(String fullUrl) {
     /*  return Hashing.murmur3_32_fixed().
                hashString(fullUrl, Charset.defaultCharset()).
                toString();*/
        return "cc70df7e";

    }
}
