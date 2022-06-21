package com.hatanaka.feignresilience4j.resource;

import com.hatanaka.feignresilience4j.client.MyIntegrationClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestResource {

    private final MyIntegrationClient myIntegrationClient;

    @GetMapping("/")
    public String testEndpoint() throws Exception {
        log.info("m=testEndpoint");
        final String result = myIntegrationClient.getMyString().orElseThrow();
//        final String result = myIntegrationClient.getMyStringThreadPool().join();
        log.info("m=testEndpoint, result={}", result);
        return result;
    }
}
