package com.t;

import com.h.UserAPI;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 相当于是调用user-provider中的控制器 /alive
 * name 可以换成我想调用的那个【服务提供者 user-provider】的名字，所以这是去eureka中寻找该服务，
 * 需要eureka-server的支持
 *
 *
 * 但其实这样写和以前的RestTemplate没什么区别，也需要知道服务名user-provider，也需要写uri /alive
 * 不过好处就是没有代码侵入，方便做异构系统
 *
 * feign是默认带有ribbon的，可以自动平均负载
 */
@FeignClient(
        name = "user-provider", /*fallback = UserProviderBack.class*/
        fallbackFactory = UserProviderBackFactory.class// 如果要粒度更细，则需要Factory
)
public interface UserConsumerApi extends UserAPI {


    /**
     * 下面全是自己想要扩展的方法，原厂商提供的方法我不用继承，相当于Adapter吧
     * 如果明明是GET  报错却是POST需要加一个依赖
     */
    @GetMapping("/getMap")
    Map<Integer, String> getMap(@RequestParam("id") Integer id);

    @GetMapping("/getMap2")
    Map<Integer, String> getMap2(@RequestParam("id") Integer id, @RequestParam("name") String name);

    @GetMapping("/getMap3")
    List<Map<String, Object>> getMap3(@RequestParam Map<String, Object> map);

    @PostMapping("/postMap")
    Map<String, Object> postMap(Map<String, Object> map);
}
