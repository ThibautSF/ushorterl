package thibaut.sf.ushorterl.kgs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import thibaut.sf.ushorterl.model.Key;
import thibaut.sf.ushorterl.repository.KeyRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class KeyGenerator {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final int BASE = ALPHABET.length();

    @Autowired
    private KeyRepository keyRepository;

    @Autowired
    private Environment env;

    private void generateKeys(List<Key> keys, int keyLength) {
        // Temporary array to store keys one by one
        char[] tmpKey = new char[keyLength];

        generateKeysIterate(keys, keyLength, tmpKey, 0);
    }

    private void generateKeysIterate(List<Key> keys, int len, char[] tmpKey, int pos) {
        if (pos == len) {
            String word = new String(tmpKey);

            keys.add(new Key(word));

            return;
        }

        for (int i = 0; i < BASE; i++) {
            tmpKey[pos] = ALPHABET.charAt(i);
            generateKeysIterate(keys, len, tmpKey, pos + 1);
        }
    }

    @PostConstruct
    public void postConstruct() {
        //Generate list of keys during first launch
        if (keyRepository.count() == 0) {
            List<Key> keys = new ArrayList<>();
            generateKeys(keys, Integer.parseInt(env.getProperty("kgs.keySize")));
            keyRepository.saveAll(keys);
        }

        System.out.println(keyRepository.count());
    }
}
