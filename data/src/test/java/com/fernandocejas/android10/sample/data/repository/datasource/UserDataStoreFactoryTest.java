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
package com.fernandocejas.android10.sample.data.repository.datasource;

import com.fernandocejas.android10.sample.data.ApplicationTestCase;
import com.fernandocejas.android10.sample.data.cache.UserCache;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class UserDataStoreFactoryTest extends ApplicationTestCase {

  private static final int FAKE_USER_ID = 11;

  private UserDataStoreFactory userDataStoreFactory;

  @Mock private UserCache mockUserCache;

  @Before
  public void setUp() {
    userDataStoreFactory = new UserDataStoreFactory(RuntimeEnvironment.application, mockUserCache);
  }

  @Test
  public void testCreateDiskDataStore() {
    given(mockUserCache.isCached(FAKE_USER_ID)).willReturn(true);
    given(mockUserCache.isExpired()).willReturn(false);

    UserDataStore userDataStore = userDataStoreFactory.create(FAKE_USER_ID);

    assertThat(userDataStore, is(notNullValue()));
    assertThat(userDataStore, is(instanceOf(DiskUserDataStore.class)));

    verify(mockUserCache).isCached(FAKE_USER_ID);
    verify(mockUserCache).isExpired();
  }

  @Test
  public void testCreateCloudDataStore() {
    given(mockUserCache.isExpired()).willReturn(true);
    given(mockUserCache.isCached(FAKE_USER_ID)).willReturn(false);

    UserDataStore userDataStore = userDataStoreFactory.create(FAKE_USER_ID);

    assertThat(userDataStore, is(notNullValue()));
    assertThat(userDataStore, is(instanceOf(CloudUserDataStore.class)));

    verify(mockUserCache).isExpired();
  }
}
