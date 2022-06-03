package saving.saving.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import saving.saving.persistence.Account;
import saving.saving.persistence.AccountRepo;
import saving.saving.service.SavingService;

@SpringBootTest
@AutoConfigureMockMvc
class SavingControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  private SavingService savingService;

  @MockBean
  private AccountRepo accountRepo;

  @MockBean
  private Account account;

  @Test
  @DisplayName("Given an account ID not existing, when GET request, then return 404")
  public void shouldReturnNotFoundStatus() throws Exception {
    when(accountRepo.findById(1L)).thenReturn(Optional.empty());
    mockMvc.perform(get("/balance")).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Given an account, when GET request, then display balance")
  public void shouldDisplayBalance() throws Exception {
    when(accountRepo.findById(1L)).thenReturn(Optional.of(account));
    mockMvc.perform(get("/balance"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("balance", Matchers.is(0)));
  }

  @Test
  @DisplayName("Given an account, when PUT request, then update balance")
  public void shouldUpdateBalance() throws Exception {
    final var updatedAccount = Mockito.mock(Account.class);
    when(accountRepo.findById(1L)).thenReturn(Optional.of(account));
    when(accountRepo.save(any())).thenReturn(updatedAccount);
    when(account.getBalance()).thenReturn(500L);
    when(updatedAccount.getBalance()).thenReturn(300L);

    mockMvc.perform(put("/balance")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"amount\":200}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("balance", Matchers.is(300)));
  }
}
