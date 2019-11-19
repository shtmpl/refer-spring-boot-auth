package refer.spring.boot.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refer.spring.boot.auth.domain.Account;
import refer.spring.boot.auth.repository.AccountRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Transactional
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> findAccount(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account saveAccount(Account account) {
        Account result = new Account();
        result.setCreatedAt(OffsetDateTime.now());
        result.setUsername(account.getUsername());
        result.setPassword(account.getPassword());

        return accountRepository.save(result);
    }
}
