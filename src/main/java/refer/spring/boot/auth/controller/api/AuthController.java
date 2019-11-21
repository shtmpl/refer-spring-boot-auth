package refer.spring.boot.auth.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import refer.spring.boot.auth.controller.api.response.ResponseAccount;
import refer.spring.boot.auth.service.AccountService;
import refer.spring.boot.auth.service.AuthService;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;
    private final AccountService accountService;

    @Autowired
    public AuthController(AuthService authService,
                          AccountService accountService) {
        this.authService = authService;
        this.accountService = accountService;
    }

    @GetMapping("/id")
    public ResponseAccount identify() {
        return authService.findOwnAccountUsername()
                .flatMap(accountService::findAccount)
                .map(ApiMapper.INSTANCE::toResponseAccount)
                .orElseThrow(() ->
                        new UnauthenticatedException("Not authenticated"));
    }
}
