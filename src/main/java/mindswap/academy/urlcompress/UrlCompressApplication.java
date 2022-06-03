package mindswap.academy.urlcompress;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
public class UrlCompressApplication {

    public static void main(String[] args) {

        SpringApplication.run(UrlCompressApplication.class, args);
    }
    @Bean
    public HashFunction hashFunction(){
        return Hashing.murmur3_32_fixed();
    }

}
