package refer.spring.boot.auth.service;

import java.util.Optional;

public interface AuthService {

    Optional<String> findOwnAccountUsername();
}
