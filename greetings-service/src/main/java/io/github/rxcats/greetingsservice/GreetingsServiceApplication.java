package io.github.rxcats.greetingsservice;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableEurekaClient
@SpringBootApplication
public class GreetingsServiceApplication {

    @GetMapping("/hi/{name}")
    public Map<String, String> hi(@PathVariable String name, @RequestHeader(value = "X-CNJ-Name", required = false) Optional<String> cn) {
        return Collections.singletonMap("greeting", "Hello, " + cn.orElse(name) + "!");
    }

    public static void main(String[] args) {
        SpringApplication.run(GreetingsServiceApplication.class, args);
    }
}
