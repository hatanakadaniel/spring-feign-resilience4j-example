package com.hatanaka.feignresilience4j;

import com.hatanaka.feignresilience4j.client.MyIntegrationClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
@Slf4j
class FeignResilience4jApplicationTests {

    @Autowired
    private MyIntegrationClient myIntegrationClient;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception {
        log.info("=====================INICIANDO===============================");
        stubFor(get(urlEqualTo("/xpto")).willReturn(aResponse().withStatus(500).withFixedDelay(0)
                                                            .withBody("wiremock response :D")));


//        List<CompletableFuture<ResponseEntity<String>>> completableFutureList = new ArrayList<>();
        List<CompletableFuture<String>> completableFutureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> myIntegrationClient.getMyString().orElse(""));

//            CompletableFuture<ResponseEntity<String>> completableFuture = CompletableFuture.supplyAsync(testRestTemplate.exchange(URI.create("/"), HttpMethod.GET, HttpEntity.EMPTY, String.class));
            completableFutureList.add(completableFuture);
        }
//        List<ResponseEntity<String>> responseEntityList = completableFutureList.stream().map(CompletableFuture::join).toList();
        List<String> responseEntityList = completableFutureList.stream().map(CompletableFuture::join).toList();
        System.out.println("==================================================================");
        System.out.println(responseEntityList);
    }


}
