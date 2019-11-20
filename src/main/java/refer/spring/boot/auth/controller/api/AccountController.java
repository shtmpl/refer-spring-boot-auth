package refer.spring.boot.auth.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return accountService.findAccount(id)
                .map(ApiMapper.INSTANCE::toResponseAccount)
                .map(ResponseEntity::ok)
                .orElseThrow(() ->
                        new AccountNotFoundException(String.format("No account found for id: %s", id)));
    }
}
