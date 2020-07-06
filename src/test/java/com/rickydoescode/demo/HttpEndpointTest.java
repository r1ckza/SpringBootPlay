package com.rickydoescode.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static com.rickydoescode.demo.controller.CustomerController.GREETING_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpEndpointTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String LOCALHOST = "http://localhost:";

    @Test
    public void defaultMessage() throws Exception {
        assertThat(restTemplate.getForObject(LOCALHOST + port + "/", String.class)).contains(GREETING_MESSAGE);
    }
}
