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
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetUserListUseCaseTest {

  private GetUserListUseCase getUserListUseCase;

  @Mock
  private ThreadExecutor mockThreadExecutor;
  @Mock
  private PostExecutionThread mockPostExecutionThread;
  @Mock
  private UserRepository mockUserRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    getUserListUseCase = new GetUserListUseCaseImpl(mockUserRepository, mockThreadExecutor,
        mockPostExecutionThread);
  }

  @Test
  public void testGetUserListUseCaseExecution() {
    doNothing().when(mockThreadExecutor).execute(any(Interactor.class));

    GetUserListUseCase.Callback mockGetUserListCallback = mock(GetUserListUseCase.Callback.class);

    getUserListUseCase.execute(mockGetUserListCallback);

    verify(mockThreadExecutor).execute(any(Interactor.class));
    verifyNoMoreInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockUserRepository);
    verifyZeroInteractions(mockPostExecutionThread);
  }

  @Test
  public void testGetUserListUseCaseInteractorRun() {
    GetUserListUseCase.Callback mockGetUserListCallback = mock(GetUserListUseCase.Callback.class);

    doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
    doNothing().when(mockUserRepository).getUserList(any(UserRepository.UserListCallback.class));

    getUserListUseCase.execute(mockGetUserListCallback);
    getUserListUseCase.run();

    verify(mockUserRepository).getUserList(any(UserRepository.UserListCallback.class));
    verify(mockThreadExecutor).execute(any(Interactor.class));
    verifyNoMoreInteractions(mockUserRepository);
    verifyNoMoreInteractions(mockThreadExecutor);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testUserListUseCaseCallbackSuccessful() {
    final GetUserListUseCase.Callback mockGetUserListCallback =
        mock(GetUserListUseCase.Callback.class);
    final Collection<User> mockResponseUserList = (Collection<User>) mock(Collection.class);

    doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ((UserRepository.UserListCallback) invocation.getArguments()[0]).onUserListLoaded(
            mockResponseUserList);
        return null;
      }
    }).when(mockUserRepository).getUserList(any(UserRepository.UserListCallback.class));

    getUserListUseCase.execute(mockGetUserListCallback);
    getUserListUseCase.run();

    verify(mockPostExecutionThread).post(any(Runnable.class));
    verifyNoMoreInteractions(mockGetUserListCallback);
    verifyZeroInteractions(mockResponseUserList);
  }

  @Test
  public void testUserListUseCaseCallbackError() {
    final GetUserListUseCase.Callback mockGetUserListUseCaseCallback =
        mock(GetUserListUseCase.Callback.class);
    final ErrorBundle mockErrorBundle = mock(ErrorBundle.class);

    doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ((UserRepository.UserListCallback) invocation.getArguments()[0]).onError(mockErrorBundle);
        return null;
      }
    }).when(mockUserRepository).getUserList(any(UserRepository.UserListCallback.class));

    getUserListUseCase.execute(mockGetUserListUseCaseCallback);
    getUserListUseCase.run();

    verify(mockPostExecutionThread).post(any(Runnable.class));
    verifyNoMoreInteractions(mockGetUserListUseCaseCallback);
    verifyZeroInteractions(mockErrorBundle);
  }
}
