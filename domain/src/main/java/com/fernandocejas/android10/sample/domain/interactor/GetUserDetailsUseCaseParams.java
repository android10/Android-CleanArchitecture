package com.fernandocejas.android10.sample.domain.interactor;

import lombok.Builder;

/**
 * @author goraczka
 */

@Builder
public class GetUserDetailsUseCaseParams extends UseCase.UseCaseParams {

  private int userId = -1;

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }
}