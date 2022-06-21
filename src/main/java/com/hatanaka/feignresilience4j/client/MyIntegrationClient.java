package com.hatanaka.feignresilience4j.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@FeignClient(name = "myIntegrationClient", url = "${feign.client.config.myIntegrationClient.url}")
public interface MyIntegrationClient {

    @GetMapping("/xpto")
//    @Retry(name = "myIntegrationClient")
//    @Bulkhead(name = "myIntegrationClient", type = Bulkhead.Type.SEMAPHORE)
    @CircuitBreaker(name = "myIntegrationClient", fallbackMethod = "getMyStringFallback")
    Optional<String> getMyString();

    default Optional<String> getMyStringFallback(Throwable cause) {
        return Optional.of("fallback getMyString");
    }

    @GetMapping("/xpto")
//    @Bulkhead(name = "myIntegrationClient", type = Bulkhead.Type.THREADPOOL)
    CompletableFuture<String> getMyStringThreadPool();
}
