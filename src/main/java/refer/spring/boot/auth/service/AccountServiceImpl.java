package refer.spring.boot.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refer.spring.boot.auth.domain.Account;
import refer.spring.boot.auth.repository.AccountRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Transactional
@Service
public class AccountServiceImpl implements AccountService {

    private final PasswordEncoder passwordEncoder;

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(PasswordEncoder passwordEncoder,
                              AccountRepository accountRepository) {
        this.passwordEncoder = passwordEncoder;

        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findAccount(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("No username found: %s", username)));
    }

    @Override
    public Optional<Account> findAccount(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<Account> findAccount(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Account saveAccount(Account account) {
        Account result = new Account();
        result.setCreatedAt(OffsetDateTime.now());
        result.setUsername(account.getUsername());
        result.setPassword(passwordEncoder.encode(account.getPassword()));

        return accountRepository.save(result);
    }
}
