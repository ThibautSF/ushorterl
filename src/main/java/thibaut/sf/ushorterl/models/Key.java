package thibaut.sf.ushorterl.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "keys")
public class Key {
    @Id
    private String key;
    private boolean used;

    public Key() {
    }

    public Key(String key) {
        super();
        this.key = key;
        this.used = false;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean getUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
