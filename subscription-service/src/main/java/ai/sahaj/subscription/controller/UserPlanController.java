package ai.sahaj.subscription.controller;

import ai.sahaj.subscription.dto.UserPlanResponse;
import ai.sahaj.subscription.service.UserPlanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-plan")
public class UserPlanController {
  private final UserPlanService userPlanService;

  public UserPlanController(UserPlanService userPlanService) {
    this.userPlanService = userPlanService;
  }


  @GetMapping("/{userId}")
  public UserPlanResponse getUserPlanDetailsForUserId(@PathVariable("userId") Integer userId) throws InterruptedException {
    return UserPlanResponse.from(userPlanService.getUserPlanDetails(userId));
  }


}
