package ai.sahaj.user.service;

import ai.sahaj.user.dto.UserPlanResponse;
import ai.sahaj.user.entity.User;
import ai.sahaj.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  private UserService service;

  @Mock
  private UserRepository userRepository;

  @Mock
  private RestTemplate restTemplate;

  @BeforeEach
  void setUp() {
    // given
    service = new UserService(userRepository, restTemplate, "http://localhost:8080/user-plan/");
  }

  @Test
  void shouldThrowExceptionIfUserIsNotFound() {
    Integer userId = 1;
    assertThrows(RuntimeException.class, () -> service.getUserDetails(userId));
  }

  @Test
  void shouldReturnUserIdIfUserIsValid() {
    Integer userId = 1;
    User expectedUser = new User(1, "John");
    when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));
    // when
    User userDetails = service.getUserDetails(userId);
    // then
    assertEquals(expectedUser, userDetails);
  }

  @Test
  void shouldReturnUserPlanDetailsForGivenUserId() {
    Integer userId = 1;
    UserPlanResponse expectedUserPlanDetails = new UserPlanResponse(1, 1, "Daily Subscription",
      BigDecimal.valueOf(100.0), LocalDate.now());
    when(userRepository.findById(userId)).thenReturn(Optional.of(new User(1, "John")));
    when(restTemplate.getForObject("http://localhost:8080/user-plan/" + userId, UserPlanResponse.class))
      .thenReturn(expectedUserPlanDetails);
    // when
    UserPlanResponse response = service.getUserPlanDetails(userId);
    // then
    assertEquals(expectedUserPlanDetails, response);
  }

  @Test
  void shouldNotReturnUserPlanDetailsIfUserIdIsInvalid() {
    Integer userId = 1;
    when(userRepository.findById(userId)).thenReturn(Optional.empty());
    // when
    assertThrows(RuntimeException.class, () -> service.getUserPlanDetails(userId));
  }

  @Test
  void shouldReturnUserPlanDetailsForAllUsers() {
    Integer userId = 1;
    UserPlanResponse expectedUserPlanDetails1 = new UserPlanResponse(1, 1, "Daily Subscription",
      BigDecimal.valueOf(100.0), LocalDate.now());
    UserPlanResponse expectedUserPlanDetails2 = new UserPlanResponse(1, 1, "Daily Subscription",
      BigDecimal.valueOf(20.0), LocalDate.now());
    when(userRepository.findAll(PageRequest.of(0, 10)))
      .thenReturn(new PageImpl<>(List.of(new User(1, "John"), new User(2, "Cena "))));
    when(userRepository.findAll(PageRequest.of(1, 10)))
      .thenReturn(new PageImpl<>(List.of()));
    when(restTemplate.getForObject("http://localhost:8080/user-plan/" + userId, UserPlanResponse.class))
      .thenReturn(expectedUserPlanDetails1);
    when(restTemplate.getForObject("http://localhost:8080/user-plan/" + 2, UserPlanResponse.class))
      .thenReturn(expectedUserPlanDetails2);
    // when
    List<UserPlanResponse> response = service.getUserPlanReport();
    // then
    assertTrue(response.containsAll(List.of(expectedUserPlanDetails1, expectedUserPlanDetails2)));
  }
}