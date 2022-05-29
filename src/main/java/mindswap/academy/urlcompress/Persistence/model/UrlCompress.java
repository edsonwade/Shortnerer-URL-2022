package mindswap.academy.urlcompress.Persistence.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name="url")
@NoArgsConstructor
@AllArgsConstructor
public class UrlCompress implements Serializable {
    private static final Long version_UUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11,
            nullable = false)
    private int id;
    @Column(name = "long_url", length = 255,
            nullable = false, unique = true)
    private String longUrl;
    @Column(name = "short_url", length = 255,
            nullable = false)
    private String shortUrl;

    public UrlCompress(String longUrl, String shortUrl) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }
}
