package payment_subscription_management_system.demo.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import payment_subscription_management_system.demo.dto.CreatePlanRequestDTO;
import payment_subscription_management_system.demo.dto.CreatePlanResponseDTO;
import payment_subscription_management_system.demo.entity.Plan;
import payment_subscription_management_system.demo.entity.SubscriptionPlan;
import payment_subscription_management_system.demo.repository.PlanRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@PreAuthorize("hasRole('ADMIN')")
public class PlanService {
    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public CreatePlanResponseDTO createPlan(CreatePlanRequestDTO dto){
        Plan plan = new Plan();
        plan.setName(dto.getName());
        plan.setPrice(dto.getPrice());
        plan.setBillingPeriod(dto.getBillingPeriod());

        planRepository.save(plan);
        return CreatePlanResponseDTO.builder()
                .id(plan.getId())
                .subscriptionName(plan.getName().toString())
                .price(plan.getPrice())
                .billingPeriod(plan.getBillingPeriod().toString())
                .build();
    }

    public CreatePlanResponseDTO deactivatePlan(UUID planId){
        if(planRepository.findById(planId).isEmpty()){
            throw new RuntimeException("Plan not found! " + planId);
        }
        Plan plan = planRepository.findById(planId).get();
        plan.setActive(true);
        planRepository.save(plan);
        return CreatePlanResponseDTO.builder()
                .id(plan.getId())
                .subscriptionName(plan.getName().toString())
                .price(plan.getPrice())
                .isActive(plan.isActive())
                .billingPeriod(plan.getBillingPeriod().toString())
                .build();
    }

    public List<Plan> getActivePlans(){
//        return planRepository.findAll().stream()
//                .filter(Plan::isActive).collect(Collectors.toList());
        return planRepository.findByActiveTrue();
    }

    public CreatePlanResponseDTO getPlanById(UUID planId){
        return planRepository.findById(planId).map(plan -> CreatePlanResponseDTO.builder()
                .id(plan.getId())
                .subscriptionName(plan.getName().toString())
                .price(plan.getPrice())
                .isActive(plan.isActive())
                .billingPeriod(plan.getBillingPeriod().toString())
                .build()).orElseThrow(() -> new RuntimeException("Plan not found! " + planId));
    }
}
