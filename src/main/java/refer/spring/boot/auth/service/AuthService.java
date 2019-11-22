package refer.spring.boot.auth.service;

import java.util.Optional;

public interface AuthService {

    Optional<String> findAuthenticatedUsername();

    boolean isAuthenticated();

    void authenticate(String username, String password);

    void authenticate(String token);
}
