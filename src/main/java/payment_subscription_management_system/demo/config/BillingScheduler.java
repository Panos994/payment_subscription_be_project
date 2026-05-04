package payment_subscription_management_system.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import payment_subscription_management_system.demo.entity.Invoice;
import payment_subscription_management_system.demo.entity.Payment;
import payment_subscription_management_system.demo.entity.Subscription;
import payment_subscription_management_system.demo.service.InvoiceService;
import payment_subscription_management_system.demo.service.PaymentService;
import payment_subscription_management_system.demo.service.SubscriptionService;

import java.util.List;

@Component
@Slf4j
public class BillingScheduler {

    private final SubscriptionService subscriptionService;
    private final InvoiceService invoiceService;
    private final PaymentService paymentService;


    public BillingScheduler(SubscriptionService subscriptionService, InvoiceService invoiceService, PaymentService paymentService) {
        this.subscriptionService = subscriptionService;
        this.invoiceService = invoiceService;
        this.paymentService = paymentService;
    }

    /**
     * Runs every hour.
     * In real production this could be every night.
     */
    @Scheduled(cron = "0 0 * * * *") //we can change this cron expression if we want
    public void processBillingCycle(){
        List<Subscription> dueSubscriptions = subscriptionService.findSubscriptionsDueForBilling();
        for(Subscription subscription : dueSubscriptions){
            handleSubscriptionBilling(subscription);
        }
    }

    /**
     * Handles ONE subscription billing.
     * This method should NEVER throw unchecked exceptions
     * that kill the scheduler for other subscriptions.
     */
    private void handleSubscriptionBilling(Subscription subscription) {

        try {
            // 1. Generate invoice
            Invoice invoice = invoiceService.generateInvoice(subscription.getId());

            // 2. Create payment attempt
            Payment payment = paymentService.createPaymentAttemptInternal(subscription.getId());

            // 3. Simulate payment gateway result (for now)
            boolean paymentSuccess = simulatePaymentResult();

            if (paymentSuccess) {
                paymentService.markAsSuccess(payment.getId());
                invoiceService.markInvoicePaid(invoice.getId());
                subscriptionService.advanceBillingCycle(subscription);

            } else {
                paymentService.markAsFailed(payment.getId());
                subscriptionService.handleFailedPayment(subscription);
            }

        } catch (Exception ex) {
            // VERY important: never break the scheduler
            // log error and continue
            log.error("Billing failed for subscription {}",
                    subscription.getId(), ex);
        }
    }

    /**
     * Temporary simulation.
     * Later can be replaced with real payment gateway / webhook.
     */
    private boolean simulatePaymentResult() {
        return Math.random() > 0.2; // 80% success
    }


}
