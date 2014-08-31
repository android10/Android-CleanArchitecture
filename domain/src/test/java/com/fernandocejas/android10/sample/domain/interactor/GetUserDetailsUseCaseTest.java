/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.interactor;

import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetUserDetailsUseCaseTest {

  private static final int FAKE_USER_ID = 123;

  private GetUserDetailsUseCase getUserDetailsUseCase;

  @Mock
  private ThreadExecutor mockThreadExecutor;
  @Mock
  private PostExecutionThread mockPostExecutionThread;
  @Mock
  private UserRepository mockUserRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    getUserDetailsUseCase = new GetUserDetailsUseCaseImpl(mockUserRepository, mockThreadExecutor,
        mockPostExecutionThread);
  }

  @Test
  public void testGetUserDetailsUseCaseExecution() {
    doNothing().when(mockThreadExecutor).execute(any(Interactor.class));

    GetUserDetailsUseCase.Callback mockGetUserDetailsCallback =
        mock(GetUserDetailsUseCase.Callback.class);

    getUserDetailsUseCase.execute(FAKE_USER_ID, mockGetUserDetailsCallback);

    verify(mockThreadExecutor).execute(any(Interactor.class));
    verifyNoMoreInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockUserRepository);
    verifyZeroInteractions(mockPostExecutionThread);
  }

  @Test
  public void testGetUserDetailsUseCaseInteractorRun() {
    GetUserDetailsUseCase.Callback mockGetUserDetailsCallback =
        mock(GetUserDetailsUseCase.Callback.class);

    doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
    doNothing().when(mockUserRepository)
        .getUserById(anyInt(), any(UserRepository.UserDetailsCallback.class));

    getUserDetailsUseCase.execute(FAKE_USER_ID, mockGetUserDetailsCallback);
    getUserDetailsUseCase.run();

    verify(mockUserRepository).getUserById(anyInt(), any(UserRepository.UserDetailsCallback.class));
    verify(mockThreadExecutor).execute(any(Interactor.class));
    verifyNoMoreInteractions(mockUserRepository);
    verifyNoMoreInteractions(mockThreadExecutor);
  }

  @Test
  public void testUserDetailsUseCaseCallbackSuccessful() {
    final GetUserDetailsUseCase.Callback mockGetUserDetailsCallback =
        mock(GetUserDetailsUseCase.Callback.class);
    final User mockResponseUser = mock(User.class);

    doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ((UserRepository.UserDetailsCallback) invocation.getArguments()[1]).onUserLoaded(
            mockResponseUser);
        return null;
      }
    }).when(mockUserRepository)
        .getUserById(anyInt(), any(UserRepository.UserDetailsCallback.class));

    getUserDetailsUseCase.execute(FAKE_USER_ID, mockGetUserDetailsCallback);
    getUserDetailsUseCase.run();

    verify(mockPostExecutionThread).post(any(Runnable.class));
    verifyNoMoreInteractions(mockGetUserDetailsCallback);
    verifyZeroInteractions(mockResponseUser);
  }

  @Test
  public void testUserDetailsUseCaseCallbackError() {
    final GetUserDetailsUseCase.Callback mockGetUserDetailsCallback =
        mock(GetUserDetailsUseCase.Callback.class);
    final ErrorBundle mockErrorHandler = mock(ErrorBundle.class);

    doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ((UserRepository.UserDetailsCallback) invocation.getArguments()[1]).onError(
            mockErrorHandler);
        return null;
      }
    }).when(mockUserRepository)
        .getUserById(anyInt(), any(UserRepository.UserDetailsCallback.class));

    getUserDetailsUseCase.execute(FAKE_USER_ID, mockGetUserDetailsCallback);
    getUserDetailsUseCase.run();

    verify(mockPostExecutionThread).post(any(Runnable.class));
    verifyNoMoreInteractions(mockGetUserDetailsCallback);
    verifyZeroInteractions(mockErrorHandler);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteUserCaseNullParameter() {
    getUserDetailsUseCase.execute(FAKE_USER_ID, null);
  }
}
