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
package com.fernandocejas.android10.sample.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

  private static final int FAKE_USER_ID = 8;

  private User user;

  @Before
  public void setUp() {
    user = new User(FAKE_USER_ID);
  }

  @Test
  public void testUserConstructorHappyCase() {
    final int userId = user.getUserId();

    assertThat(userId).isEqualTo(FAKE_USER_ID);
  }
}
