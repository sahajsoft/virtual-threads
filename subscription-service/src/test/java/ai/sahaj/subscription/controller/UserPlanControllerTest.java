package ai.sahaj.subscription.controller;

import ai.sahaj.subscription.entity.Plan;
import ai.sahaj.subscription.entity.UserPlan;
import ai.sahaj.subscription.repository.PlanRepository;
import ai.sahaj.subscription.repository.UserPlanRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserPlanControllerTest {
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

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @Autowired
  private UserPlanRepository userPlanRepository;
  @Autowired
  private PlanRepository planRepository;
  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    planRepository.deleteAll();
    Plan plan1 = planRepository.save(new Plan("Test1", BigDecimal.valueOf(100), 100));
    Plan plan2 = planRepository.save(new Plan("Test2", BigDecimal.valueOf(100), 100));

    userPlanRepository.deleteAll();
    UserPlan userPlan1 = new UserPlan(plan1.getId(), 1L, BigDecimal.valueOf(12), LocalDate.now());
    UserPlan userPlan2 = new UserPlan(plan2.getId(), 3L, BigDecimal.valueOf(85), LocalDate.now());
    userPlanRepository.saveAll(List.of(userPlan1, userPlan2));
  }

  @Test
  void shouldGetDetailsOfUserPlanForProvidedUserId() throws Exception {
    Plan plan = planRepository.findAll()
      .stream().filter(it -> it.getName().equals("Test1"))
      .findFirst().get();

    mockMvc.perform(get("/user-plan/1"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.userId").value(1L))
      .andExpect(jsonPath("$.planId").value(plan.getId()))
      .andExpect(jsonPath("$.planName").value("Test1"))
      .andExpect(jsonPath("$.dataBalance").value(BigDecimal.valueOf(88).setScale(1)));
  }

  @Test
  void shouldGive404WhenUserPlanNotFoundForUser() throws Exception {
    mockMvc.perform(get("/user-plan/100000"))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.message").value("Plan for user 100000 not found"));
  }

  @Test
  void shouldGive404WhenUserPlanNotFound() throws Exception {
    UserPlan userPlan = new UserPlan(1000000, 4L, BigDecimal.valueOf(85), LocalDate.now());
    userPlanRepository.save(userPlan);

    mockMvc.perform(get("/user-plan/4"))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.message").value("Plan with id 1000000 not found"));
  }
}
