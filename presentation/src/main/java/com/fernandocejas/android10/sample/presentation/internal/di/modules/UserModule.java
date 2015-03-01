/**
 * Copyright (C) 2015 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.internal.di.modules;

import android.content.Context;
import com.fernandocejas.android10.sample.data.cache.FileManager;
import com.fernandocejas.android10.sample.data.cache.UserCache;
import com.fernandocejas.android10.sample.data.cache.UserCacheImpl;
import com.fernandocejas.android10.sample.data.cache.serializer.JsonSerializer;
import com.fernandocejas.android10.sample.data.entity.mapper.UserEntityDataMapper;
import com.fernandocejas.android10.sample.data.executor.JobExecutor;
import com.fernandocejas.android10.sample.data.repository.UserDataRepository;
import com.fernandocejas.android10.sample.data.repository.datasource.UserDataStoreFactory;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.interactor.GetUserDetailsUseCase;
import com.fernandocejas.android10.sample.domain.interactor.GetUserDetailsUseCaseImpl;
import com.fernandocejas.android10.sample.domain.interactor.GetUserListUseCase;
import com.fernandocejas.android10.sample.domain.interactor.GetUserListUseCaseImpl;
import com.fernandocejas.android10.sample.domain.repository.UserRepository;
import com.fernandocejas.android10.sample.presentation.UIThread;
import com.fernandocejas.android10.sample.presentation.mapper.UserModelDataMapper;
import com.fernandocejas.android10.sample.presentation.presenter.UserDetailsPresenter;
import com.fernandocejas.android10.sample.presentation.presenter.UserListPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

  @Provides ThreadExecutor provideThreadExecutor() {
    return JobExecutor.getInstance();
  }

  @Provides PostExecutionThread providePostExecutionThread() {
    return UIThread.getInstance();
  }

  @Provides JsonSerializer provideJsonSerializer() {
    return new JsonSerializer();
  }

  @Provides FileManager provideFileManager() {
    return FileManager.getInstance();
  }

  @Provides UserCache provideUserCache(Context context, JsonSerializer jsonSerializer,
      FileManager fileManager, ThreadExecutor threadExecutor) {
    return UserCacheImpl.getInstance(context, jsonSerializer, fileManager, threadExecutor);
  }

  @Provides UserDataStoreFactory provideUserDataStoreFactory(Context context, UserCache userCache) {
    return new UserDataStoreFactory(context, userCache);
  }

  @Provides UserEntityDataMapper provideUserEntityDataMapper() {
    return new UserEntityDataMapper();
  }

  @Provides UserRepository provideUserRepository(UserDataStoreFactory userDataStoreFactory,
      UserEntityDataMapper userEntityDataMapper) {
    return UserDataRepository.getInstance(userDataStoreFactory, userEntityDataMapper);
  }

  @Provides GetUserListUseCase provideGetUserListUseCase(UserRepository userRepository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new GetUserListUseCaseImpl(userRepository, threadExecutor, postExecutionThread);
  }

  @Provides UserModelDataMapper provideUserModelDataMapper() {
    return new UserModelDataMapper();
  }

  @Provides UserListPresenter provideUserListPresenter(GetUserListUseCase userListUseCase,
      UserModelDataMapper userModelDataMapper) {
    return new UserListPresenter(userListUseCase, userModelDataMapper);
  }

  @Provides GetUserDetailsUseCase provideGetUserDetailsUseCase(UserRepository userRepository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return  new GetUserDetailsUseCaseImpl(userRepository,
        threadExecutor, postExecutionThread);
  }

  @Provides UserDetailsPresenter provideUserDetailsPresenter(GetUserDetailsUseCase getUserDetailsUseCase,
      UserModelDataMapper userModelDataMapper) {
    return new UserDetailsPresenter(getUserDetailsUseCase, userModelDataMapper);
  }
}
