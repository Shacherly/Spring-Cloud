package org.apache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@EnableEurekaServer
@SpringBootApplication
@RestController
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

    LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(5, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    return "load" + new Random().nextInt(100);
                }
            });


    @PostMapping("/test-set/{id}")
    public String set(@PathVariable("id") String id) throws ExecutionException {
        cache.put(id, "set: " + new Random().nextInt(100));
        System.out.println(cache.get(id));
        return "success";
    }


    @GetMapping("/test-get/{id}")
    public String get(@PathVariable("id") String id) {
        String cacheValue = "";
        try {
            cacheValue =  cache.get(id);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return cacheValue;
    }
}
