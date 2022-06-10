package mindswap.academy.urlcompress.Persistence.model;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UrlCompress)) return false;
        UrlCompress that = (UrlCompress) o;
        return id == that.id && Objects.equals(longUrl, that.longUrl) && Objects.equals(shortUrl, that.shortUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, longUrl, shortUrl);
    }

    @Override
    public String toString() {
        return "UrlCompress{" +
                "id=" + id +
                ", longUrl='" + longUrl + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                '}';
    }
}
