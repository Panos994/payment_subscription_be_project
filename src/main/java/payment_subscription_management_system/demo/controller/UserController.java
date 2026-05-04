package payment_subscription_management_system.demo.controller;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payment_subscription_management_system.demo.dto.AssignRoleDTO;
import payment_subscription_management_system.demo.dto.CreateUserRequestDTO;
import payment_subscription_management_system.demo.dto.CreateUserResponseDTO;
import payment_subscription_management_system.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponseDTO> register(@RequestBody CreateUserRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }
    @PostMapping("/assign")
    public ResponseEntity<Void> assignRoleToUser(@RequestBody AssignRoleDTO dto){
        userService.assignRole(dto.getUserId(), dto.getRole());
        return ResponseEntity.ok().build();
    }
}
