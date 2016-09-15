/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.sample.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.fernandocejas.android10.sample.data.entity.UserEntity;
import com.fernandocejas.android10.sample.data.entity.mapper.EntityJsonMapper;
import com.fernandocejas.android10.sample.data.exception.NetworkConnectionException;
import com.fernandocejas.frodo.annotation.RxLogObservable;
import java.net.MalformedURLException;
import java.util.List;
import rx.Observable;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApi {

  private final Context context;
  private final EntityJsonMapper<UserEntity> entityJsonMapper;

  /**
   * Constructor of the class
   *
   * @param context {@link android.content.Context}.
   * @param entityJsonMapper {@link EntityJsonMapper}.
   */
  public RestApiImpl(Context context, EntityJsonMapper entityJsonMapper) {
    if (context == null || entityJsonMapper == null) {
      throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
    }
    this.context = context.getApplicationContext();
    this.entityJsonMapper = entityJsonMapper;
  }

  @RxLogObservable
  @Override public Observable<List<UserEntity>> userEntityList() {
    return Observable.create(subscriber -> {
      if (isThereInternetConnection()) {
        try {
          String responseUserEntities = getUserEntitiesFromApi();
          if (responseUserEntities != null) {
            subscriber.onNext(entityJsonMapper.transformUserEntityCollection(
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
    });
  }

  @RxLogObservable
  @Override public Observable<UserEntity> userEntityById(final int userId) {
    return Observable.create(subscriber -> {
      if (isThereInternetConnection()) {
        try {
          String responseUserDetails = getUserDetailsFromApi(userId);
          if (responseUserDetails != null) {
            subscriber.onNext(entityJsonMapper.transformUserEntity(responseUserDetails));
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
    });
  }

  private String getUserEntitiesFromApi() throws MalformedURLException {
    return ApiConnection.createGET(API_URL_GET_USER_LIST).requestSyncCall();
  }

  private String getUserDetailsFromApi(int userId) throws MalformedURLException {
    String apiUrl = API_URL_GET_USER_DETAILS + userId + ".json";
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
