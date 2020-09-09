package org.apache.ac;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Random;

@RestController
public class ConsumerController {
    @Value("${server.port}")
    String port;
    @Autowired
    DiscoveryClient discoveryClient;
    // @Qualifier("eurekaClient")
    @Autowired
    EurekaClient eurekaClient;
    // 如果要使用多个负载客户端，就用这个来调用，用上面的调用不行
    @Autowired
    LoadBalancerClient lb;

    @GetMapping("/get")
    public String get() {
        return "hi, I am a consumer, my prot is " + port;
    }

    @GetMapping("/client1")
    public String client1() {
        List<String> services = discoveryClient.getServices();
        System.out.println(services);
        System.out.println(services.size());
        return "hi client";
    }

    @ResponseBody
    @GetMapping("/client2")
    @Produces(MediaType.APPLICATION_JSON)
    public Object client2(HttpServletResponse response) {
        response.setContentType("application/json");
        return discoveryClient.getInstances("eureka-provider");
    }

    @GetMapping("/client4")
    public Object client4() {
        // List<InstanceInfo> instances = eurekaClient.getInstancesById("HP-NightElf:provider:80");
        List<InstanceInfo> instances = eurekaClient.getInstancesByVipAddress("eureka-provider", false);
        instances.forEach(System.out::println);

        String respStr = "";
        if (instances.size() > 0) {
            InstanceInfo instanceInfo = instances.get(0);
            if (instanceInfo.getStatus() == InstanceInfo.InstanceStatus.UP) {
                String url = "http://" + instanceInfo.getHostName() + ":"
                        + instanceInfo.getPort() + "/get";
                System.out.println(url);

                RestTemplate restTemplate = new RestTemplate();
                respStr = restTemplate.getForObject(url, String.class);
                System.out.println("RPC resp: " + respStr);// resp: hi
            }
        }
        return respStr;
    }

    @GetMapping("/client5")
    public Object client5() {

        // lb会帮我们过滤掉不可用的服务，而不用我们自己去获取状态然后判断，过滤DOWN的服务
        ServiceInstance instance = lb.choose("eureka-provider");
        System.out.println(instance);

        String url = "http://" + instance.getHost() + ":"
                + instance.getPort() + "/get";
        System.out.println(url);
        RestTemplate restTemplate = new RestTemplate();
        String respStr = restTemplate.getForObject(url, String.class);
        System.out.println("resp: " + respStr);// resp: hi
        return "ib activate port -->> " + instance.getPort();
    }

    @Autowired
    RestTemplate restTemplate;

    /**
     * 本机启动了80 、81两个端口的provider，进行choose
     * @return
     */
    @GetMapping("/client6")
    public Object client6() {
        ServiceInstance instance = lb.choose("eureka-provider");
        System.out.println(instance);
        String url = "http://" + instance.getHost() + ":"
                + instance.getPort() + "/get";
        String respStr = restTemplate.getForObject(url, String.class);
        System.out.println("resp: " + respStr);// resp: hi
        return "{client6 : }" + respStr;
    }

    /**
     * 自定义负载均衡，就不需要Eureka了，直接DiscoveryClient
     * @return
     */
    @GetMapping("/client7")
    public Object client7() {
        List<ServiceInstance> instances = discoveryClient.getInstances("eureka-provider");
        // 自定义轮询算法
        int next = new Random().nextInt(instances.size());
        ServiceInstance instance = instances.get(next);
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/get";

        String respStr = restTemplate.getForObject(url, String.class);

        return respStr;
    }

    /**
     * 要先开启@LoadBalanced，
     * @return
     */
    @GetMapping("/client9")
    public Object client9() {
        // 自动处理URL
        String url = "http://mufasa:82/get";
        String respStr = restTemplate.getForObject(url, String.class);

        System.out.println(respStr);
        return respStr;
    }
}
