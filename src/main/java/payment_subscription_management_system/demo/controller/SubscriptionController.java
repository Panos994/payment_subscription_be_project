package payment_subscription_management_system.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import payment_subscription_management_system.demo.dto.CreateSubscriptionRequestDTO;
import payment_subscription_management_system.demo.dto.CreateSubscriptionResponseDTO;
import payment_subscription_management_system.demo.entity.Subscription;
import payment_subscription_management_system.demo.security.CustomUserDetails;
import payment_subscription_management_system.demo.service.SubscriptionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<CreateSubscriptionResponseDTO> createSubscription(@RequestBody CreateSubscriptionRequestDTO dto, @AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionService.createSubscription(dto,user.getUser().getId()));
    }

    @PatchMapping("/{subscriptionId}/cancel")
    public ResponseEntity<CreateSubscriptionResponseDTO> cancelSubscription(@PathVariable UUID subscriptionId){
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionService.cancelSubscription(subscriptionId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Subscription>> getUserSubscriptions(@PathVariable UUID userId){

        return ResponseEntity.status(HttpStatus.OK).body(subscriptionService.getUserSubscriptions(userId));
    }
    @GetMapping("/dueForBilling")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Subscription>> getDueForBillingSubscriptions(){
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionService.findSubscriptionsDueForBilling());
    }





}
