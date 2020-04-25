package net.thumbtack.school.hospital;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HospitalServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HospitalServer.class);

    public static void main(String[] args) {
        LOGGER.info(" ---------------------------------- Start Hospital Server ---------------------------------- ");
        SpringApplication.run(HospitalServer.class, args);
        LOGGER.info(" --------------------------------- Hospital Server started --------------------------------- ");
    }
}
