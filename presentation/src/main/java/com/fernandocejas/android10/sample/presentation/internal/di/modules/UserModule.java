/**
 * Copyright (C) 2015 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.internal.di.modules;

import com.fernandocejas.android10.sample.data.cache.UserCache;
import com.fernandocejas.android10.sample.data.cache.UserCacheImpl;
import com.fernandocejas.android10.sample.data.executor.JobExecutor;
import com.fernandocejas.android10.sample.data.repository.UserDataRepository;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.GetUserDetailsUseCase;
import com.fernandocejas.android10.sample.domain.interactor.GetUserDetailsUseCaseImpl;
import com.fernandocejas.android10.sample.domain.interactor.GetUserListUseCase;
import com.fernandocejas.android10.sample.domain.interactor.GetUserListUseCaseImpl;
import com.fernandocejas.android10.sample.domain.repository.UserRepository;
import com.fernandocejas.android10.sample.presentation.UIThread;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class UserModule {

  @Provides @Singleton ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides @Singleton PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  @Provides @Singleton UserCache provideUserCache(UserCacheImpl userCache) {
    return userCache;
  }

  @Provides @Singleton UserRepository provideUserRepository(UserDataRepository userDataRepository) {
    return userDataRepository;
  }

  @Provides GetUserListUseCase provideGetUserListUseCase(UserRepository userRepository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new GetUserListUseCaseImpl(userRepository, threadExecutor, postExecutionThread);
  }

  @Provides GetUserDetailsUseCase provideGetUserDetailsUseCase(UserRepository userRepository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return  new GetUserDetailsUseCaseImpl(userRepository,
        threadExecutor, postExecutionThread);
  }
}
