package payment_subscription_management_system.demo.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSubscriptionRequestDTO {
    private UUID planId;
}
