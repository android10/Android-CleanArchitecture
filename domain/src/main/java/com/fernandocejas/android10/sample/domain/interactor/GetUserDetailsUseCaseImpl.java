/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.interactor;

import com.fernandocejas.android10.sample.domain.DefaultSubscriber;
import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.repository.UserRepository;
import javax.inject.Inject;

/**
 * This class is an implementation of {@link GetUserDetailsUseCase} that represents a use case for
 * retrieving data related to an specific {@link User}.
 */
public class GetUserDetailsUseCaseImpl implements GetUserDetailsUseCase {

  private final UserRepository userRepository;
  private final ThreadExecutor threadExecutor;
  private final PostExecutionThread postExecutionThread;

  private int userId = -1;
  private GetUserDetailsUseCase.Callback callback;

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
  public GetUserDetailsUseCaseImpl(UserRepository userRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    this.userRepository = userRepository;
    this.threadExecutor = threadExecutor;
    this.postExecutionThread = postExecutionThread;
  }

  @Override public void execute(int userId, Callback callback) {
    if (userId < 0 || callback == null) {
      throw new IllegalArgumentException("Invalid parameter!!!");
    }
    this.userId = userId;
    this.callback = callback;
    this.threadExecutor.execute(this);
  }

  @Override public void run() {
    this.userRepository.getUser(this.userId)
        .subscribe(new GetUserDetailsSubscriber(this.callback, this.postExecutionThread));
  }

  private static class GetUserDetailsSubscriber extends DefaultSubscriber<User> {
    private final Callback callback;
    private final PostExecutionThread postExecutionThread;

    public GetUserDetailsSubscriber(Callback callback,
        PostExecutionThread postExecutionThread) {
      this.callback = callback;
      this.postExecutionThread = postExecutionThread;
    }

    @Override public void onError(final Throwable e) {
      this.postExecutionThread.post(new Runnable() {
        @Override public void run() {
          callback.onError(new DefaultErrorBundle((Exception) e));
        }
      });
    }

    @Override public void onNext(final User user) {
      this.postExecutionThread.post(new Runnable() {
        @Override public void run() {
          callback.onUserDataLoaded(user);
        }
      });
    }
  }
}
