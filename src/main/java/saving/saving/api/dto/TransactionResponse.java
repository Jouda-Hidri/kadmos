package saving.saving.api.dto;

import saving.saving.persistence.Account;

public class TransactionResponse {

  private Long balance;

  public TransactionResponse() {

  }

  public TransactionResponse(final Account account) {
    this.balance = account.getBalance();
  }

  public Long getBalance() {
    return balance;
  }

  public void setBalance(Long balance) {
    this.balance = balance;
  }
}
