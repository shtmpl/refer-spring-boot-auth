package refer.spring.boot.auth.service;

import refer.spring.boot.auth.domain.Account;

import java.util.Optional;

public interface AccountService {

    Optional<Account> findAccount(Long id);

    Optional<Account> findAccount(String username);

    Account saveAccount(Account account);
}
