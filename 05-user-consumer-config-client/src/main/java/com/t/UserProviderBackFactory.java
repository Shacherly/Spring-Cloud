package com.t;

import com.hhhh.Person;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserProviderBackFactory implements FallbackFactory<UserConsumerApi> {
    @Override
    public UserConsumerApi create(Throwable throwable) {
        return new UserConsumerApi() {
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
                System.out.println(throwable);
                if (throwable instanceof FeignException.InternalServerError) {
                    return "远程服务器500哦！";
                }
                System.out.println(throwable.toString());
                return "呵呵";
            }

            @Override
            public String getById(Integer id) {
                return null;
            }

            @Override
            public Person postPerson(Person p) {
                return null;
            }
        };
    }
}
