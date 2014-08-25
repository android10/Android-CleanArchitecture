/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.interactor;

import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;

/**
 * This interface represents a execution unit for a use case to get data for an specific user.
 * By convention this use case ({@link Interactor}) implementation will return the result using a
 * callback that should be executed in the UI thread.
 */
public interface GetUserDetailsUseCase {
  /**
   * Callback used to be notified when either a user has been loaded or an error happened.
   */
  interface Callback {
    void onUserDataLoaded(User user);
    void onError(ErrorBundle errorBundle);
  }

  /**
   * Executes this user case.
   * @param userId The user id to retrieve.
   * @param callback A {@link GetUserDetailsUseCase.Callback} used for notify the client.
   */
  public void execute(int userId, Callback callback);
}
