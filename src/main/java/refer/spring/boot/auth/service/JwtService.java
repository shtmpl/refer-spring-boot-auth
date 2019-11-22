package refer.spring.boot.auth.service;

import refer.spring.boot.auth.domain.Account;

import java.util.Date;

public interface JwtService {

    String readTokenSubject(String token);

    Date readTokenExpiration(String token);

    String createToken(Account account);

    boolean isTokenValid(String token, Account account);
}
