package payment_subscription_management_system.demo.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import payment_subscription_management_system.demo.dto.CreatePlanResponseDTO;
import payment_subscription_management_system.demo.dto.CreateSubscriptionRequestDTO;
import payment_subscription_management_system.demo.dto.CreateSubscriptionResponseDTO;
import payment_subscription_management_system.demo.entity.*;
import payment_subscription_management_system.demo.repository.PlanRepository;
import payment_subscription_management_system.demo.repository.SubscriptionRepository;
import payment_subscription_management_system.demo.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, PlanRepository planRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
        this.userRepository = userRepository;
    }


    public CreateSubscriptionResponseDTO createSubscription(CreateSubscriptionRequestDTO dto, UUID userId){
         Plan planId = planRepository.findById(dto.getPlanId()).orElseThrow(() -> new IllegalArgumentException("Plan not found with id: " + dto.getPlanId()));
         Subscription subscription = new Subscription();
         User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
         subscription.setPlan(planId);
         subscription.setUser(user);
         subscription.setStatus(SubscriptionStatus.ACTIVE);
         subscription.setStartDate(LocalDateTime.now());
         subscription.setNextBillingDate(LocalDateTime.now().plusMonths(1)); // Assuming monthly billing cycle
         subscription.setAutoRenew(true);
         Subscription savedSubscription = subscriptionRepository.save(subscription);

         return CreateSubscriptionResponseDTO.builder()
                 .subscriptionId(savedSubscription.getId())
                 .planId(savedSubscription.getPlan().getId())
                 .status(savedSubscription.getStatus())
                 .nextBillingDate(savedSubscription.getNextBillingDate())
                 .build();
    }

    public CreateSubscriptionResponseDTO cancelSubscription(UUID subscriptionId){
        if(subscriptionRepository.findById(subscriptionId).isEmpty()){
            throw new IllegalArgumentException("Subscription not found with id: " + subscriptionId);
        }
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(() -> new IllegalArgumentException("Subscription not found with id: " + subscriptionId));
        if(subscription.getStatus() != SubscriptionStatus.ACTIVE){
            throw new IllegalArgumentException("Only active subscriptions can be cancelled");
        }

        subscription.setStatus(SubscriptionStatus.CANCELLED);
        subscription.setAutoRenew(false);
        subscriptionRepository.save(subscription);
        return CreateSubscriptionResponseDTO.builder()
                .subscriptionId(subscription.getId())
                .planId(subscription.getPlan().getId())
                .status(subscription.getStatus())
                .nextBillingDate(subscription.getNextBillingDate())
                .build();

    }

    public List<Subscription> getUserSubscriptions(UUID userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        return subscriptionRepository.findByUser_Id(user.getId());
    }

    public  List<Subscription> findSubscriptionsDueForBilling(){     // this needs scheduler to run every day and check for subscriptions that are due for billing
        return subscriptionRepository.findByStatusAndNextBillingDateBefore(SubscriptionStatus.ACTIVE, LocalDateTime.now());
    }


    @Transactional
    public void advanceBillingCycle(Subscription subscription) {

        if (!subscription.isAutoRenew()) {
            subscription.setStatus(SubscriptionStatus.EXPIRED);
            return;
        }

        subscription.setNextBillingDate(
                calculateNextBillingDate(
                        subscription.getNextBillingDate(),
                        subscription.getPlan().getBillingPeriod()
                )
        );
    }

    @Transactional
    public void handleFailedPayment(Subscription subscription) {

        // v1: immediate expire
        subscription.setStatus(SubscriptionStatus.EXPIRED);

        // v2 (future):
        // increment retryCount
        // if retryCount >= max → EXPIRED
    }


    private LocalDateTime calculateNextBillingDate(
            LocalDateTime currentBillingDate,
            BillingPeriod billingPeriod
    ) {
        return switch (billingPeriod) {
            case MONTHLY -> currentBillingDate.plusMonths(1);
            case YEARLY -> currentBillingDate.plusYears(1);
        };
    }



}
