package refer.spring.boot.auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public Optional<String> findOwnAccountUsername() {
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
}
