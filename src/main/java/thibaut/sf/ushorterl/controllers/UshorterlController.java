package thibaut.sf.ushorterl.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thibaut.sf.ushorterl.exceptions.OutOfKeysException;
import thibaut.sf.ushorterl.models.Key;
import thibaut.sf.ushorterl.models.ShortURL;
import thibaut.sf.ushorterl.models.ShortURLParameter;
import thibaut.sf.ushorterl.services.KeyService;
import thibaut.sf.ushorterl.services.ShortURLService;

import java.net.URI;
import java.util.Date;

/**
 * Main REST Controller
 *
 * @author ThibautSF
 * @version 1.0
 */
@RestController
public class UshorterlController {
    /**
     * Key Generation System service
     */
    @Autowired
    private KeyService keyService;

    /**
     * ShortURL service
     */
    @Autowired
    private ShortURLService shorturlService;

    /**
     * Get infos about a short URL
     *
     * @param keystr the short URL key string
     * @return An HTTP response with ShortURL object
     */
    @Operation(summary = "Get infos about a short URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "shortURL infos",
                    content = {@Content(schema = @Schema(implementation = ShortURL.class))}),
            @ApiResponse(responseCode = "404", description = "Unknown shortURL",
                    content = @Content)})
    @RequestMapping(value = "/url_info/{keystr}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ShortURL> getShortURLInfos(@PathVariable("keystr") String keystr) {
        ShortURL shortURL = shorturlService.getShortURL(keystr);

        //Check if short URL exists
        if (shortURL == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(shortURL);
    }

    /**
     * Get short URL (redirect)
     *
     * @param keystr the short URL key string
     * @return An HTTP response with redirection to long URL
     */
    @Operation(summary = "Get short URL (redirect)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redirection to full URL",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unknown shortURL",
                    content = @Content)})
    @RequestMapping(value = "/short_url/{keystr}", method = RequestMethod.GET)
    public ResponseEntity<Void> getShortURL(@PathVariable("keystr") String keystr) {
        ShortURL shortURL = shorturlService.getShortURL(keystr);

        //Check if short URL exists
        if (shortURL == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        shortURL.setNbAccess(shortURL.getNbAccess() + 1);

        shorturlService.saveShortURL(shortURL);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(shortURL.getFullURL()))
                .build();
    }

    /**
     * Create a new short URL
     * <p>
     * Get a random key, associate it with URL and flag key as used
     * </p>
     *
     * @param shortURLParameter the new url parameters
     * @return An HTTP response with ShortURL object created
     */
    @Operation(summary = "Create a new short URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "shortURL created",
                    content = {@Content(schema = @Schema(implementation = ShortURL.class))}),
            @ApiResponse(responseCode = "400", description = "fullURL parameter is missing",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Limit of short URL reached on this server",
                    content = @Content)})
    @RequestMapping(value = "/short_url/",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ShortURL> postShortURL(@RequestBody ShortURLParameter shortURLParameter) {
        String fullURL = shortURLParameter.getFullURL().trim();

        if (fullURL.length() == 0) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            Key key = keyService.getUnusedKey();

            ShortURL newShortUrl = new ShortURL(fullURL, key.getKey());
            newShortUrl.setShortURL(key.getKey());
            newShortUrl.setDateCreated(new Date());
            newShortUrl.setNbAccess(0);

            shorturlService.saveShortURL(newShortUrl);

            key.setUsed(true);
            keyService.saveKey(key);

            return ResponseEntity
                    .created(URI.create(String.format("/short_url/%s", newShortUrl.getShortURL())))
                    .body(newShortUrl);
        } catch (OutOfKeysException e) {
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(null);
        }
    }

    /**
     * Update short URL
     *
     * @param keystr           the short URL key string
     * @param newShortUrlParam the new url parameters
     * @return An HTTP response with ShortURL object updated
     */
    @Operation(summary = "Update short URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "shortURL updated",
                    content = {@Content(schema = @Schema(implementation = ShortURL.class))}),
            @ApiResponse(responseCode = "400", description = "fullURL parameter is missing",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unknown shortURL",
                    content = @Content)})
    @RequestMapping(value = "/short_url/{keystr}",
            method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ShortURL> putShortURL(@PathVariable("keystr") String keystr, @RequestBody ShortURLParameter newShortUrlParam) {
        ShortURL shortURL = shorturlService.getShortURL(keystr);

        //Check if short URL exists
        if (shortURL == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        String fullURL = newShortUrlParam.getFullURL().trim();

        if (fullURL.length() == 0) {
            return ResponseEntity.badRequest().body(null);
        }

        shortURL.setFullURL(fullURL);

        shorturlService.saveShortURL(shortURL);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(shortURL);
    }

    /**
     * Delete short URL.
     * <p>
     * Reset associated key to unused
     * </p>
     *
     * @param keystr the short URL key string
     * @return An HTTP response with ShortURL object deleted
     */
    @Operation(summary = "Delete short URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "shortURL deleted",
                    content = {@Content(schema = @Schema(implementation = ShortURL.class))}),
            @ApiResponse(responseCode = "404", description = "Unknown shortURL",
                    content = @Content)})
    @RequestMapping(value = "/short_url/{keystr}",
            method = RequestMethod.DELETE,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ShortURL> deleteShortURL(@PathVariable("keystr") String keystr) {
        ShortURL shortURL = shorturlService.getShortURL(keystr);

        //Check if short URL exists
        if (shortURL == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        //Retrieve key
        Key key = keyService.getKey(keystr);

        //Delete shortURL
        shorturlService.deleteShortURL(shortURL);

        //Reset key to unused status
        key.setUsed(false);
        keyService.saveKey(key);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(shortURL);
    }
}
