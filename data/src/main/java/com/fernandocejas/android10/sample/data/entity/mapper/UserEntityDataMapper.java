/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.entity.mapper;

import com.fernandocejas.android10.sample.data.entity.UserEntity;
import com.fernandocejas.android10.sample.domain.User;

/**
 * Mapper class used to transform {@link UserEntity} (in the data layer) to {@link User} in the
 * domain layer.
 */
public class UserEntityDataMapper {

  public UserEntityDataMapper() {
    //empty
  }

  /**
   * Transform a {@link UserEntity} into an {@link User}.
   *
   * @param userEntity Object to be transformed.
   * @return {@link User}.
   */
  public User transform(UserEntity userEntity) {
    User user = null;

    if (userEntity != null) {
    }

    return user;
  }
}
