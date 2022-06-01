package saving.saving.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import saving.saving.api.dto.TransactionResponse;
import saving.saving.persistence.Account;
import saving.saving.persistence.AccountRepo;

@Service
public class SavingService {
  private final AccountRepo accountRepo;

  public SavingService(final AccountRepo accountRepo) {
    this.accountRepo = accountRepo;
  }

  public TransactionResponse update(final Long amount) {
    Optional<Account> optionalAccount = accountRepo.findById(1L);
    if(optionalAccount.isEmpty()) {
      return null; // todo 404
    }
    final var account = optionalAccount.get();
      account.updateBalance(amount);
      final var savedAccount = accountRepo.save(account);
      return new TransactionResponse(savedAccount);
  }

}
