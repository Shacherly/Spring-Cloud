package com.t;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {
    @Autowired
    RestTemplate template;

    @HystrixCommand(defaultFallback = "back")
    public String alive() {
        String url = "http://user-provider/alive";
        return template.getForObject(url, String.class);
    }


    public String back() {
        return "back 呵呵呵";
    }
}
