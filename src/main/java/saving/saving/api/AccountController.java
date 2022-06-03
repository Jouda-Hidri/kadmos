package saving.saving.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import saving.saving.api.dto.AccountRequest;
import saving.saving.api.dto.AccountResponse;
import saving.saving.service.AccountService;

@RestController
@RequestMapping("/balance")
public class AccountController {

  private final AccountService accountService;

  public AccountController(final AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping
  public ResponseEntity<AccountResponse> fetchBalance() {
    final var response = new AccountResponse(accountService.findAccount());
    return ResponseEntity.ok(response);
  }

  @PutMapping
  public ResponseEntity<AccountResponse> updateBalance(@RequestBody final AccountRequest request) {
    final var response = new AccountResponse(accountService.updateBalance(request.getAmount()));
    return ResponseEntity.ok(response);
  }
}
