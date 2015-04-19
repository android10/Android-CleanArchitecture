/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository.datasource;

import com.fernandocejas.android10.sample.data.ApplicationTestCase;
import com.fernandocejas.android10.sample.data.cache.UserCache;
import com.fernandocejas.android10.sample.data.entity.UserEntity;
import com.fernandocejas.android10.sample.data.net.RestApi;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class CloudUserDataStoreTest extends ApplicationTestCase {

  private static final int FAKE_USER_ID = 765;

  private CloudUserDataStore cloudUserDataStore;

  @Mock private RestApi mockRestApi;
  @Mock private UserCache mockUserCache;
  @Mock private UserDataStore.UserListCallback mockUserListDataStoreCallback;
  @Mock private UserDataStore.UserDetailsCallback mockUserDetailsDataStoreCallback;
  @Captor private ArgumentCaptor<RestApi.UserListCallback> restApiUserListCallbackArgumentCaptor;
  @Captor private ArgumentCaptor<RestApi.UserDetailsCallback> restApiUserDetailsCallbackArgumentCaptor;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    cloudUserDataStore = new CloudUserDataStore(mockRestApi, mockUserCache);
  }

  @Test
  public void testGetUserEntityDetailsSuccessfully() {
    UserEntity mockUserEntity = mock(UserEntity.class);

    cloudUserDataStore.getUserEntityDetails(FAKE_USER_ID, mockUserDetailsDataStoreCallback);

    verify(mockRestApi).getUserById(anyInt(), restApiUserDetailsCallbackArgumentCaptor.capture());
    verifyZeroInteractions(mockUserDetailsDataStoreCallback);
    verifyZeroInteractions(mockUserCache);

    restApiUserDetailsCallbackArgumentCaptor.getValue().onUserEntityLoaded(mockUserEntity);

    verify(mockUserDetailsDataStoreCallback).onUserEntityLoaded(mockUserEntity);
    verify(mockUserCache).put(mockUserEntity);
  }

  @Test
  public void testGetUserEntityDetailsError() {
    cloudUserDataStore.getUserEntityDetails(FAKE_USER_ID, mockUserDetailsDataStoreCallback);

    verify(mockRestApi).getUserById(anyInt(), restApiUserDetailsCallbackArgumentCaptor.capture());
    verifyZeroInteractions(mockUserDetailsDataStoreCallback);

    restApiUserDetailsCallbackArgumentCaptor.getValue().onError(any(Exception.class));

    verify(mockUserDetailsDataStoreCallback).onError(any(Exception.class));
    verifyZeroInteractions(mockUserCache);
  }

  @Test
  public void testGetUserEntityListFromApi() {
    cloudUserDataStore.getUserEntityList();
    verify(mockRestApi).getUserEntityList();
  }
}
