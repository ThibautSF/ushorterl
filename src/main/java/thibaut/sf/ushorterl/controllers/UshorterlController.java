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
import thibaut.sf.ushorterl.kgs.exceptions.OutOfKeysException;
import thibaut.sf.ushorterl.models.Key;
import thibaut.sf.ushorterl.models.ShortURL;
import thibaut.sf.ushorterl.models.ShortURLParameter;
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
    @Operation(summary = "Get infos about a short URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "shortURL infos",
                    content = {@Content(schema = @Schema(implementation = ShortURL.class))}),
            @ApiResponse(responseCode = "404", description = "Unknown shortURL",
                    content = @Content)})
    @RequestMapping(value = "/url_info/{id}", method = RequestMethod.GET)
    public ResponseEntity<ShortURL> getShortURLInfos(@PathVariable("id") String id) {
        ShortURL shortURL = shorturlService.getShortURL(id);

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
     * @param id
     * @return
     */
    @Operation(summary = "Get short URL (redirect)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redirection to full URL",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unknown shortURL",
                    content = @Content)})
    @RequestMapping(value = "/short_url/{id}", method = RequestMethod.GET)
    public ResponseEntity<Void> getShortURL(@PathVariable("id") String id) {
        ShortURL shortURL = shorturlService.getShortURL(id);

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
     * @param shortURLParameter
     * @return
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
     * @param id
     * @return
     */
    @Operation(summary = "Update short URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "shortURL updated",
                    content = {@Content(schema = @Schema(implementation = ShortURL.class))}),
            @ApiResponse(responseCode = "400", description = "fullURL parameter is missing",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Unknown shortURL",
                    content = @Content)})
    @RequestMapping(value = "/short_url/{id}",
            method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ShortURL> putShortURL(@PathVariable("id") String id, @RequestBody ShortURLParameter newShortUrl) {
        ShortURL shortURL = shorturlService.getShortURL(id);

        if (shortURL == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        String fullURL = newShortUrl.getFullURL().trim();

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
     * @param id
     * @return
     */
    @RequestMapping(value = "/short_url/{id}", method = RequestMethod.DELETE)
    public String deleteShortURL(@PathVariable("id") String id) {
        //TODO
        return null;
    }
}
