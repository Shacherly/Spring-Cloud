package com.t;

import com.h.Person;
import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ConsumerController {

    @Autowired
    UserConsumerApi userConsumerApi;
    @Autowired
    RestService rest;
    @Value("${server.port}")
    String port;


    @GetMapping("/alive")
    public String alive() {
        return userConsumerApi.alive();
    }

    @GetMapping("/alive2")
    // @HystrixCommand(defaultFallback = "back")
    public String alive2() {
        return "Consumer port : " + port + rest.alive();
    }

    @GetMapping("/getById")
    public String getById(Integer id) {
        return userConsumerApi.getById(id);
    }

    @PostMapping("/postPerson")
    public Person postPerson(Person p) {
        return userConsumerApi.postPerson(p);
    }


    @GetMapping(value = "/map", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Integer, String> map(Integer id) {
        return userConsumerApi.getMap(id);
    }

    @GetMapping(value = "/map2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Integer, String> map2(Integer id, String name) {
        return userConsumerApi.getMap2(id, name);
    }

    @GetMapping(value = "/map3", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getMap3(@RequestParam Map<String, Object> map) {
        return userConsumerApi.getMap3(map);
    }

    @PostMapping(value = "/postMap", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> postMap(@RequestBody Map<String, Object> map) {
        return userConsumerApi.postMap(map);
    }
}
