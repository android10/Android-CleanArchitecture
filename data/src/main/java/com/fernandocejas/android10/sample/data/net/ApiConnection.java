/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.net;

import android.support.annotation.Nullable;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Api Connection class used to retrieve data from the cloud.
 * Implements {@link java.util.concurrent.Callable} so when executed asynchronously can
 * return a value.
 */
public class ApiConnection implements Callable<String> {

  private static final String CONTENT_TYPE_LABEL = "Content-Type";
  private static final String CONTENT_TYPE_VALUE_JSON = "application/json; charset=utf-8";

  private URL url;
  private String response;

  private ApiConnection(String url) throws MalformedURLException {
    this.url = new URL(url);
  }

  public static ApiConnection createGET(String url) throws MalformedURLException {
    return new ApiConnection(url);
  }

  /**
   * Do a request to an api synchronously.
   * It should not be executed in the main thread of the application.
   *
   * @return A string response
   */
  @Nullable
  public String requestSyncCall() {
    connectToApi();
    return response;
  }

  private void connectToApi() {
    OkHttpClient okHttpClient = this.createClient();
    final Request request = new Request.Builder()
        .url(this.url)
        .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
        .get()
        .build();

    try {
      this.response = okHttpClient.newCall(request).execute().body().string();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private OkHttpClient createClient() {
    final OkHttpClient okHttpClient = new OkHttpClient();
    okHttpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);
    okHttpClient.setConnectTimeout(15000, TimeUnit.MILLISECONDS);

    return okHttpClient;
  }

  @Override public String call() throws Exception {
    return requestSyncCall();
  }
}
