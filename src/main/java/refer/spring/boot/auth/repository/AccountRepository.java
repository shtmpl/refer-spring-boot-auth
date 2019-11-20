package refer.spring.boot.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import refer.spring.boot.auth.domain.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);
}
