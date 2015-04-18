/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository.datasource;

import com.fernandocejas.android10.sample.data.cache.UserCache;
import com.fernandocejas.android10.sample.data.entity.UserEntity;
import com.fernandocejas.android10.sample.data.net.RestApi;
import java.util.List;
import rx.Observable;

/**
 * {@link UserDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudUserDataStore implements UserDataStore {

  private final RestApi restApi;
  private final UserCache userCache;

  /**
   * Construct a {@link UserDataStore} based on connections to the api (Cloud).
   *
   * @param restApi The {@link RestApi} implementation to use.
   * @param userCache A {@link UserCache} to cache data retrieved from the api.
   */
  public CloudUserDataStore(RestApi restApi, UserCache userCache) {
    this.restApi = restApi;
    this.userCache = userCache;
  }

  @Override public Observable<List<UserEntity>> getUserEntityList() {
    return this.restApi.getUserEntityList();
  }

  @Override public void getUserEntityDetails(int id,
      final UserDetailsCallback userDetailsCallback) {
    this.restApi.getUserById(id, new RestApi.UserDetailsCallback() {
      @Override public void onUserEntityLoaded(UserEntity userEntity) {
        userDetailsCallback.onUserEntityLoaded(userEntity);
        CloudUserDataStore.this.putUserEntityInCache(userEntity);
      }

      @Override public void onError(Exception exception) {
        userDetailsCallback.onError(exception);
      }
    });
  }

  /**
   * Saves a {@link UserEntity} into cache.
   *
   * @param userEntity The {@link UserEntity} to save.
   */
  private void putUserEntityInCache(UserEntity userEntity) {
    if (userEntity != null) {
      this.userCache.put(userEntity);
    }
  }
}
