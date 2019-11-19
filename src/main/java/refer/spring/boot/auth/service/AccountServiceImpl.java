package refer.spring.boot.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Optional<Account> findAccount(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<Account> findOwnAccount() {
        return Optional.empty();
    }

    @Override
    public Account saveAccount(Account account) {
        Account result = new Account();
        result.setCreatedAt(OffsetDateTime.now());
        result.setUsername(account.getUsername());
        result.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));

        return accountRepository.save(result);
    }
}
