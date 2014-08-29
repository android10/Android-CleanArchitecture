/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.interactor;

import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.repository.UserRepository;

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
  public GetUserDetailsUseCaseImpl(UserRepository userRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    if (userRepository == null || threadExecutor == null || postExecutionThread == null) {
      throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
    }
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
    this.userRepository.getUserById(this.userId, this.repositoryCallback);
  }

  private final UserRepository.UserDetailsCallback repositoryCallback =
      new UserRepository.UserDetailsCallback() {
        @Override public void onUserLoaded(User user) {
          notifyGetUserDetailsSuccessfully(user);
        }

        @Override public void onError(ErrorBundle errorBundle) {
          notifyError(errorBundle);
        }
      };

  private void notifyGetUserDetailsSuccessfully(final User user) {
    this.postExecutionThread.post(new Runnable() {
      @Override public void run() {
        callback.onUserDataLoaded(user);
      }
    });
  }

  private void notifyError(final ErrorBundle errorBundle) {
    this.postExecutionThread.post(new Runnable() {
      @Override public void run() {
        callback.onError(errorBundle);
      }
    });
  }
}
