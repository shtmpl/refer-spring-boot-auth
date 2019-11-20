package refer.spring.boot.auth.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import refer.spring.boot.auth.controller.api.request.RequestAuthSignIn;
import refer.spring.boot.auth.controller.api.request.RequestAuthSignUp;
import refer.spring.boot.auth.controller.api.response.ResponseAccount;
import refer.spring.boot.auth.service.AccountService;
import refer.spring.boot.auth.service.AuthService;

import javax.validation.Valid;


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
    public ResponseEntity<ResponseAccount> identify() {
        return authService.findOwnAccountUsername()
                .flatMap(accountService::findAccount)
                .map(ApiMapper.INSTANCE::toResponseAccount)
                .map(ResponseEntity::ok)
                .orElseThrow(() ->
                        new UnauthenticatedException("Not authenticated"));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody RequestAuthSignUp request) {
        return ResponseEntity.ok("Signed up");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn() {
        return ResponseEntity.ok("Signed in");
    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> signOut() {
        return ResponseEntity.ok("Signed out");
    }
}
