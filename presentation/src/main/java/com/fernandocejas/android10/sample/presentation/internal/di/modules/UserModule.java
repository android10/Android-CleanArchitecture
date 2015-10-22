/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.sample.presentation.internal.di.modules;

import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.interactor.GetUserDetails;
import com.fernandocejas.android10.sample.domain.interactor.GetUserDetailsUseCaseParams;
import com.fernandocejas.android10.sample.domain.interactor.GetUserList;
import com.fernandocejas.android10.sample.domain.interactor.GetUserListUseCaseParams;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;
import java.util.List;
import javax.inject.Named;

/**
 * Dagger module that provides user related collaborators.
 */
@Module public class UserModule {

  private int userId = -1;

  public UserModule() {
  }

  public UserModule(int userId) {
    this.userId = userId;
  }

  @Provides @PerActivity int provideUserId() {
    return userId;
  }

  @Provides @PerActivity @Named("userList")
  UseCase<GetUserListUseCaseParams, List<User>> provideGetUserListUseCase(GetUserList getUserList) {

    return getUserList;
  }

  @Provides @PerActivity @Named("userDetails")
  UseCase<GetUserDetailsUseCaseParams, User> provideGetUserDetailsUseCase(
      GetUserDetails getUserDetails) {

    return getUserDetails;
  }
}