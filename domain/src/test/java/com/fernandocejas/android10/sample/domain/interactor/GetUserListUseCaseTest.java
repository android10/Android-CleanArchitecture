/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.interactor;

import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetUserListUseCaseTest {

  private GetUserListUseCase getUserListUseCase;

  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;
  @Mock private UserRepository mockUserRepository;
  @Mock private GetUserListUseCase.Callback mockGetUserListCallback;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    getUserListUseCase = new GetUserListUseCaseImpl(mockUserRepository, mockThreadExecutor,
        mockPostExecutionThread);
  }

  @Test
  public void testGetUserListUseCaseExecution() {
    getUserListUseCase.execute(mockGetUserListCallback);

    verify(mockThreadExecutor).execute(any(Interactor.class));
    verifyNoMoreInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockUserRepository);
    verifyZeroInteractions(mockPostExecutionThread);
  }

  @Test
  public void testGetUserListUseCaseInteractorRun() {
    given(mockUserRepository.getUsers()).willReturn(Observable.just(buildTestUserList()));

    getUserListUseCase.execute(mockGetUserListCallback);
    getUserListUseCase.run();

    verify(mockUserRepository).getUsers();
    verify(mockThreadExecutor).execute(any(Interactor.class));
    verifyNoMoreInteractions(mockUserRepository);
    verifyNoMoreInteractions(mockThreadExecutor);
  }

  private List<User> buildTestUserList() {
    List<User> userList = new ArrayList<>();
    userList.add(new User(1));
    userList.add(new User(2));
    userList.add(new User(3));

    return userList;
  }
}
