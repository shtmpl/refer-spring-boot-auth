package refer.spring.boot.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refer.spring.boot.auth.domain.Account;
import refer.spring.boot.auth.repository.AccountRepository;

import java.util.Collection;
import java.util.Collections;

@Transactional
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Collection<GrantedAuthority> DEFAULT_AUTHORITIES = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

    private final AccountRepository accountRepository;

    @Autowired
    public UserDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsername(username)
                .map(this::toUserDetails)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("No username found: %s", username)));
    }

    private UserDetails toUserDetails(Account account) {
        return new User(
                account.getUsername(),
                account.getPassword(),
                DEFAULT_AUTHORITIES);
    }
}
