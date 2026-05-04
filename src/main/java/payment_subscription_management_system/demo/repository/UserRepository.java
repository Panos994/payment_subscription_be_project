package payment_subscription_management_system.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import payment_subscription_management_system.demo.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

}
