package payment_subscription_management_system.demo.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponseDTO {
    private BigDecimal amount;
    private UUID transactionRef;
    private String status;
}
