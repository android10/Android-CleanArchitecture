/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.repository;

import com.fernandocejas.android10.sample.domain.User;
import java.util.List;
import rx.Observable;

/**
 * Interface that represents a Repository for getting {@link User} related data.
 */
public interface UserRepository {
  /**
   * Get an {@link rx.Observable} which will emit a List of {@link User}.
   */
  Observable<List<User>> getUsers();

  /**
   * Get an {@link rx.Observable} which will emit a {@link User}.
   *
   * @param userId The user id used to retrieve user data.
   */
  Observable<User> getUser(final int userId);
}
