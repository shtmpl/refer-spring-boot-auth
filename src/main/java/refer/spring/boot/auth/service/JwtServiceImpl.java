package refer.spring.boot.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import refer.spring.boot.auth.domain.Account;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    private final byte[] key;

    public JwtServiceImpl(@Value("${jwt.secret}") String secret) {
        this.key = secret.getBytes();
    }

    @Override
    public String getTokenSubject(String token) {
        return getTokenClaim(token, Claims::getSubject);
    }

    @Override
    public OffsetDateTime getTokenIssuedAt(String token) {
        return toOffsetDateTime(getTokenClaim(token, Claims::getIssuedAt));
    }

    @Override
    public OffsetDateTime getTokenExpiration(String token) {
        return toOffsetDateTime(getTokenClaim(token, Claims::getExpiration));
    }

    private <X> X getTokenClaim(String token, Function<Claims, X> extractor) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        return extractor.apply(claims);
    }

    @Override
    public String createToken(Account account) {
        return Jwts.builder()
                .setSubject(account.getUsername())
                .setIssuedAt(toDate(OffsetDateTime.now()))
                .setExpiration(toDate(OffsetDateTime.now().plusDays(1)))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, Account account) {
        return account.getUsername().equals(getTokenSubject(token)) &&
                OffsetDateTime.now().isBefore(getTokenExpiration(token));
    }

    private static Date toDate(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) {
            return null;
        }

        return Date.from(offsetDateTime.toInstant());
    }

    private static OffsetDateTime toOffsetDateTime(Date date) {
        if (date == null) {
            return null;
        }

        return date.toInstant().atOffset(OffsetDateTime.now().getOffset());
    }
}
