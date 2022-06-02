package saving.saving.service;

import java.util.Optional;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import saving.saving.api.dto.TransactionResponse;
import saving.saving.persistence.Account;
import saving.saving.persistence.AccountRepo;

@Service
public class SavingService {

  // hard coded ID should be replaced by the actual requested account ID in a production env
  public static final long ACCOUNT_ID = 1L;
  private final AccountRepo accountRepo;

  public SavingService(final AccountRepo accountRepo) {
    this.accountRepo = accountRepo;
  }

  public TransactionResponse find() {
    Optional<Account> optionalAccount = accountRepo.findById(ACCOUNT_ID);
    if (optionalAccount.isEmpty()) {
      return null; // todo 404
    }
    return new TransactionResponse(optionalAccount.get());
  }

  public TransactionResponse update(@NonNull final Long amount) {
    Optional<Account> optionalAccount = accountRepo.findById(ACCOUNT_ID);
    if (optionalAccount.isEmpty()) {
      return null; // todo 404
    }
    final var account = optionalAccount.get();
    account.updateBalance(amount);
    final var savedAccount = accountRepo.save(account);
    return new TransactionResponse(savedAccount);
  }
}
