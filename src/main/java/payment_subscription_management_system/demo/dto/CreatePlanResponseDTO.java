package payment_subscription_management_system.demo.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePlanResponseDTO {
    private UUID id;
    private BigDecimal price;
    private String billingPeriod; //BillingPeriod name
    private boolean isActive;
    private String subscriptionName; //SubscriptionPlan name
    private LocalDateTime createdAt;


}
