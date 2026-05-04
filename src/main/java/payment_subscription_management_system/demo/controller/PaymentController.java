package payment_subscription_management_system.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payment_subscription_management_system.demo.dto.InvoiceResponseDTO;
import payment_subscription_management_system.demo.dto.PaymentResponseDTO;
import payment_subscription_management_system.demo.entity.Invoice;
import payment_subscription_management_system.demo.entity.Payment;
import payment_subscription_management_system.demo.service.PaymentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{subscriptionId}")
    public ResponseEntity<PaymentResponseDTO> createPaymentAttemp(@PathVariable UUID subscriptionId){
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.createPaymentAttempt(subscriptionId));
    }
    @PatchMapping("{paymentId}/complete")
    public ResponseEntity<PaymentResponseDTO> completePayment(@PathVariable UUID paymentId){
        return ResponseEntity.ok().body(mapToResponse(paymentService.markAsSuccess(paymentId)));
    }

    @PatchMapping("{paymentId}/failed")
    public ResponseEntity<PaymentResponseDTO> failedPayment(@PathVariable UUID paymentId){
        return ResponseEntity.ok().body(mapToResponse(paymentService.markAsFailed(paymentId)));
    }
    
    @GetMapping("/{subscriptionId}")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsBySubscriptionId(@PathVariable UUID subscriptionId){
        return ResponseEntity.ok().body(paymentService.getPaymentsBySubscriptionId(subscriptionId));
    }

    private PaymentResponseDTO mapToResponse(Payment payment){
        return  PaymentResponseDTO.builder()
                .status(payment.getStatus().toString())
                .amount(payment.getAmount())
                .transactionRef(payment.getTransactionRef())
                .build();
    }

}
