package com.midas.app.workflows;

import com.midas.app.activities.AccountActivity;
import com.midas.app.models.Account;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateAccountWorkFlowImpl implements CreateAccountWorkflow {

  private final AccountActivity activity;

  @Override
  public Account createAccount(Account details) throws StripeException {
    return activity.createPaymentAccount(details);
  }
}
