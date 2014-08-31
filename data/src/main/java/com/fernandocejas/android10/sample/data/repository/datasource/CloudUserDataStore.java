/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository.datasource;

import com.fernandocejas.android10.sample.data.cache.UserCache;
import com.fernandocejas.android10.sample.data.entity.UserEntity;
import com.fernandocejas.android10.sample.data.net.RestApi;
import java.util.Collection;

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

  /**
   * {@inheritDoc}
   *
   * @param userListCallback A {@link UserListCallback} used for notifying clients.
   */
  @Override public void getUsersEntityList(final UserListCallback userListCallback) {
    this.restApi.getUserList(new RestApi.UserListCallback() {
      @Override public void onUserListLoaded(Collection<UserEntity> usersCollection) {
        userListCallback.onUserListLoaded(usersCollection);
      }

      @Override public void onError(Exception exception) {
        userListCallback.onError(exception);
      }
    });
  }

  /**
   * {@inheritDoc}
   *
   * @param id The user id used to retrieve user data.
   * @param userDetailsCallback A {@link UserDetailsCallback} used for notifying clients.
   */
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
