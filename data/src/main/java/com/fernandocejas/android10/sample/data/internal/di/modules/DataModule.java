package com.fernandocejas.android10.sample.data.internal.di.modules;

import com.fernandocejas.android10.sample.data.cache.UserCache;
import com.fernandocejas.android10.sample.data.cache.UserCacheImpl;
import com.fernandocejas.android10.sample.data.entity.UserEntity;
import com.fernandocejas.android10.sample.data.entity.mapper.EntityDataMapper;
import com.fernandocejas.android10.sample.data.entity.mapper.UserEntityDataMapper;
import com.fernandocejas.android10.sample.data.repository.UserDataRepository;
import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.repository.UserRepository;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * @author maciej@goraczka.com
 */
@Module
public class DataModule {

  @Provides @Singleton UserCache provideUserCache(UserCacheImpl userCache) {
    return userCache;
  }

  @Provides @Singleton UserRepository provideUserRepository(UserDataRepository userDataRepository) {
    return userDataRepository;
  }

  @Provides @Singleton EntityDataMapper<UserEntity, User> provideEntityDataMapper(){
    return new UserEntityDataMapper();
  }
}
