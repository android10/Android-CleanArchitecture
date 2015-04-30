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
package com.fernandocejas.android10.sample.data.repository;

import com.fernandocejas.android10.sample.data.entity.UserEntity;
import com.fernandocejas.android10.sample.data.entity.mapper.UserEntityDataMapper;
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

  private final Func1<List<UserEntity>, List<User>> userListEntityMapper =
      new Func1<List<UserEntity>, List<User>>() {
        @Override public List<User> call(List<UserEntity> userEntities) {
          return UserDataRepository.this.userEntityDataMapper.transform(userEntities);
        }
      };

  private final Func1<UserEntity, User>
      userDetailsEntityMapper = new Func1<UserEntity, User>() {
    @Override public User call(UserEntity userEntity) {
      return UserDataRepository.this.userEntityDataMapper.transform(userEntity);
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
    return userDataStore.getUserEntityList().map(userListEntityMapper);
  }

  @Override public Observable<User> getUser(int userId) {
    final UserDataStore userDataStore = this.userDataStoreFactory.create(userId);
    return userDataStore.getUserEntityDetails(userId).map(userDetailsEntityMapper);
  }
}
