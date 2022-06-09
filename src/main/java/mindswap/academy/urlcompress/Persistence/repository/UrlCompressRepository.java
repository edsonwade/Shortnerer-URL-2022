package mindswap.academy.urlcompress.Persistence.repository;


import mindswap.academy.urlcompress.Persistence.model.UrlCompress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface UrlCompressRepository extends JpaRepository<UrlCompress,Integer> {

    @Query(value="select long_url " +
            "from" +
            " url where short_url =?1",
            nativeQuery = true)

    String findByShortUrl(String shortUrl);

}
