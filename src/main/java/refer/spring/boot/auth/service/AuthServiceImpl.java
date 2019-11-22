package refer.spring.boot.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refer.spring.boot.auth.domain.Account;
import refer.spring.boot.auth.domain.AccountNotFoundException;
import refer.spring.boot.auth.domain.AuthException;

import java.util.Optional;

@Transactional
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final AccountService accountService;
    private final JwtService jwtService;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           AccountService accountService,
                           JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.accountService = accountService;

        this.jwtService = jwtService;
    }

    @Override
    public Optional<String> findAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = getPrincipal(authentication);
        if (userDetails == null) {
            return Optional.empty();
        }

        return Optional.of(userDetails.getUsername());
    }

    private UserDetails getPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return (UserDetails) principal;
        }

        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    @Override
    public void authenticate(String username, String password) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(authentication));
    }

    @Override
    public void authenticate(String token) {
        if (token == null) {
            throw new AuthException("No token provided");
        }

        String tokenSubject = jwtService.readTokenSubject(token);
        if (tokenSubject == null) {
            throw new AuthException("Invalid token provided");
        }

        Account account = accountService.findAccount(tokenSubject)
                .orElseThrow(() ->
                        new AccountNotFoundException(String.format("No account found for username: %s", tokenSubject)));

        if (!jwtService.isTokenValid(token, account)) {
            throw new AuthException("Invalid token provided");
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities());
        authentication.setDetails(account);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
