package thibaut.sf.ushorterl.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * ShortURL model entity for the API
 *
 * @author ThibautSF
 * @version 1.0
 */
@Document("ushorterl")
public class ShortURL extends AbstractShortURL {
    /**
     * The unique shortURL key (in general given by KGS)
     */
    @Id
    @NotBlank
    protected String shortURL;
    /**
     * Creation date of the ShortURL
     */
    @NotBlank
    protected Date dateCreated;
    /**
     * Number of time the ShortURL was used
     */
    @NotBlank
    protected int nbAccess;

    /**
     * Default constructor
     */
    public ShortURL() {
        super();
    }

    /**
     * Full constructor to create a new ShortURL
     *
     * @param fullURL     the full URL targeted
     * @param key         the short URL unique key
     * @param dateCreated the creation date
     * @param nbAccess    the number of time this ShortURL was clicked
     */
    public ShortURL(String fullURL, String key, Date dateCreated, int nbAccess) {
        super(fullURL);
        this.shortURL = key;
        this.dateCreated = dateCreated;
        this.nbAccess = nbAccess;
    }

    /**
     * Main constructor to create a new ShortURL.
     * <p>
     * Initialize dateCreated to system current date and nbAccess to 0
     * </p>
     *
     * @param fullURL the full URL targeted
     * @param key     the short URL unique key
     */
    public ShortURL(String fullURL, String key) {
        this(fullURL, key, new Date(), 0);
    }

    /**
     * @return the short URL unique key
     */
    public String getShortURL() {
        return this.shortURL;
    }

    /**
     * @param shortURL the short URL new unique key
     */
    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }

    /**
     * @return the number of time this ShortURL was clicked
     */
    public int getNbAccess() {
        return this.nbAccess;
    }

    /**
     * @param nbAccess the new number of time this ShortURL was clicked
     */
    public void setNbAccess(int nbAccess) {
        this.nbAccess = nbAccess;
    }

    /**
     * @return the date when this ShortURL was created
     */
    public Date getDateCreated() {
        return this.dateCreated;
    }

    /**
     * @param dateCreated the new date when this ShortURL was created
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
