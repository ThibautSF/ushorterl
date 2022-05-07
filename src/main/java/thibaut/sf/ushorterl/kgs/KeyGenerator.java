package thibaut.sf.ushorterl.kgs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import thibaut.sf.ushorterl.models.Key;
import thibaut.sf.ushorterl.repositories.KeyRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to generate keys based on alphabet.
 * <p>
 * Spring boot component.
 * </p>
 * <p>
 * Currently keys are generated using base 62.
 * </p>
 *
 * @author ThibautSF
 * @version 1.0
 */
@Component
public class KeyGenerator {
    /**
     * Alphabet used
     */
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * Base used
     */
    public static final int BASE = ALPHABET.length();

    /**
     * Key repository to directly store
     */
    @Autowired
    private KeyRepository keyRepository;

    /**
     * Access to environment variables
     */
    @Autowired
    private Environment env;

    /**
     * Generate all possible keys of a specific length for the alphabet.
     * <p>
     * Iterative algorithm {@link #generateKeysIterate(List, int, char[], int)}
     * </p>
     *
     * @param keys      list of keys generated, modified at generation
     * @param keyLength length of key wanted
     */
    private void generateKeys(List<Key> keys, int keyLength) {
        // Temporary array to store keys one by one
        char[] tmpKey = new char[keyLength];

        generateKeysIterate(keys, keyLength, tmpKey, 0);
    }

    /**
     * Generate all possible keys of a specific length for the alphabet based on the current situation.
     * <p>
     * Iteration of {@link #generateKeys(List, int)}
     * </p>
     *
     * @param keys      list of keys generated, modified at generation
     * @param keyLength length of key wanted
     * @param tmpKey    current key generated (or partially generated)
     * @param pos       position to add a char
     */
    private void generateKeysIterate(List<Key> keys, int keyLength, char[] tmpKey, int pos) {
        if (pos == keyLength) {
            String word = new String(tmpKey);

            keys.add(new Key(word));

            return;
        }

        for (int i = 0; i < BASE; i++) {
            tmpKey[pos] = ALPHABET.charAt(i);
            generateKeysIterate(keys, keyLength, tmpKey, pos + 1);
        }
    }

    /**
     * Generate list of keys and store them in KGS database at launch.
     * <p>
     * The generation occurs only at first launch.
     * </p>
     */
    @PostConstruct
    public void postConstruct() {
        //Generate keys only if repo is empty (first launch)
        if (keyRepository.count() == 0) {
            System.out.println("No keys in KGS, starting generation");

            List<Key> keys = new ArrayList<>();
            generateKeys(keys, Integer.parseInt(env.getProperty("kgs.keySize", "3")));
            keyRepository.saveAll(keys);

            System.out.println("KGS generation END");
        }
    }
}
