package payment_subscription_management_system.demo.dto;

import lombok.*;
import payment_subscription_management_system.demo.entity.BillingPeriod;
import payment_subscription_management_system.demo.entity.SubscriptionPlan;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePlanRequestDTO {
    private BigDecimal price;
    private SubscriptionPlan name;
    private BillingPeriod billingPeriod;
}
