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
import java.util.Collection;
import javax.inject.Inject;

/**
 * This class is an implementation of {@link GetUserListUseCase} that represents a use case for
 * retrieving a collection of all {@link User}.
 */
public class GetUserListUseCaseImpl implements GetUserListUseCase {

  private final UserRepository userRepository;
  private final ThreadExecutor threadExecutor;
  private final PostExecutionThread postExecutionThread;

  private Callback callback;

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
  public GetUserListUseCaseImpl(UserRepository userRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    this.userRepository = userRepository;
    this.threadExecutor = threadExecutor;
    this.postExecutionThread = postExecutionThread;
  }

  @Override public void execute(Callback callback) {
    if (callback == null) {
      throw new IllegalArgumentException("Interactor callback cannot be null!!!");
    }
    this.callback = callback;
    this.threadExecutor.execute(this);
  }

  @Override public void run() {
    this.userRepository.getUserList(this.repositoryCallback);
  }

  private final UserRepository.UserListCallback repositoryCallback =
      new UserRepository.UserListCallback() {
        @Override public void onUserListLoaded(Collection<User> usersCollection) {
          notifyGetUserListSuccessfully(usersCollection);
        }

        @Override public void onError(ErrorBundle errorBundle) {
          notifyError(errorBundle);
        }
      };

  private void notifyGetUserListSuccessfully(final Collection<User> usersCollection) {
    this.postExecutionThread.post(new Runnable() {
      @Override public void run() {
        callback.onUserListLoaded(usersCollection);
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
