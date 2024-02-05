import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.midas.app.activities.AccountActivity;
import com.midas.app.models.Account;
import com.midas.app.workflows.CreateAccountWorkFlowImpl;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CreateAccountWorkFlowImplTest {

  @Mock private AccountActivity accountActivity;

  @InjectMocks private CreateAccountWorkFlowImpl createAccountWorkFlow;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testCreateAccount() throws StripeException {

    Account inputAccount =
        Account.builder()
            .firstName("first_name")
            .lastName("last_name")
            .email("test@gmail.com")
            .build();
    Account createdAccount =
        Account.builder()
            .firstName("first_name")
            .lastName("last_name")
            .email("test@gmail.com")
            .build();
    ;

    when(accountActivity.createPaymentAccount(inputAccount)).thenReturn(createdAccount);

    Account result = createAccountWorkFlow.createAccount(inputAccount);

    assertEquals(createdAccount, result);

    verify(accountActivity, times(1)).createPaymentAccount(inputAccount);
  }
}
