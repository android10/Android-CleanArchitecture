/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.net;

import com.fernandocejas.android10.sample.data.entity.UserEntity;
import com.fernandocejas.android10.sample.domain.User;
import java.util.Collection;
import java.util.List;
import rx.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {
  /**
   * Callback used to be notified when either a user list has been loaded or an error happened.
   */
  interface UserListCallback {
    void onUserListLoaded(Collection<UserEntity> usersCollection);

    void onError(Exception exception);
  }

  /**
   * Callback to be notified when getting a user from the network.
   */
  interface UserDetailsCallback {
    void onUserEntityLoaded(UserEntity userEntity);

    void onError(Exception exception);
  }

  static final String API_BASE_URL = "http://www.android10.org/myapi/";

  /** Api url for getting all users */
  static final String API_URL_GET_USER_LIST = API_BASE_URL + "users.json";
  /** Api url for getting a user profile: Remember to concatenate id + 'json' */
  static final String API_URL_GET_USER_DETAILS = API_BASE_URL + "user_";

  /**
   * Get a Observable which will emit a List of {@link User}.
   */
  Observable<List<UserEntity>> getUserEntityList();

  /**
   * Retrieves a user by id from the network.
   *
   * @param userId The user id used to get user data.
   * @param userDetailsCallback {@link UserDetailsCallback} to be notified when user data has been
   * retrieved.
   */
  void getUserById(final int userId, final UserDetailsCallback userDetailsCallback);
}
