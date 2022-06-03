package mindswap.academy.urlcompress.controller;

import lombok.RequiredArgsConstructor;
import mindswap.academy.urlcompress.Persistence.model.UrlCompress;
import mindswap.academy.urlcompress.service.UrlCompressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/")
@RequiredArgsConstructor
public class UrlCompressController {

    private final UrlCompressService urlCompressService;

    @GetMapping(value="hello")
   public ResponseEntity<String> welcome() {
        return ResponseEntity.ok().body("Hello world");
   }

    @GetMapping(value = "url")
    public ResponseEntity<List<UrlCompress>> findAllUrl() {
        return ResponseEntity.ok().body(urlCompressService.getAllUrl());

    }

    @GetMapping(value="/url/{id}")
    public ResponseEntity<?>getOriginalUrl(@PathVariable(name="id") String id){
          return ResponseEntity.ok().body(urlCompressService.getLongUrl(id));
    }

    @PostMapping(value="/url/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UrlCompress generateShortUrl(@Valid @RequestBody String url){
        return urlCompressService.createShortUrl(url);
    }
}
