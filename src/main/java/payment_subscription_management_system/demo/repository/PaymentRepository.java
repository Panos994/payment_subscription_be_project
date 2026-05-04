package payment_subscription_management_system.demo.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import payment_subscription_management_system.demo.entity.Payment;
import payment_subscription_management_system.demo.entity.PaymentStatus;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findBySubscription_Id(UUID subscriptionId);
    List<Payment> findByStatus(PaymentStatus status);
    Page<Payment> findBySubscription_Id(UUID subscriptionId, Pageable pageable);
}
