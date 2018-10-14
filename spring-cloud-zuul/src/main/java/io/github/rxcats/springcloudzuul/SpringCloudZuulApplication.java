package io.github.rxcats.springcloudzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.netflix.zuul.ZuulFilter;

@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
public class SpringCloudZuulApplication {

    @Bean
    ZuulFilter sampleFilter() {
        return new SampleFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZuulApplication.class, args);
    }
}
