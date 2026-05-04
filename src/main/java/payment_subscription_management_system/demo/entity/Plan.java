package payment_subscription_management_system.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "plans")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    @Enumerated(EnumType.STRING)
    private SubscriptionPlan name;

    @Column
    private BigDecimal price;

    @Column
    @Enumerated(EnumType.STRING)
    private BillingPeriod billingPeriod;

    @Column
    private boolean isActive;

    @Column
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<Subscription> subscriptions;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}
