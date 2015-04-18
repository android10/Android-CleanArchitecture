/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.repository;

import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import java.util.Collection;
import java.util.List;
import rx.Observable;

/**
 * Interface that represents a Repository for getting {@link User} related data.
 */
public interface UserRepository {
  /**
   * Callback used to be notified when either a user list has been loaded or an error happened.
   */
  interface UserListCallback {
    void onUserListLoaded(Collection<User> usersCollection);

    void onError(ErrorBundle errorBundle);
  }

  /**
   * Callback used to be notified when either a user has been loaded or an error happened.
   */
  interface UserDetailsCallback {
    void onUserLoaded(User user);

    void onError(ErrorBundle errorBundle);
  }

  /**
   * Get an {@link rx.Observable} which will emit a List of {@link User}.
   */
  Observable<List<User>> getUsers();

  /**
   * Get an {@link User} by id.
   *
   * @param userId The user id used to retrieve user data.
   * @param userCallback A {@link UserDetailsCallback} used for notifying clients.
   */
  void getUserById(final int userId, UserDetailsCallback userCallback);
}
