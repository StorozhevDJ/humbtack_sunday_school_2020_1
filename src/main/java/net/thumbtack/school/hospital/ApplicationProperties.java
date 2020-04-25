package net.thumbtack.school.hospital;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class ApplicationProperties {

    @Value("${max_name_length}")
    private int maxNameLength;
    @Value("${min_password_length}")
    private int minPasswordLength;
    @Value("${server.port}")
    private int serverPort;


    public int getMaxNameLength() {
        return maxNameLength;
    }
    public int getMinPasswordLength() {
        return minPasswordLength;
    }
    public int getServerPort() {
        return serverPort;
    }


}
