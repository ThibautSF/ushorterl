package thibaut.sf.ushorterl.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Document("ushorterl")
public class ShortURL extends AbstractShortURL {
    @Id
    @NotBlank
    protected String shortURL;
    @NotBlank
    protected Date dateCreated;
    @NotBlank
    protected int nbAccess;

    public ShortURL() {
        super();
    }

    public ShortURL(String fullURL, String shortURL, Date dateCreated, int nbAccess) {
        super(fullURL);
        this.shortURL = shortURL;
        this.dateCreated = dateCreated;
        this.nbAccess = nbAccess;
    }

    public ShortURL(String fullURL, String key) {
        this(fullURL, key, new Date(), 0);
    }

    public String getShortURL() {
        return this.shortURL;
    }

    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }

    public int getNbAccess() {
        return this.nbAccess;
    }

    public void setNbAccess(int nbAccess) {
        this.nbAccess = nbAccess;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
