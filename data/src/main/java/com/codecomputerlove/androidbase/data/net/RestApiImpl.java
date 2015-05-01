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
package com.codecomputerlove.androidbase.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.codecomputerlove.androidbase.data.entity.UserEntity;
import com.codecomputerlove.androidbase.data.entity.mapper.UserEntityJsonMapper;
import com.codecomputerlove.androidbase.data.exception.NetworkConnectionException;
import java.net.MalformedURLException;
import java.util.List;
import rx.Observable;
import rx.Subscriber;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApi {

  private final Context context;
  private final UserEntityJsonMapper userEntityJsonMapper;

  /**
   * Constructor of the class
   *
   * @param context {@link android.content.Context}.
   * @param userEntityJsonMapper {@link UserEntityJsonMapper}.
   */
  public RestApiImpl(Context context, UserEntityJsonMapper userEntityJsonMapper) {
    if (context == null || userEntityJsonMapper == null) {
      throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
    }
    this.context = context.getApplicationContext();
    this.userEntityJsonMapper = userEntityJsonMapper;
  }

  @Override public Observable<List<UserEntity>> getUserEntityList() {
    return Observable.create(new Observable.OnSubscribe<List<UserEntity>>() {
      @Override public void call(Subscriber<? super List<UserEntity>> subscriber) {

        if (isThereInternetConnection()) {
          try {
            String responseUserEntities = getUserEntitiesFromApi();
            if (responseUserEntities != null) {
              subscriber.onNext(userEntityJsonMapper.transformUserEntityCollection(
                  responseUserEntities));
              subscriber.onCompleted();
            } else {
              subscriber.onError(new NetworkConnectionException());
            }
          } catch (Exception e) {
            subscriber.onError(new NetworkConnectionException(e.getCause()));
          }
        } else {
          subscriber.onError(new NetworkConnectionException());
        }
      }
    });
  }

  @Override public Observable<UserEntity> getUserEntityById(final int userId) {
    return Observable.create(new Observable.OnSubscribe<UserEntity>() {
      @Override public void call(Subscriber<? super UserEntity> subscriber) {

        if (isThereInternetConnection()) {
          try {
            String responseUserDetails = getUserDetailsFromApi(userId);
            if (responseUserDetails != null) {
              subscriber.onNext(userEntityJsonMapper.transformUserEntity(responseUserDetails));
              subscriber.onCompleted();
            } else {
              subscriber.onError(new NetworkConnectionException());
            }
          } catch (Exception e) {
            subscriber.onError(new NetworkConnectionException(e.getCause()));
          }
        } else {
          subscriber.onError(new NetworkConnectionException());
        }
      }
    });
  }

  private String getUserEntitiesFromApi() throws MalformedURLException {
    return ApiConnection.createGET(RestApi.API_URL_GET_USER_LIST).requestSyncCall();
  }

  private String getUserDetailsFromApi(int userId) throws MalformedURLException {
    String apiUrl = RestApi.API_URL_GET_USER_DETAILS + userId + ".json";
    return ApiConnection.createGET(apiUrl).requestSyncCall();
  }

  /**
   * Checks if the device has any active internet connection.
   *
   * @return true device with internet connection, otherwise false.
   */
  private boolean isThereInternetConnection() {
    boolean isConnected;

    ConnectivityManager connectivityManager =
        (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

    return isConnected;
  }
}
