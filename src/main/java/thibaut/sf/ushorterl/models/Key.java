package thibaut.sf.ushorterl.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Key model entity for KGS
 *
 * @author ThibautSF
 * @version 1.0
 */
@Entity
@Table(name = "keys")
public class Key {
    /**
     * Key value as string
     */
    @Id
    private String key;

    /**
     * Indicate if the key was used in a ShortURL and should not be used again
     */
    private boolean used;

    /**
     * Default constructor
     */
    public Key() {
    }

    /**
     * Main constructor to create a new key, set used to false
     *
     * @param key short key
     */
    public Key(String key) {
        super();
        this.key = key;
        this.used = false;
    }

    /**
     * @return the short key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key new short key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return used status
     */
    public boolean getUsed() {
        return used;
    }

    /**
     * @param used new use status
     */
    public void setUsed(boolean used) {
        this.used = used;
    }
}
