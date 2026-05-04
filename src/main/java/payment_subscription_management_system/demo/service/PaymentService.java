package payment_subscription_management_system.demo.service;

import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import payment_subscription_management_system.demo.dto.PaymentResponseDTO;
import payment_subscription_management_system.demo.entity.Payment;
import payment_subscription_management_system.demo.entity.PaymentStatus;
import payment_subscription_management_system.demo.entity.Subscription;
import payment_subscription_management_system.demo.repository.PaymentRepository;
import payment_subscription_management_system.demo.repository.SubscriptionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final SubscriptionRepository subscriptionRepository;

    public PaymentService(PaymentRepository paymentRepository, SubscriptionRepository subscriptionRepository) {
        this.paymentRepository = paymentRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public PaymentResponseDTO createPaymentAttempt(UUID subscriptionId){
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(() -> new IllegalArgumentException("Subscription not found with id: " + subscriptionId));
        Payment payment = new Payment();
        payment.setSubscription((subscription));
        payment.setAmount(subscription.getPlan().getPrice());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setTransactionRef(UUID.randomUUID());
        payment.setCreatedAt(LocalDateTime.now());

        paymentRepository.save(payment);

        return PaymentResponseDTO.builder()
                .amount(payment.getAmount())
                .transactionRef(payment.getTransactionRef())
                .status(PaymentStatus.PENDING.toString())
                .build();
    }

    @Transactional
    public Payment createPaymentAttemptInternal(UUID subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));

        Payment payment = new Payment();
        payment.setSubscription(subscription);
        payment.setAmount(subscription.getPlan().getPrice());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setTransactionRef(UUID.randomUUID());
        payment.setCreatedAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }


    public Payment markAsSuccess(UUID paymentId){

        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new IllegalArgumentException("Payment not found with id: " + paymentId));
        if(payment.getStatus() != PaymentStatus.PENDING){
            throw new IllegalArgumentException("Payment already finalized");
        }
        payment.setStatus(PaymentStatus.SUCCESS);
        return paymentRepository.save(payment);
    }
    public Payment markAsFailed(UUID paymentId){
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new IllegalArgumentException("Payment not found with id: " + paymentId));
        payment.setStatus(PaymentStatus.FAILED);
        return paymentRepository.save(payment);
    }
//    public List<Payment> getPaymentsBySubscriptionId(UUID subscriptionId){
//        return paymentRepository.findBySubscription_Id(subscriptionId);
//    }
    public List<PaymentResponseDTO> getPaymentsBySubscriptionId(UUID subscriptionId){
        return paymentRepository.findBySubscription_Id(subscriptionId).stream()
                .map(p -> PaymentResponseDTO.builder()
                        .status(p.getStatus().toString())
                        .amount(p.getAmount())
                        .transactionRef(p.getTransactionRef())
                        .build())
                .toList();
    }

}
