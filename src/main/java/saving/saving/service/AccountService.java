package saving.saving.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import saving.saving.api.ResourceNotFoundException;
import saving.saving.persistence.Account;
import saving.saving.persistence.AccountRepo;

@Service
public class AccountService {

  public static final long ACCOUNT_ID = 1L;
  private final AccountRepo accountRepo;

  public AccountService(final AccountRepo accountRepo) {
    this.accountRepo = accountRepo;
  }

  public Account findAccount() {
    return accountRepo.findById(ACCOUNT_ID).orElseThrow(ResourceNotFoundException::new);
  }

  public Account updateBalance(@NonNull final Long amount) {
    final var account = findAccount();
    account.updateBalance(amount);
    return accountRepo.save(account);
  }
}
