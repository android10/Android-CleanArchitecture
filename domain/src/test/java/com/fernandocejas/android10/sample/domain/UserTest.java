/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserTest {

  private static final int FAKE_USER_ID = 8;

  private User user;

  @Before
  public void setUp() {
    user = new User(FAKE_USER_ID);
  }

  @Test
  public void testUserConstructorHappyCase() {
    int userId = user.getUserId();

    assertThat(userId, is(FAKE_USER_ID));
  }
}
