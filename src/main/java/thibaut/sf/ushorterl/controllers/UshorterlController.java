package thibaut.sf.ushorterl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thibaut.sf.ushorterl.kgs.exceptions.OutOfKeysException;
import thibaut.sf.ushorterl.models.Key;
import thibaut.sf.ushorterl.models.ShortURL;
import thibaut.sf.ushorterl.services.KeyService;
import thibaut.sf.ushorterl.services.ShortURLService;

import java.net.URI;
import java.util.Date;

/**
 *
 */
@RestController
public class UshorterlController {
    @Autowired
    private KeyService keyService;

    @Autowired
    private ShortURLService shorturlService;

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/short_url/{id}", method = RequestMethod.GET)
    public ResponseEntity<Void> getShortURL(@PathVariable("id") String id) {
        ShortURL shortURL = shorturlService.getShortURL(id);

        shortURL.setNbAccess(shortURL.getNbAccess() + 1);

        shorturlService.saveShortURL(shortURL);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(shortURL.getFullURL()))
                .build();
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/url_info/{id}", method = RequestMethod.GET)
    public ResponseEntity<ShortURL> getShortURLInfos(@PathVariable("id") String id) {
        ShortURL shortURL = shorturlService.getShortURL(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(shortURL);
    }

    /**
     * @return
     */
    @RequestMapping(value = "/short_url/",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ShortURL> postShortURL(@RequestBody ShortURL newShortUrl) {
        String fullURL = newShortUrl.getFullURL();

        if (fullURL.trim().length() == 0) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            Key key = keyService.getUnusedKey();

            newShortUrl.setShortURL(key.getKey());
            newShortUrl.setDateCreated(new Date());
            newShortUrl.setNbAccess(0);

            shorturlService.saveShortURL(newShortUrl);

            key.setUsed(true);
            keyService.saveKey(key);
        } catch (OutOfKeysException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity
                .created(URI.create(String.format("/short_url/%s", newShortUrl.getShortURL())))
                .body(newShortUrl);
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/short_url/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ShortURL> putShortURL(@PathVariable("id") String id, @RequestBody ShortURL newShortUrl) {
        String fullURL = newShortUrl.getFullURL();

        if (fullURL.trim().length() == 0) {
            return ResponseEntity.badRequest().body(null);
        }

        ShortURL shortURL = shorturlService.getShortURL(id);

        shortURL.setFullURL(fullURL);

        shorturlService.saveShortURL(shortURL);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(shortURL);
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/short_url/{id}", method = RequestMethod.DELETE)
    public String deleteShortURL(@PathVariable("id") String id) {
        return null;
    }
}
