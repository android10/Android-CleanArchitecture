/**
 * Copyright (C) 2015 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.internal.di.modules;

import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.GetUserDetailsUseCase;
import com.fernandocejas.android10.sample.domain.interactor.GetUserListUseCase;
import com.fernandocejas.android10.sample.domain.interactor.UseCase;
import com.fernandocejas.android10.sample.domain.repository.UserRepository;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

/**
 * Dagger module that provides user related collaborators.
 */
@Module
public class UserModule {

  private int userId = -1;

  public UserModule() {}

  public UserModule(int userId) {
    this.userId = userId;
  }

  @Provides @PerActivity @Named("userList") UseCase provideGetUserListUseCase(
      GetUserListUseCase getUserListUseCase) {
    return getUserListUseCase;
  }

  @Provides @PerActivity @Named("userDetails") UseCase provideGetUserDetailsUseCase(
      UserRepository userRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new GetUserDetailsUseCase(userId, userRepository, threadExecutor, postExecutionThread);
  }
}