package com.midas.app.activities;

import com.midas.app.models.Account;
import com.midas.app.repositories.AccountRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import io.temporal.workflow.Workflow;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public class AccountActivityImpl implements AccountActivity {

  private final Logger logger = Workflow.getLogger(AccountActivityImpl.class);

  @Value("${stripe.api-key}")
  private String apiKey;

  private final AccountRepository accountRepository;

  @Override
  public Account saveAccount(Account account) {
    Account savedAccount = null;
    try {
      savedAccount = accountRepository.save(account);
    } catch (Exception exception) {
      logger.error("Error in saving user account {} ", account);
      exception.fillInStackTrace();
    }
    return savedAccount;
  }

  @Override
  public Account createPaymentAccount(Account account) throws StripeException {
    Stripe.apiKey = apiKey;
    CustomerCreateParams params =
        CustomerCreateParams.builder()
            .setName(account.getFirstName())
            .setEmail(account.getEmail())
            .build();
    Customer customer = Customer.create(params);
    account.setProviderId(customer.getId());
    return saveAccount(account);
  }
}
