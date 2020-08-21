package org.apache;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EurekaConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaConsumerApplication.class, args);
    }

    /**
     * 这默认就是单例的了
     * @return
     */
    @Bean
    @LoadBalanced
    RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new HttpRequestInterceptor());
        return restTemplate;
    }

    /**
     * 自定义负载策略注入进去替换默认的策略，低耦合的注入
     * @return
     */
    @Bean
    public IRule myRule() {
        return new RoundRobinRule();
        // return new RandomRule();
    }

}
