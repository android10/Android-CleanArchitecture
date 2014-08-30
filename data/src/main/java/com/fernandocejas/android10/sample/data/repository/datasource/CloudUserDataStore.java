/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository.datasource;

import com.fernandocejas.android10.sample.data.cache.UserCache;
import com.fernandocejas.android10.sample.data.net.RestApi;

/**
 * {@link UserDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudUserDataStore implements UserDataStore {

  private final RestApi restApi;
  private final UserCache userCache;

  /**
   * Construct a {@link UserDataStore} based on connections to the api (Cloud).
   * @param restApi The {@link RestApi} implementation to use.
   * @param userCache A {@link UserCache} to cache data retrieved from the api.
   */
  public CloudUserDataStore(RestApi restApi, UserCache userCache) {
    this.restApi = restApi;
    this.userCache = userCache;
  }

  @Override public void getUsersEntityList(UserListCallback userListCallback) {

  }

  @Override public void getUserEntityDetails(int id, Callback callback) {

  }
}
