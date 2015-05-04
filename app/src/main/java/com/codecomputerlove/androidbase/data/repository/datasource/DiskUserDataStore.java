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
package com.codecomputerlove.androidbase.data.repository.datasource;

import com.codecomputerlove.androidbase.data.cache.UserCache;
import com.codecomputerlove.androidbase.data.entity.UserEntity;
import java.util.List;
import rx.Observable;

/**
 * {@link UserDataStore} implementation based on file system data store.
 */
public class DiskUserDataStore implements UserDataStore {

  private final UserCache userCache;

  /**
   * Construct a {@link UserDataStore} based file system data store.
   *
   * @param userCache A {@link UserCache} to cache data retrieved from the api.
   */
  public DiskUserDataStore(UserCache userCache) {
    this.userCache = userCache;
  }

  @Override public Observable<List<UserEntity>> getUserEntityList() {
    //TODO: implement simple cache for storing/retrieving collections of users.
    throw new UnsupportedOperationException("Operation is not available!!!");
  }

  @Override public Observable<UserEntity> getUserEntityDetails(final int userId) {
     return this.userCache.get(userId);
  }
}
