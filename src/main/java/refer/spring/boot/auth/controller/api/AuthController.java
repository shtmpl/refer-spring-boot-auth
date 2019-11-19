package refer.spring.boot.auth.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import refer.spring.boot.auth.controller.api.request.RequestAuthSignIn;
import refer.spring.boot.auth.controller.api.request.RequestAuthSignUp;
import refer.spring.boot.auth.service.AuthService;

import javax.validation.Valid;


@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody RequestAuthSignUp request) {
        // FIXME:

        return ResponseEntity.ok("Done!");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody RequestAuthSignIn request) {
        // FIXME:

        return ResponseEntity.ok("Done!");
    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> signOut() {
        return ResponseEntity.ok("Done!");
    }
}
