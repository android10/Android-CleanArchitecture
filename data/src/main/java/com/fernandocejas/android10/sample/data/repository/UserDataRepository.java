/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository;

import com.fernandocejas.android10.sample.data.entity.UserEntity;
import com.fernandocejas.android10.sample.data.entity.mapper.UserEntityDataMapper;
import com.fernandocejas.android10.sample.data.exception.RepositoryErrorBundle;
import com.fernandocejas.android10.sample.data.exception.UserNotFoundException;
import com.fernandocejas.android10.sample.data.repository.datasource.UserDataStore;
import com.fernandocejas.android10.sample.data.repository.datasource.UserDataStoreFactory;
import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.repository.UserRepository;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.functions.Func1;

/**
 * {@link UserRepository} for retrieving user data.
 */
@Singleton
public class UserDataRepository implements UserRepository {

  private final UserDataStoreFactory userDataStoreFactory;
  private final UserEntityDataMapper userEntityDataMapper;

  private final Func1<List<UserEntity>, List<User>> userEntityMapper =
      new Func1<List<UserEntity>, List<User>>() {
        @Override public List<User> call(List<UserEntity> userEntities) {
          return UserDataRepository.this.userEntityDataMapper.transform(userEntities);
        }
      };

  /**
   * Constructs a {@link UserRepository}.
   *
   * @param dataStoreFactory A factory to construct different data source implementations.
   * @param userEntityDataMapper {@link UserEntityDataMapper}.
   */
  @Inject
  public UserDataRepository(UserDataStoreFactory dataStoreFactory,
      UserEntityDataMapper userEntityDataMapper) {
    this.userDataStoreFactory = dataStoreFactory;
    this.userEntityDataMapper = userEntityDataMapper;
  }

  @Override public Observable<List<User>> getUsers() {
    //we always get all users from the cloud
    final UserDataStore userDataStore = this.userDataStoreFactory.createCloudDataStore();
    return userDataStore.getUserEntityList().map(userEntityMapper);
  }

  @Override public void getUserById(final int userId, final UserDetailsCallback userCallback) {
    UserDataStore userDataStore = this.userDataStoreFactory.create(userId);
    userDataStore.getUserEntityDetails(userId, new UserDataStore.UserDetailsCallback() {
      @Override public void onUserEntityLoaded(UserEntity userEntity) {
        User user = UserDataRepository.this.userEntityDataMapper.transform(userEntity);
        if (user != null) {
          userCallback.onUserLoaded(user);
        } else {
          userCallback.onError(new RepositoryErrorBundle(new UserNotFoundException()));
        }
      }

      @Override public void onError(Exception exception) {
        userCallback.onError(new RepositoryErrorBundle(exception));
      }
    });
  }
}
