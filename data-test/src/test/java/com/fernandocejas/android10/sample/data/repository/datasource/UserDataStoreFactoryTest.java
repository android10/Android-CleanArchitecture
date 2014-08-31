/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository.datasource;

import com.fernandocejas.android10.sample.data.ApplicationTestCase;
import com.fernandocejas.android10.sample.data.cache.UserCache;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class UserDataStoreFactoryTest extends ApplicationTestCase {

  private static final int FAKE_USER_ID = 11;

  private UserDataStoreFactory userDataStoreFactory;

  @Mock
  private UserCache mockUserCache;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    userDataStoreFactory =
        new UserDataStoreFactory(Robolectric.application, mockUserCache);
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
