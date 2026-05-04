package payment_subscription_management_system.demo.dto;

import lombok.*;
import payment_subscription_management_system.demo.entity.SubscriptionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSubscriptionResponseDTO {
    private UUID planId;
    private UUID userId;
    private UUID subscriptionId;
    private SubscriptionStatus status;
    private LocalDateTime nextBillingDate;
}
