package refer.spring.boot.auth.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import refer.spring.boot.auth.controller.api.request.RequestSignIn;
import refer.spring.boot.auth.controller.api.response.ResponseAccount;
import refer.spring.boot.auth.controller.api.response.ResponseToken;
import refer.spring.boot.auth.domain.Account;
import refer.spring.boot.auth.domain.AuthException;
import refer.spring.boot.auth.service.AccountService;
import refer.spring.boot.auth.service.AuthService;
import refer.spring.boot.auth.service.JwtService;

import javax.validation.Valid;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authService;
    private final AccountService accountService;

    @Autowired
    public AuthController(JwtService jwtService,
                          AuthService authService,
                          AccountService accountService) {
        this.jwtService = jwtService;
        this.authService = authService;
        this.accountService = accountService;
    }

    @GetMapping("/id")
    public ResponseAccount identify() {
        return authService.findAuthenticatedUsername()
                .flatMap(accountService::findAccount)
                .map(ApiMapper.INSTANCE::toResponseAccount)
                .orElseThrow(() ->
                        new AuthException("Not authenticated"));
    }

    @PostMapping("/sign-in")
    public ResponseToken signIn(@Valid @RequestBody RequestSignIn request) {
        authService.authenticate(request.getUsername(), request.getPassword());

        Account account = authService.findAuthenticatedUsername()
                .flatMap(accountService::findAccount)
                .orElseThrow(() ->
                        new AuthException("Not authenticated"));

        ResponseToken result = new ResponseToken();
        result.setAccess(jwtService.createToken(account));

        return result;
    }
}
