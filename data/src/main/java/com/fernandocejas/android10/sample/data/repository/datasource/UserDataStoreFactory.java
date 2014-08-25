/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository.datasource;

import android.content.Context;

/**
 * Factory that creates different implementations of {@link UserDataStore}.
 */
public class UserDataStoreFactory {

  private final Context context;

  public UserDataStoreFactory(Context context) {
    this.context = context.getApplicationContext();
  }

  /**
   * Create {@link UserDataStore} from a user id.
   * @param userId
   * @return
   */
  public UserDataStore create(int userId) {
    UserDataStore userDataStore = null;

    return userDataStore;
  }
}
