package refer.spring.boot.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import refer.spring.boot.auth.domain.Account;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    private final byte[] key;

    public JwtServiceImpl(@Value("${jwt.secret}") String secret) {
        this.key = secret.getBytes();
    }

    @Override
    public String readTokenSubject(String token) {
        return extractTokenClaim(token, Claims::getSubject);
    }

    @Override
    public Date readTokenExpiration(String token) {
        return extractTokenClaim(token, Claims::getExpiration);
    }

    private <X> X extractTokenClaim(String token, Function<Claims, X> extraction) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        return extraction.apply(claims);
    }

    @Override
    public String createToken(Account account) {
        return Jwts.builder()
                .setSubject(account.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, Account account) {
        String username = account.getUsername();

        return username.equals(readTokenSubject(token)) && Date.from(Instant.now()).before(readTokenExpiration(token));
    }
}
