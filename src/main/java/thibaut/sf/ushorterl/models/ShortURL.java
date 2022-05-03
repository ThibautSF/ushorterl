package thibaut.sf.ushorterl.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("ushorterl")
public class ShortURL {
    @Id
    private String shortURL;
    private String fullURL;
    private Date dateCreated;
    private int nbAccess;

    public ShortURL(String shortURL, String fullURL, Date dateCreated, int nbAccess) {
        this.shortURL = shortURL;
        this.dateCreated = dateCreated;
        this.fullURL = fullURL;
        this.nbAccess = nbAccess;
    }

    public String getShortURL() {
        return this.shortURL;
    }

    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }

    public String getFullURL() {
        return this.fullURL;
    }

    public void setFullURL(String fullURL) {
        this.fullURL = fullURL;
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
