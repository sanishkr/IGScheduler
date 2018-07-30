package com.sns.igscheduler;

import com.sns.igscheduler.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class IgschedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IgschedulerApplication.class, args);
    }
}
