/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.net;

import com.fernandocejas.android10.sample.data.entity.UserEntity;
import java.util.List;
import rx.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {
  static final String API_BASE_URL = "http://www.android10.org/myapi/";

  /** Api url for getting all users */
  static final String API_URL_GET_USER_LIST = API_BASE_URL + "users.json";
  /** Api url for getting a user profile: Remember to concatenate id + 'json' */
  static final String API_URL_GET_USER_DETAILS = API_BASE_URL + "user_";

  /**
   * Retrieves an {@link rx.Observable} which will emit a List of {@link UserEntity}.
   */
  Observable<List<UserEntity>> getUserEntityList();

  /**
   * Retrieves an {@link rx.Observable} which will emit a {@link UserEntity}.
   *
   * @param userId The user id used to get user data.
   */
  Observable<UserEntity> getUserEntityById(final int userId);
}
