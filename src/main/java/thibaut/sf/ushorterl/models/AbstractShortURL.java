package thibaut.sf.ushorterl.models;

import javax.validation.constraints.NotBlank;

/**
 * Base ShortURL parent object
 *
 * @author ThibautSF
 * @version 1.0
 */
abstract class AbstractShortURL {
    /**
     * The full URL targeted by this short URL
     */
    @NotBlank
    protected String fullURL;

    /**
     * Default constructor
     */
    public AbstractShortURL() {
    }

    /**
     * Main constructor to create a new AbstractShortURL
     *
     * @param fullURL The full URL targeted
     */
    public AbstractShortURL(String fullURL) {
        this.fullURL = fullURL;
    }

    /**
     * @return the full URL targeted by this short URL
     */
    public String getFullURL() {
        return this.fullURL;
    }

    /**
     * @param fullURL The new full URL targeted
     */
    public void setFullURL(String fullURL) {
        this.fullURL = fullURL;
    }
}
