/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.test.exception;

import android.test.AndroidTestCase;
import com.fernandocejas.android10.sample.data.exception.NetworkConnectionException;
import com.fernandocejas.android10.sample.data.exception.UserNotFoundException;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ErrorMessageFactoryTest extends AndroidTestCase {

  @Override protected void setUp() throws Exception {
    super.setUp();
  }

  public void testNetworkConnectionErrorMessage() {
    String expectedMessage = getContext().getString(R.string.exception_message_no_connection);
    String actualMessage = ErrorMessageFactory.create(getContext(),
        new NetworkConnectionException());

    assertThat(actualMessage, is(equalTo(expectedMessage)));
  }

  public void testUserNotFoundErrorMessage() {
    String expectedMessage = getContext().getString(R.string.exception_message_user_not_found);
    String actualMessage = ErrorMessageFactory.create(getContext(), new UserNotFoundException());

    assertThat(actualMessage, is(equalTo(expectedMessage)));
  }
}
