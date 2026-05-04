package payment_subscription_management_system.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import payment_subscription_management_system.demo.entity.Subscription;
import payment_subscription_management_system.demo.entity.SubscriptionStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    List<Subscription> findByUser_Id(UUID userId);
    List<Subscription> findByPlan_Id(UUID planId);
    List<Subscription> findByStatus(SubscriptionStatus status);
    List<Subscription> findByStatusAndNextBillingDateBefore(SubscriptionStatus status, LocalDateTime date);
}
