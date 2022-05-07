package thibaut.sf.ushorterl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot API server entry point
 *
 * @author ThibautSF
 * @version 1.0
 */
@SpringBootApplication
public class UshorterlApplication {
    /**
     * @param args arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(UshorterlApplication.class, args);
    }
}
