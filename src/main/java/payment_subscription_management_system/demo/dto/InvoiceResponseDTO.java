package payment_subscription_management_system.demo.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceResponseDTO {
    private String invoiceNumber;
    private String status;
    private BigDecimal totalAmount;
}
