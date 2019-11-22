package refer.spring.boot.auth.service;

import refer.spring.boot.auth.domain.Account;

import java.time.OffsetDateTime;

public interface JwtService {

    String getTokenSubject(String token);

    OffsetDateTime getTokenIssuedAt(String token);

    OffsetDateTime getTokenExpiration(String token);

    String createToken(Account account);

    boolean isTokenValid(String token, Account account);
}
