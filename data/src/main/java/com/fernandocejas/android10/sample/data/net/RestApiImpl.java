/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.fernandocejas.android10.sample.data.entity.UserEntity;
import com.fernandocejas.android10.sample.data.entity.mapper.UserEntityJsonMapper;
import com.fernandocejas.android10.sample.data.exception.NetworkConnectionException;
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
            subscriber.onNext(getUserEntitiesFromApi());
            subscriber.onCompleted();
          } catch (Exception e) {
            subscriber.onError(new NetworkConnectionException(e.getCause()));
          }
        } else {
          subscriber.onError(new NetworkConnectionException());
        }
      }
    });
  }

  private List<UserEntity> getUserEntitiesFromApi() throws MalformedURLException {
    ApiConnection getUserListConnection =
        ApiConnection.createGET(RestApi.API_URL_GET_USER_LIST);
    String responseUserList = getUserListConnection.requestSyncCall();

    return userEntityJsonMapper.transformUserEntityCollection(responseUserList);
  }

  @Override public void getUserById(final int userId,
      final UserDetailsCallback userDetailsCallback) {
    if (userDetailsCallback == null) {
      throw new IllegalArgumentException("Callback cannot be null!!!");
    }

    if (isThereInternetConnection()) {
      try {
        String apiUrl = RestApi.API_URL_GET_USER_DETAILS + userId + ".json";
        ApiConnection getUserDetailsConnection = ApiConnection.createGET(apiUrl);
        String responseUserDetails = getUserDetailsConnection.requestSyncCall();

        if (responseUserDetails != null) {
          UserEntity userEntity =
              this.userEntityJsonMapper.transformUserEntity(responseUserDetails);
          userDetailsCallback.onUserEntityLoaded(userEntity);
        } else {
          userDetailsCallback.onError(new NetworkConnectionException());
        }
      } catch (Exception e) {
        userDetailsCallback.onError(new NetworkConnectionException(e.getCause()));
      }
    } else {
      userDetailsCallback.onError(new NetworkConnectionException());
    }
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
