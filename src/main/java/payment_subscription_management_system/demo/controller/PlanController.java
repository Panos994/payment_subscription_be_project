package payment_subscription_management_system.demo.controller;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payment_subscription_management_system.demo.dto.CreatePlanRequestDTO;
import payment_subscription_management_system.demo.dto.CreatePlanResponseDTO;
import payment_subscription_management_system.demo.dto.CreateUserResponseDTO;
import payment_subscription_management_system.demo.dto.PaymentResponseDTO;
import payment_subscription_management_system.demo.entity.Payment;
import payment_subscription_management_system.demo.entity.Plan;
import payment_subscription_management_system.demo.service.PlanService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/plans")
public class PlanController {
    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping
    public ResponseEntity<CreatePlanResponseDTO> createPlan(@RequestBody CreatePlanRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(planService.createPlan(dto));
    }

    @PatchMapping("/{planId}/deactivate}")
    public ResponseEntity<CreatePlanResponseDTO> deactivatePlan(@PathVariable UUID planId){
        CreatePlanResponseDTO responseDTO = planService.deactivatePlan(planId);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/active")
    public ResponseEntity<List<CreatePlanResponseDTO>> getActivePlans(){
        List<Plan> plans = planService.getActivePlans();
        return ResponseEntity.status(HttpStatus.OK).body(mapToResponse(plans));
    }

    @GetMapping("/{planId}")
    public ResponseEntity<CreatePlanResponseDTO> getPlanById(@PathVariable UUID planId){
        return ResponseEntity.status(HttpStatus.OK).body(planService.getPlanById(planId));
    }
    private CreatePlanResponseDTO mapToResponse(Plan plan){
        return  CreatePlanResponseDTO.builder()
                .id(plan.getId())
                .price(plan.getPrice())
                .isActive(plan.isActive())
                .billingPeriod(plan.getBillingPeriod().toString())
                .createdAt(plan.getCreatedAt())
                .subscriptionName(plan.getName().toString())
                .build();
    }

    private List<CreatePlanResponseDTO> mapToResponse(List<Plan> plans){
        return plans.stream()
                .map(this::mapToResponse)
                .toList();
    }
}
