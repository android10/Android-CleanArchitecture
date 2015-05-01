/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codecomputerlove.androidbase.test.presenter;

import android.content.Context;
import android.test.AndroidTestCase;
import com.codecomputerlove.androidbase.domain.interactor.GetUserListUseCase;
import com.codecomputerlove.androidbase.presentation.mapper.UserModelDataMapper;
import com.codecomputerlove.androidbase.presentation.presenter.UserListPresenter;
import com.codecomputerlove.androidbase.presentation.view.UserListView;
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
