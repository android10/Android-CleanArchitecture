/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository;

import com.fernandocejas.android10.sample.data.ApplicationTestCase;
import com.fernandocejas.android10.sample.data.entity.UserEntity;
import com.fernandocejas.android10.sample.data.entity.mapper.UserEntityDataMapper;
import com.fernandocejas.android10.sample.data.repository.datasource.UserDataStore;
import com.fernandocejas.android10.sample.data.repository.datasource.UserDataStoreFactory;
import com.fernandocejas.android10.sample.domain.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

public class UserDataRepositoryTest extends ApplicationTestCase {

  private static final int FAKE_USER_ID = 123;

  private UserDataRepository userDataRepository;

  @Mock private UserDataStoreFactory mockUserDataStoreFactory;
  @Mock private UserEntityDataMapper mockUserEntityDataMapper;
  @Mock private UserDataStore mockUserDataStore;
  @Mock private UserEntity mockUserEntity;
  @Mock private User mockUser;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    resetSingleton(UserDataRepository.class);
    userDataRepository = new UserDataRepository(mockUserDataStoreFactory,
        mockUserEntityDataMapper);

    given(mockUserDataStoreFactory.create(anyInt())).willReturn(mockUserDataStore);
    given(mockUserDataStoreFactory.createCloudDataStore()).willReturn(mockUserDataStore);
  }

  @Test
  public void testGetUsersHappyCase() {
    List<UserEntity> usersList = new ArrayList<>();
    usersList.add(new UserEntity());
    given(mockUserDataStore.getUserEntityList()).willReturn(Observable.just(usersList));

    userDataRepository.getUsers();

    verify(mockUserDataStoreFactory).createCloudDataStore();
    verify(mockUserDataStore).getUserEntityList();
  }

  @Test
  public void testGetUserHappyCase() {
    UserEntity userEntity = new UserEntity();
    given(mockUserDataStore.getUserEntityDetails(FAKE_USER_ID)).willReturn(Observable.just(userEntity));
    userDataRepository.getUser(FAKE_USER_ID);

    verify(mockUserDataStoreFactory).create(FAKE_USER_ID);
    verify(mockUserDataStore).getUserEntityDetails(FAKE_USER_ID);
  }
}
