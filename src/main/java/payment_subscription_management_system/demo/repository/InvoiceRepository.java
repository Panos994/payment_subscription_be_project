package payment_subscription_management_system.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import payment_subscription_management_system.demo.entity.Invoice;
import payment_subscription_management_system.demo.entity.InvoiceStatus;

import java.util.Optional;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    Page<Invoice> findBySubscription_Id(UUID subscriptionId, Pageable pageable);
    Page<Invoice> findBySubscription_IdAndStatus(UUID subscriptionId, InvoiceStatus status, Pageable pageable);

    boolean existsBySubscription_IdAndStatus(UUID subscriptionId, InvoiceStatus status);
}
