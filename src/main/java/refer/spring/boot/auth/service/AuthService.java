package refer.spring.boot.auth.service;

import java.util.Optional;

public interface AuthService {

    Optional<String> findOwnAccountUsername();

    boolean isAuthenticated();

    void auth(String username, String password);

    void auth(String token);
}
