/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository.datasource;

import android.content.Context;
import com.fernandocejas.android10.sample.data.cache.UserCache;
import com.fernandocejas.android10.sample.data.entity.mapper.UserEntityJsonMapper;
import com.fernandocejas.android10.sample.data.net.RestApi;
import com.fernandocejas.android10.sample.data.net.RestApiImpl;

/**
 * Factory that creates different implementations of {@link UserDataStore}.
 */
public class UserDataStoreFactory {

  private final Context context;
  private final UserCache userCache;

  public UserDataStoreFactory(Context context, UserCache userCache) {
    if (context == null || userCache == null) {
      throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
    }
    this.context = context.getApplicationContext();
    this.userCache = userCache;
  }

  /**
   * Create {@link UserDataStore} from a user id.
   */
  public UserDataStore create(int userId) {
    UserDataStore userDataStore;

    if (!this.userCache.isExpired() && this.userCache.isCached(userId)) {
      userDataStore = new DiskUserDataStore(this.userCache);
    } else {
      userDataStore = createCloudDataStore();
    }

    return userDataStore;
  }

  /**
   * Create {@link UserDataStore} to retrieve data from the Cloud.
   */
  public UserDataStore createCloudDataStore() {
    UserEntityJsonMapper userEntityJsonMapper = new UserEntityJsonMapper();
    RestApi restApi = new RestApiImpl(this.context, userEntityJsonMapper);

    return new CloudUserDataStore(restApi, this.userCache);
  }
}
