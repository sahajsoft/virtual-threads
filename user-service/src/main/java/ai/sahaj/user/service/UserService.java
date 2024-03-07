package ai.sahaj.user.service;

import ai.sahaj.user.dto.UserPlanResponse;
import ai.sahaj.user.entity.User;
import ai.sahaj.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
  private final UserRepository userRepository;

  private final RestTemplate restTemplate;

  private final String userPlanServiceUrl;

  public UserService(final UserRepository userRepository, final RestTemplate restTemplate,
                     @Value("api.subscription-service.base.url") final String userPlanServiceUrl) {
    this.userRepository = userRepository;
    this.restTemplate = restTemplate;
    this.userPlanServiceUrl = userPlanServiceUrl;
  }

  public User getUserDetails(final Integer userId) {
    return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
  }

  public UserPlanResponse getUserPlanDetails(final Integer userId) {
    validateUser(userId);
    return restTemplate.getForObject(userPlanServiceUrl + userId, UserPlanResponse.class);
  }

  private void validateUser(final Integer userId) {
    if (userRepository.findById(userId).isEmpty()) {
      throw new RuntimeException("User not found");
    }
  }
}
