package payment_subscription_management_system.demo.dto;

import lombok.*;
import payment_subscription_management_system.demo.entity.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {
    private String token;
    private Role role;
}
