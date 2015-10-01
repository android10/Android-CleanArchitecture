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
package com.fernandocejas.android10.sample.data.net;

import com.fernandocejas.android10.sample.data.entity.UserEntity;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import rx.Observable;

/**
 * RestApiService for retrieving data from the network.
 */
public interface RestApi {
  String API_BASE_URL = "http://www.android10.org/myapi/";

  String CONTENT_TYPE_JSON_HEADER = "Content-Type: application/json; charset=utf-8";

  /**
   * Retrieves an {@link rx.Observable} which will emit a List of {@link UserEntity}.
   */
  @Headers(CONTENT_TYPE_JSON_HEADER)
  @GET("users.json")
  Observable<List<UserEntity>> userEntityList();

  /**
   * Retrieves an {@link rx.Observable} which will emit a {@link UserEntity}.
   *
   * @param userId The user id used to get user data.
   */
  @Headers(CONTENT_TYPE_JSON_HEADER)
  @GET("user_{id}.json")
  Observable<UserEntity> userEntityById(@Path("id") final int userId);
}
