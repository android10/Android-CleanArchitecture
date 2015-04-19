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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class CloudUserDataStoreTest extends ApplicationTestCase {

  private static final int FAKE_USER_ID = 765;

  private CloudUserDataStore cloudUserDataStore;

  @Mock private RestApi mockRestApi;
  @Mock private UserCache mockUserCache;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    cloudUserDataStore = new CloudUserDataStore(mockRestApi, mockUserCache);
  }

  @Test
  public void testGetUserEntityListFromApi() {
    cloudUserDataStore.getUserEntityList();
    verify(mockRestApi).getUserEntityList();
  }

  @Test
  public void testGetUserEntityDetailsFromApi() {
    UserEntity fakeUserEntity = new UserEntity();
    Observable<UserEntity> fakeObservable = Observable.just(fakeUserEntity);
    given(mockRestApi.getUserEntityById(FAKE_USER_ID)).willReturn(fakeObservable);

    cloudUserDataStore.getUserEntityDetails(FAKE_USER_ID);

    verify(mockRestApi).getUserEntityById(FAKE_USER_ID);
  }
}
