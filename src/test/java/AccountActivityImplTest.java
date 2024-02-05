import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import com.midas.app.activities.AccountActivityImpl;
import com.midas.app.models.Account;
import com.midas.app.repositories.AccountRepository;
import com.stripe.model.Customer;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

public class AccountActivityImplTest {

  @Mock private Logger logger;

  @Mock private AccountRepository accountRepository;

  @InjectMocks private AccountActivityImpl accountActivity;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Mock private Customer customer;

  @Test
  public void testSaveAccount_Successful() {

    Account inputAccount =
        Account.builder()
            .firstName("first_name")
            .lastName("last_name")
            .email("test@gmail.com")
            .build();
    Account savedAccount =
        Account.builder()
            .firstName("first_name")
            .lastName("last_name")
            .email("test@gmail.com")
            .build();
    ;
    when(accountRepository.save(inputAccount)).thenReturn(savedAccount);

    Account result = accountActivity.saveAccount(inputAccount);

    assertEquals(savedAccount, result);

    verify(logger, never()).error(anyString(), Optional.ofNullable(any()));

    verify(accountRepository, times(1)).save(inputAccount);
  }

  @Test
  public void testSaveAccount_ExceptionHandling() {
    Account inputAccount =
        Account.builder()
            .firstName("first_name")
            .lastName("last_name")
            .email("test@gmail.com")
            .build();
    Exception mockException = new RuntimeException("Mocked repository exception");

    when(accountRepository.save(inputAccount)).thenThrow(mockException);

    Account result = accountActivity.saveAccount(inputAccount);

    assertNull(result);

    verify(accountRepository, times(1)).save(inputAccount);
  }
}
