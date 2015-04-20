/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.test.presenter;

import android.content.Context;
import android.test.AndroidTestCase;
import com.fernandocejas.android10.sample.domain.interactor.GetUserListUseCase;
import com.fernandocejas.android10.sample.presentation.mapper.UserModelDataMapper;
import com.fernandocejas.android10.sample.presentation.presenter.UserListPresenter;
import com.fernandocejas.android10.sample.presentation.view.UserListView;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Subscriber;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class UserListPresenterTest extends AndroidTestCase {

  private UserListPresenter userListPresenter;

  @Mock
  private Context mockContext;
  @Mock
  private UserListView mockUserListView;
  @Mock
  private GetUserListUseCase mockGetUserListUseCase;
  @Mock
  private UserModelDataMapper mockUserModelDataMapper;

  @Override protected void setUp() throws Exception {
    super.setUp();
    MockitoAnnotations.initMocks(this);
    userListPresenter = new UserListPresenter(mockGetUserListUseCase, mockUserModelDataMapper);
    userListPresenter.setView(mockUserListView);
  }

  public void testUserListPresenterInitialize() {
    given(mockUserListView.getContext()).willReturn(mockContext);

    userListPresenter.initialize();

    verify(mockUserListView).hideRetry();
    verify(mockUserListView).showLoading();
    verify(mockGetUserListUseCase).execute(any(Subscriber.class));
  }
}
