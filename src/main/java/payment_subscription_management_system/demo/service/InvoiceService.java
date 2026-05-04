package payment_subscription_management_system.demo.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import payment_subscription_management_system.demo.dto.InvoiceResponseDTO;
import payment_subscription_management_system.demo.entity.Invoice;
import payment_subscription_management_system.demo.entity.InvoiceStatus;
import payment_subscription_management_system.demo.entity.Subscription;
import payment_subscription_management_system.demo.entity.SubscriptionStatus;
import payment_subscription_management_system.demo.repository.InvoiceRepository;
import payment_subscription_management_system.demo.repository.SubscriptionRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final SubscriptionRepository subscriptionRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, SubscriptionRepository subscriptionRepository) {
        this.invoiceRepository = invoiceRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Transactional
    public Invoice generateInvoice(UUID subscriptionId){
        if(invoiceRepository.existsBySubscription_IdAndStatus(subscriptionId, InvoiceStatus.ISSUED)){
            throw new IllegalArgumentException("Invoice already exists for subscription with id: " + subscriptionId);
        }
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(() -> new IllegalArgumentException("Subscription not found with id: " + subscriptionId));
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber("INV-" + subscriptionId);
        invoice.setSubscription(subscription);
        invoice.setTotalAmount(subscription.getPlan().getPrice());
        invoice.setIssuedAt(LocalDateTime.now());
        invoice.setStatus(InvoiceStatus.ISSUED);

        return invoiceRepository.save(invoice);
    }

    @Transactional
    public Invoice generateInvoiceSc(UUID subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));

        return generateInvoice(subscription.getId());
    }

    public void markInvoicePaid(UUID invoiceId){
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new IllegalArgumentException("Invoice not found with id: " + invoiceId));
        invoice.setStatus(InvoiceStatus.PAID);
        invoiceRepository.save(invoice);
    }
    public Page<Invoice> getInvoicesBySubscription(UUID subscriptionId, Pageable pageable){
        return invoiceRepository.findBySubscription_Id(subscriptionId, pageable);
    }
}
