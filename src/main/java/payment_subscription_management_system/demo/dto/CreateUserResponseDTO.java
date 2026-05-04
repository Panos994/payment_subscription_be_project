package payment_subscription_management_system.demo.dto;

import lombok.*;
import payment_subscription_management_system.demo.entity.Role;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserResponseDTO {
    private UUID id;
    private String email;
    private Role role;
}
