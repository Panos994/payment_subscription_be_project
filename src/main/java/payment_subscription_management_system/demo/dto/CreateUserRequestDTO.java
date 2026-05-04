package payment_subscription_management_system.demo.dto;

import lombok.*;
import payment_subscription_management_system.demo.entity.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequestDTO {
    private String email;
    private String password;
    private Role role;
}
