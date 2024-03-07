package ai.sahaj.user.controller;

import ai.sahaj.user.dto.UserPlanResponse;
import ai.sahaj.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-plan")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(final UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{userId}")
  public UserPlanResponse getUserPlanDetails(@PathVariable("userId") final Integer userId) {
    return userService.getUserPlanDetails(userId);
  }
}
