package com.piemoe.springboot3.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * @author Mc
 * @date 2022/12/26 09:24
 */
@RestController
@RefreshScope
public class TestController {

    @Value("${demo.name}")
    String name;

    @GetMapping("n")
    public String getName() {
        return name;
    }

    @GetMapping("/name")
    public Mono<String> name() {
        return Mono.fromSupplier(() -> name);
    }

    @GetMapping("/hello")
    public Mono<String> hello() {
        long start = System.currentTimeMillis();
        Mono<String> hello = Mono.fromSupplier(this::getHelloStr);
        System.out.println("接口耗时：" + (System.currentTimeMillis() - start));
        return hello;
    }

    private String getHelloStr() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello";
    }

    @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> flux() {
        return Flux.interval(Duration.ofSeconds(1)).map(seconds -> {
            System.out.println("我被调用了");
            return "胡八一";
        });
    }


}
