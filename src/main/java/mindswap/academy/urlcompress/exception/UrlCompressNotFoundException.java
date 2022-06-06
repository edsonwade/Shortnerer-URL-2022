package mindswap.academy.urlcompress.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UrlCompressNotFoundException extends RuntimeException {
    public UrlCompressNotFoundException(String message) {
        super(message);
    }



}
