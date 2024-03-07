package ai.sahaj.user.controller;

import ai.sahaj.user.entity.User;
import ai.sahaj.user.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.yaml")
class UserControllerTest {

  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
    "postgres:16-alpine"
  );

  @BeforeAll
  static void beforeAll() {
    postgres.start();
  }

  @AfterAll
  static void afterAll() {
    postgres.stop();
  }

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MockMvc mockMvc;

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @Autowired
  private RestTemplate restTemplate;

  private MockRestServiceServer mockServer;
  @BeforeEach
  void setUp() {
    User user1 = new User(1, "John");
    User user2 = new User(2, "Doe");
    userRepository.saveAll(List.of(user1, user2));
    mockServer = MockRestServiceServer.createServer(restTemplate);
  }

  @Test
  void shouldGetPlanDetailsForProvidedUserId() throws Exception {
    setupSuccessfulUserPlanResponse();
    mockMvc.perform(get("/user-plan/1"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.userId").value(1L))
      .andExpect(jsonPath("$.planName").value("Test1"))
      .andExpect(jsonPath("$.dataBalance").value(BigDecimal.valueOf(88.0).setScale(1)));
  }

  private void setupSuccessfulUserPlanResponse() throws URISyntaxException {
    mockServer.expect(ExpectedCount.once(),
      requestTo(new URI("http://localhost:8081/user-plan/1")))
      .andExpect(method(HttpMethod.GET))
      .andRespond(withStatus(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body("{\"userId\":1,\"planId\":1,\"planName\":\"Test1\",\"dataBalance\":88.0,\"startDate\":\"2022-01-01\"}"));
  }
}