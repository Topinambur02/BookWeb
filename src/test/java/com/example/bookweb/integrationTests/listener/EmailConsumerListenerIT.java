package com.example.bookweb.integrationTests.listener;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

import com.example.config.JwtConfig;
import com.example.dto.kafka.KafkaEmailMessageDto;
import com.example.dto.rest.EmailMessageDto;
import com.example.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class EmailConsumerListenerIT {

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private EmailService emailService;
    @MockBean
    private JwtConfig config;
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
                    .verify(emailService, Mockito.times(1))
                    .sendMessage(Mockito.any(EmailMessageDto.class));
        });

    }

}
