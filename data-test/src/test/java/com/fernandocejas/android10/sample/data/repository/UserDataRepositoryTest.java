/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository;

import com.fernandocejas.android10.sample.data.ApplicationTestCase;
import com.fernandocejas.android10.sample.data.entity.UserEntity;
import com.fernandocejas.android10.sample.data.entity.mapper.UserEntityDataMapper;
import com.fernandocejas.android10.sample.data.exception.RepositoryErrorBundle;
import com.fernandocejas.android10.sample.data.repository.datasource.UserDataStore;
import com.fernandocejas.android10.sample.data.repository.datasource.UserDataStoreFactory;
import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.repository.UserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class UserDataRepositoryTest extends ApplicationTestCase {

  private static final int FAKE_USER_ID = 123;

  private UserDataRepository userDataRepository;

  @Mock
  private UserDataStoreFactory mockUserDataStoreFactory;
  @Mock
  private UserEntityDataMapper mockUserEntityDataMapper;
  @Mock
  private UserDataStore mockUserDataStore;
  @Mock
  private UserEntity mockUserEntity;
  @Mock
  private User mockUser;
  @Mock
  private UserRepository.UserDetailsCallback mockUserDetailsRepositoryCallback;
  @Mock
  private UserRepository.UserListCallback mockUserListRepositoryCallback;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    resetSingleton(UserDataRepository.class);
    userDataRepository = UserDataRepository.getInstance(mockUserDataStoreFactory,
        mockUserEntityDataMapper);

    given(mockUserDataStoreFactory.create(anyInt())).willReturn(mockUserDataStore);
    given(mockUserDataStoreFactory.createCloudDataStore()).willReturn(mockUserDataStore);
  }

  @Test
  public void testGetUserDetailsSuccess() {
    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ((UserDataStore.UserDetailsCallback) invocation.getArguments()[1]).onUserEntityLoaded(
            mockUserEntity);
        return null;
      }
    }).when(mockUserDataStore).getUserEntityDetails(anyInt(),
        any(UserDataStore.UserDetailsCallback.class));
    given(mockUserEntityDataMapper.transform(mockUserEntity)).willReturn(mockUser);

    userDataRepository.getUserById(FAKE_USER_ID, mockUserDetailsRepositoryCallback);

    verify(mockUserEntityDataMapper).transform(mockUserEntity);
    verify(mockUserDetailsRepositoryCallback).onUserLoaded(mockUser);
  }

  @Test
  public void testGetUserDetailsNullResult() {
    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ((UserDataStore.UserDetailsCallback) invocation.getArguments()[1]).onUserEntityLoaded(
            mockUserEntity);
        return null;
      }
    }).when(mockUserDataStore).getUserEntityDetails(anyInt(),
        any(UserDataStore.UserDetailsCallback.class));
    given(mockUserEntityDataMapper.transform(mockUserEntity)).willReturn(null);

    doNothing().when(mockUserDetailsRepositoryCallback).onError(any(RepositoryErrorBundle.class));

    userDataRepository.getUserById(FAKE_USER_ID, mockUserDetailsRepositoryCallback);

    verify(mockUserEntityDataMapper).transform(mockUserEntity);
    verify(mockUserDetailsRepositoryCallback).onError(any(RepositoryErrorBundle.class));
  }

  @Test
  public void testGetUserByIdError() {
    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ((UserDataStore.UserDetailsCallback) invocation.getArguments()[1]).onError(
            any(Exception.class));
        return null;
      }
    }).when(mockUserDataStore).getUserEntityDetails(anyInt(),
        any(UserDataStore.UserDetailsCallback.class));

    userDataRepository.getUserById(FAKE_USER_ID, mockUserDetailsRepositoryCallback);

    verify(mockUserDetailsRepositoryCallback).onError(any(RepositoryErrorBundle.class));
    verifyZeroInteractions(mockUserEntityDataMapper);
  }

  @Test
  public void testInvalidParametersConstructor() {
    resetSingleton(UserDataRepository.class);

    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("Invalid null parameter");

    userDataRepository = UserDataRepository.getInstance(null, null);
  }
}
