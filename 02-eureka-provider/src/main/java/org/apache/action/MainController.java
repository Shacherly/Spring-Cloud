package org.apache.action;

import org.apache.HealthStatusService;
import org.apache.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class MainController {
    @Value("${server.port}")
    String port;

    @GetMapping("/get")
    public String get() {
        return "hi, I am provider, my port is" + port;
    }


    @GetMapping("/map")
    public Map<String, String> map() {
        return Collections.singletonMap("key", "value");
    }

    @GetMapping("person/{id}")
    public Person person(@PathVariable("id") Long id) {
        return new Person(id, "啪啪啪");
    }


    @Autowired
    HealthStatusService hs;

    @GetMapping("/health/{status}")
    public String health(@PathVariable("status") Boolean status) {
        hs.setStatus(status);
        return hs.getStatus();
    }
}
