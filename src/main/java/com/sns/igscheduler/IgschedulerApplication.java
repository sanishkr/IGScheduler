package com.sns.igscheduler;

import com.sns.igscheduler.Cache.CacheHelper;
import com.sns.igscheduler.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.text.ParseException;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class IgschedulerApplication {

    public static void main(String[] args) throws ParseException, InterruptedException {
        SpringApplication.run(IgschedulerApplication.class, args);
        System.out.println("-------------------Started Loading Cache-------------------\nPostCache:"+CacheHelper.PostCache.size());
        Thread.sleep(5000);
        CacheHelper.startInitialTasks();
        System.out.println("\n-------------------Ended Loading Cache-------------------\nPostCache:"+CacheHelper.PostCache.size());
    }
}
