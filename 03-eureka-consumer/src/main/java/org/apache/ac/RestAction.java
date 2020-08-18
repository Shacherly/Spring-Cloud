package org.apache.ac;

import com.netflix.discovery.EurekaClient;
import org.apache.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class RestAction {
    @Value("${server.port}")
    String port;
    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    @Qualifier("eurekaClient")
    EurekaClient eurekaClient;
    @Autowired
    LoadBalancerClient lb;
    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value = "/rest1/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object rest1(@PathVariable("id") Long id) {
        // String url = "http://provider/map";
        String url = "http://provider/person/" + id;
        Person respStr = restTemplate.getForObject(url, Person.class, id);

        ResponseEntity<Person> entity = restTemplate.getForEntity(url, Person.class, id);
        System.out.println(respStr);
        System.out.println(entity);
        return respStr;
    }
}
