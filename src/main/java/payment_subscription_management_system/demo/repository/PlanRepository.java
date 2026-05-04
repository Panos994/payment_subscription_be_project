package payment_subscription_management_system.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import payment_subscription_management_system.demo.entity.Plan;
import payment_subscription_management_system.demo.entity.SubscriptionPlan;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlanRepository extends JpaRepository<Plan, UUID> {
    Optional<Plan> findByName(SubscriptionPlan name);
    Page<Plan> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    List<Plan> findByActiveTrue();



}
