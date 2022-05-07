package thibaut.sf.ushorterl.models;

/**
 * ShortURL parameter model for API body parameters requests
 *
 * @author ThibautSF
 * @version 1.0
 */
public class ShortURLParameter extends AbstractShortURL {

    /**
     * Default constructor
     */
    public ShortURLParameter() {
        super();
    }

    /**
     * @param fullURL the full URL targeted
     */
    public ShortURLParameter(String fullURL) {
        super(fullURL);
    }
}
