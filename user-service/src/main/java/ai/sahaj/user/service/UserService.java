package ai.sahaj.user.service;

import ai.sahaj.user.dto.UserPlanResponse;
import ai.sahaj.user.entity.User;
import ai.sahaj.user.exception.UserNotFoundException;
import ai.sahaj.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class UserService {
  private static final int PAGE_SIZE = 10;
  private final UserRepository userRepository;

  private final RestTemplate restTemplate;

  private final String userPlanServiceUrl;

  public UserService(final UserRepository userRepository, final RestTemplate restTemplate,
                     @Value("${api.subscription-service.base.url}") final String userPlanServiceUrl) {
    this.userRepository = userRepository;
    this.restTemplate = restTemplate;
    this.userPlanServiceUrl = userPlanServiceUrl;
  }

  public User getUserDetails(final Integer userId) {
    return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found" + userId + " not found"));
  }

  public UserPlanResponse getUserPlanDetails(final Integer userId) {
    log.info("Get plan details for user: {}", userId);
    return restTemplate.getForObject(userPlanServiceUrl + userId, UserPlanResponse.class);
  }

  public List<UserPlanResponse> getUserPlanReport() {
    return Stream
      .iterate(0, page -> page + 1)
      .map(page -> userRepository.findAll(PageRequest.of(page, PAGE_SIZE)))
      .takeWhile(users -> !users.isEmpty())
      .flatMap(Streamable::stream)
      .map(user -> getUserPlanDetails(user.getId()))
      .toList();
  }
}
