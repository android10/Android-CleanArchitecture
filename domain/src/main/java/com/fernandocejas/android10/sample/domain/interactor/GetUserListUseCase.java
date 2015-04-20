/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.interactor;

import com.fernandocejas.android10.sample.domain.UseCaseSubscriber;
import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.repository.UserRepository;
import java.util.List;
import javax.inject.Inject;

/**
 * This class is an implementation of {@link GetUserListUseCase} that represents a use case for
 * retrieving a collection of all {@link User}.
 */
public class GetUserListUseCase implements UseCase {

  private final UserRepository userRepository;
  private final ThreadExecutor threadExecutor;
  private final PostExecutionThread postExecutionThread;

  /**
   * Constructor of the class.
   *
   * @param userRepository A {@link UserRepository} as a source for retrieving data.
   * @param threadExecutor {@link ThreadExecutor} used to execute this use case in a background
   * thread.
   * @param postExecutionThread {@link PostExecutionThread} used to post updates when the use case
   * has been executed.
   */
  @Inject
  public GetUserListUseCase(UserRepository userRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    this.userRepository = userRepository;
    this.threadExecutor = threadExecutor;
    this.postExecutionThread = postExecutionThread;
  }

  @Override public void execute(UseCaseSubscriber subscriber) {
    this.userRepository.getUsers().subscribeOn(null).observeOn(null).subscribe(subscriber);
  }
}
