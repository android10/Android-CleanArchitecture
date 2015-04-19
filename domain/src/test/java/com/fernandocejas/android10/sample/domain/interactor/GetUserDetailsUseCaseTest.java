/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.interactor;

import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetUserDetailsUseCaseTest {

  private static final int FAKE_USER_ID = 123;

  private GetUserDetailsUseCase getUserDetailsUseCase;

  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;
  @Mock private UserRepository mockUserRepository;
  @Mock private GetUserDetailsUseCase.Callback mockGetUserDetailsCallback;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    getUserDetailsUseCase = new GetUserDetailsUseCaseImpl(mockUserRepository, mockThreadExecutor,
        mockPostExecutionThread);
  }

  @Test
  public void testGetUserDetailsUseCaseExecution() {
    doNothing().when(mockThreadExecutor).execute(any(Interactor.class));

    getUserDetailsUseCase.execute(FAKE_USER_ID, mockGetUserDetailsCallback);

    verify(mockThreadExecutor).execute(any(Interactor.class));
    verifyNoMoreInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockUserRepository);
    verifyZeroInteractions(mockPostExecutionThread);
  }

  @Test
  public void testGetUserDetailsUseCaseInteractorRun() {

    given(mockUserRepository.getUser(FAKE_USER_ID)).willReturn(Observable.just(new User(FAKE_USER_ID)));

    getUserDetailsUseCase.execute(FAKE_USER_ID, mockGetUserDetailsCallback);
    getUserDetailsUseCase.run();

    verify(mockUserRepository).getUser(FAKE_USER_ID);
    verify(mockThreadExecutor).execute(any(Interactor.class));
    verifyNoMoreInteractions(mockUserRepository);
    verifyNoMoreInteractions(mockThreadExecutor);
  }
}
