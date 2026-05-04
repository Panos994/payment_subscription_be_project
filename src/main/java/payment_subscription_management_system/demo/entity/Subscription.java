package payment_subscription_management_system.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="subscriptions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Plan plan;

    @Enumerated(EnumType.STRING)
    @Column
    private SubscriptionStatus status;

    @Column
    private LocalDateTime startDate;
    @Column
    private LocalDateTime endDate;

    @Column
    private LocalDateTime nextBillingDate;

    @Column
    private boolean autoRenew;

    @Column
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "subscription",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Payment> payments;

    @OneToMany(mappedBy = "subscription",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Invoice> invoices;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}
