package com.ms;

import com.h.Person;
import com.h.UserAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// 本来暴露的接口全部都在UserAPI接口中，
// 那么provider的Controller只要implements UserAPI即可实现所有方法
// 而且也不需要写@GetMapping()，因为UserAPI中已经写了
@RestController
public class ProviderController_UserAPI_Impl implements UserAPI {
    private static final AtomicInteger AI = new AtomicInteger(1);

    @Value("${server.port}")
    String port;

    @Override
    // @GetMapping("/alive")
    public String alive() {
        // int i = 1 / 0;
        try {
            // 模拟业务执行超时
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(port + "端口调用" + AI.getAndIncrement() + "次[" + Instant.now() + "]");
        return "ok, (づ￣ 3￣)づ [from provider, port: " + port + "]";
    }

    @Override
    public String getById(/*@RequestParam("id")*/ Integer id) {
        return "用户ID : " + id;
    }

    @Override
    public Person postPerson(Person p) {
        return new Person(11, "PAPAPA");
    }

    /**
     * 这里 getMapping 是给Feign看的 get请求 user-provider/getMap?id={1}
     * @RequestParam("id") 也是给Feign看的
     *
     * HttpClient Http协议
     * @param id
     * @return
     */
    @GetMapping("/getMap")
    public Map<Integer, String> getMap(@RequestParam("id") Integer id) {
        System.out.println(id);
        return Collections.singletonMap(id, "mmeme");
    }

    @GetMapping("/getMap2")
    public Map<Integer, String> getMap2(@RequestParam("id") Integer id, @RequestParam("name") String name) {
        System.out.println(id);
        return Collections.singletonMap(id, name);
    }

    @GetMapping("/getMap3")
    public List<Map<String, Object>> getMap3(@RequestParam Map<String, Object> map) {
        return Stream.generate(() -> map).limit(5).collect(Collectors.toList());
    }


    @PostMapping("/postMap")
    public Map<String, Object> postMap(@RequestBody Map<String, Object> map) {
        return map;
    }

}
