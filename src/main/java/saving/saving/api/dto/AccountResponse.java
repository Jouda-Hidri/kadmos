package saving.saving.api.dto;

import saving.saving.persistence.Account;

public class AccountResponse {

  private Long balance;

  public AccountResponse() {

  }

  public AccountResponse(final Account account) {
    this.balance = account.getBalance();
  }

  public Long getBalance() {
    return balance;
  }

  public void setBalance(Long balance) {
    this.balance = balance;
  }
}
