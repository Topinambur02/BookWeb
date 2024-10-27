package com.example.bookweb.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

import com.example.dto.kafka.KafkaEmailMessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailConsumerServiceIT {

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JavaMailSender javaMailSender;
    @Autowired
    private KafkaTemplate<String, String> template;
    @Container
    private static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @BeforeAll
    public static void setUp() {
        kafka.start();
        System.setProperty("spring.kafka.bootstrap-servers", kafka.getBootstrapServers());
    }

    @AfterAll
    public static void tearDown() {
        kafka.stop();
    }

    @Test
    void testConsume() throws Exception {
        final var dto = KafkaEmailMessageDto
                .builder()
                .to("test")
                .text("test")
                .build();
        final var json = objectMapper.writeValueAsString(dto);

        template.send("email-message", json);

        await().atMost(20, SECONDS).untilAsserted(() -> {
            Mockito
                    .verify(javaMailSender, Mockito.times(1))
                    .send(Mockito.any(SimpleMailMessage.class));
        });

    }

}
