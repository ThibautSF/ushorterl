package thibaut.sf.ushorterl.models;

import javax.validation.constraints.NotBlank;

public abstract class AbstractShortURL {
    @NotBlank
    protected String fullURL;

    public AbstractShortURL() {
    }

    public AbstractShortURL(String fullURL) {
        this.fullURL = fullURL;
    }

    public String getFullURL() {
        return this.fullURL;
    }

    public void setFullURL(String fullURL) {
        this.fullURL = fullURL;
    }
}
