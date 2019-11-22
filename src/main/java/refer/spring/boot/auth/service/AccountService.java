package refer.spring.boot.auth.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import refer.spring.boot.auth.domain.Account;

import java.util.Optional;

public interface AccountService extends UserDetailsService {

    Optional<Account> findAccount(Long id);

    Optional<Account> findAccount(String username);

    Account saveAccount(Account account);
}
