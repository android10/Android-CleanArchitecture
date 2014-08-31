/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository.datasource;

import com.fernandocejas.android10.sample.data.ApplicationTestCase;
import com.fernandocejas.android10.sample.data.cache.UserCache;
import com.fernandocejas.android10.sample.data.entity.UserEntity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class DiskUserDataStoreTest extends ApplicationTestCase {

  private static final int FAKE_USER_ID = 11;

  private DiskUserDataStore diskUserDataStore;

  @Mock
  private UserCache mockUserCache;
  @Mock
  private UserDataStore.UserDetailsCallback mockUserDetailsDataStoreCallback;
  @Captor
  private ArgumentCaptor<UserCache.UserCacheCallback> userCacheCallbackArgumentCaptor;

  @Rule
  ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    diskUserDataStore = new DiskUserDataStore(mockUserCache);
  }

  @Test
  public void testGetUserEntityByIdSuccessfully() {
    UserEntity mockUserEntity = mock(UserEntity.class);

    diskUserDataStore.getUserEntityDetails(FAKE_USER_ID, mockUserDetailsDataStoreCallback);

    verify(mockUserCache).get(anyInt(), userCacheCallbackArgumentCaptor.capture());
    verifyZeroInteractions(mockUserDetailsDataStoreCallback);

    userCacheCallbackArgumentCaptor.getValue().onUserEntityLoaded(mockUserEntity);

    verify(mockUserDetailsDataStoreCallback).onUserEntityLoaded(mockUserEntity);
  }

  @Test
  public void testGetUserEntityByIdError() {
    diskUserDataStore.getUserEntityDetails(FAKE_USER_ID, mockUserDetailsDataStoreCallback);

    verify(mockUserCache).get(anyInt(), userCacheCallbackArgumentCaptor.capture());
    verifyZeroInteractions(mockUserDetailsDataStoreCallback);

    userCacheCallbackArgumentCaptor.getValue().onError(any(Exception.class));

    verify(mockUserDetailsDataStoreCallback).onError(any(Exception.class));
  }

  @Test
  public void testGetUserEntityListUnsupported() {
    expectedException.expect(UnsupportedOperationException.class);
    diskUserDataStore.getUsersEntityList(any(UserDataStore.UserListCallback.class));
  }
}
