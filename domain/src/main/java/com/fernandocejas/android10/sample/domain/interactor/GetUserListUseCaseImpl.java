/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.interactor;

import com.fernandocejas.android10.sample.domain.repository.UserRepository;

/**
 *
 */
public class GetUserListUseCaseImpl implements GetUserListUseCase {

  private final UserRepository userRepository;

  public GetUserListUseCaseImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override public void run() {

  }
}
