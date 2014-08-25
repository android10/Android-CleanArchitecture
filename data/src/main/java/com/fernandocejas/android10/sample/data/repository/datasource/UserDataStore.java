/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository.datasource;

import com.fernandocejas.android10.sample.data.entity.UserEntity;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface UserDataStore {
  /**
   * Callback used for clients to be notified when either user data has been retrieved successfully
   * or any error occurred.
   */
  interface Callback {
    void onUserEntityLoaded(UserEntity userEntity);
    void onError(Exception exception);
  }

  /**
   * Get a {@link UserEntity} by its id.
   * @param id The id to retrieve user data.
   * @param callback A {@link UserDataStore.Callback} for notifications.
   */
  void getUserEntityById(int id, Callback callback);
}
