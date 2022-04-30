package thibaut.sf.ushorterl.controller;

import org.springframework.web.bind.annotation.*;

/**
 *
 */
@RestController
public class UshorterlController {
    @GetMapping("/test")
    public String test() {
        //KeyGenerator.generateKeys(4);
        return "List :\n";
    }

    /**
     * @return
     */
    @RequestMapping(value = "/short_url", method = RequestMethod.GET)
    public String getShortURL() {
        return null;
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/short_url/{id}", method = RequestMethod.POST)
    public String postShortURL(@PathVariable("id") String id) {
        return null;
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/short_url/{id}", method = RequestMethod.PUT)
    public String putShortURL(@PathVariable("id") String id) {
        return null;
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
