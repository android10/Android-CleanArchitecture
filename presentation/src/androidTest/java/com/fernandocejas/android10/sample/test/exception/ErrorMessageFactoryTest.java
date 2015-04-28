/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
