package mindswap.academy.urlcompress.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UrlCompressNotValidException extends RuntimeException{

    public UrlCompressNotValidException(String message) {

        super(message);
    }
}
