package refer.spring.boot.auth.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import refer.spring.boot.auth.controller.api.request.RequestAccountSave;
import refer.spring.boot.auth.controller.api.response.ResponseAccount;
import refer.spring.boot.auth.domain.Account;
import refer.spring.boot.auth.domain.AccountNotFoundException;
import refer.spring.boot.auth.service.AccountService;

import javax.validation.Valid;

@RequestMapping("/api/account")
@RestController
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseAccount> show(@PathVariable Long id) {
        Account account = accountService.findAccount(id)
                .orElseThrow(() ->
                        new AccountNotFoundException(String.format("No account found for id: %s", id)));

        return ResponseEntity.ok(ApiMapper.INSTANCE.toResponseAccount(account));
    }

    @PostMapping("")
    public ResponseEntity<ResponseAccount> save(@Valid @RequestBody RequestAccountSave request) {
        Account account = accountService.saveAccount(ApiMapper.INSTANCE.toAccount(request));

        return ResponseEntity.ok(ApiMapper.INSTANCE.toResponseAccount(account));
    }
}
