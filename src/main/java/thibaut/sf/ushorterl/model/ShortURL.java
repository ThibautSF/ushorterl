package thibaut.sf.ushorterl.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Date;

@Document
public class ShortURL {
    @Id
    private String shortURL;
    private String fullURL;
    private Date dateCreated;
    private int nbAccess;

    public ShortURL(String fullURL) {
        this.shortURL = Base64.getEncoder().encodeToString(ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(System.currentTimeMillis()).array());
        this.fullURL = fullURL;
        this.nbAccess = 0;
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
