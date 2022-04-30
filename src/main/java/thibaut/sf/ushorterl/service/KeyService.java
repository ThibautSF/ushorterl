package thibaut.sf.ushorterl.service;

import org.springframework.stereotype.Component;
import thibaut.sf.ushorterl.model.Key;

@Component
public interface KeyService {
    Key saveKey(Key key);

    Key getUnusedKey();
}
