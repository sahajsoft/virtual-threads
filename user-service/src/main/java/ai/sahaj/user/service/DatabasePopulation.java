package ai.sahaj.user.service;

import ai.sahaj.user.entity.User;
import ai.sahaj.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
public class DatabasePopulation {

  private final UserRepository userRepository;
  private final Faker faker;

  public DatabasePopulation(UserRepository userRepository) {
    this.userRepository = userRepository;
    this.faker = new Faker();
  }

  @PostConstruct
  public void populateUsers() {
    List<User> users = Stream
      .iterate(1, userId -> userId <= 20, userId -> userId + 1)
      .map(userId -> new User(userId, faker.name().fullName()))
      .toList();
    userRepository.saveAll(users);
  }
}
