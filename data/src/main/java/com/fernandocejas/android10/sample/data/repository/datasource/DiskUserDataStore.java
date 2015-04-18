/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository.datasource;

import com.fernandocejas.android10.sample.data.cache.UserCache;
import com.fernandocejas.android10.sample.data.entity.UserEntity;
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

  @Override public void getUserEntityDetails(int id,
      final UserDetailsCallback userDetailsCallback) {
    this.userCache.get(id, new UserCache.UserCacheCallback() {
      @Override public void onUserEntityLoaded(UserEntity userEntity) {
        userDetailsCallback.onUserEntityLoaded(userEntity);
      }

      @Override public void onError(Exception exception) {
        userDetailsCallback.onError(exception);
      }
    });
  }
}
