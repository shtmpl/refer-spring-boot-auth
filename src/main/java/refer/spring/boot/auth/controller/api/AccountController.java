package refer.spring.boot.auth.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import refer.spring.boot.auth.controller.api.response.ResponseAccount;
import refer.spring.boot.auth.domain.Account;
import refer.spring.boot.auth.domain.AccountNotFoundException;
import refer.spring.boot.auth.service.AccountService;

@RequestMapping("/api/account")
@RestController
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseAccount> identify() {
        // FIXME:

        Account account = accountService.findOwnAccount()
                .orElseThrow(() ->
                        new AccountNotFoundException("No account found: %s"));

        return ResponseEntity.ok(ApiMapper.INSTANCE.toResponseAccount(account));
    }
}
