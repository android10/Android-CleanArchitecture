/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.interactor;

import com.fernandocejas.android10.sample.domain.repository.UserRepository;
import java.util.Collections;

/**
 *
 */
public class GetUserListUseCaseImpl implements GetUserListUseCase {

  private final UserRepository userRepository;

  public GetUserListUseCaseImpl(UserRepository userRepository) {
    if (userRepository == null) {
      throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
    }
    this.userRepository = userRepository;
  }

  @Override public void run() {

  }

  @Override public void getUserList(Callback callback) {
    //doing some fake job
    try {
      Thread.sleep(6000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    // should this be in a new thread?
    callback.onUserListLoaded(Collections.unmodifiableCollection(Collections.EMPTY_LIST));
  }
}
