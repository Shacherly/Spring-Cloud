package com.t;

import com.h.Person;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserProviderBack implements UserConsumerApi {
    @Override
    public Map<Integer, String> getMap(Integer id) {
        return null;
    }

    @Override
    public Map<Integer, String> getMap2(Integer id, String name) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getMap3(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Object> postMap(Map<String, Object> map) {
        return null;
    }

    @Override
    public String alive() {
        return "Hystrix 降级了！";
    }

    @Override
    public String getById(Integer id) {
        return null;
    }

    @Override
    public Person postPerson(Person p) {
        return null;
    }
}
