/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository;

import com.fernandocejas.android10.sample.data.entity.mapper.UserEntityDataMapper;
import com.fernandocejas.android10.sample.data.repository.datasource.UserDataStoreFactory;
import com.fernandocejas.android10.sample.domain.repository.UserRepository;

/**
 * {@link UserRepository} for retrieving user data.
 */
public class UserDataRepository implements UserRepository {

  private static UserDataRepository INSTANCE;

  public static synchronized UserDataRepository getInstance(UserDataStoreFactory dataStoreFactory,
      UserEntityDataMapper userEntityDataMapper) {
    if (INSTANCE == null) {
      INSTANCE = new UserDataRepository(dataStoreFactory, userEntityDataMapper);
    }
    return INSTANCE;
  }

  private final UserDataStoreFactory dataStoreFactory;
  private final UserEntityDataMapper userEntityDataMapper;

  /**
   * Constructs a {@link UserRepository}.
   * @param dataStoreFactory A factory to construct different data source implementations.
   * @param userEntityDataMapper {@link UserEntityDataMapper}.
   */
  protected UserDataRepository(UserDataStoreFactory dataStoreFactory,
      UserEntityDataMapper userEntityDataMapper) {
    if (dataStoreFactory == null || userEntityDataMapper == null) {
      throw new IllegalArgumentException("Invalid null parameters in constructor!!!");
    }
    this.dataStoreFactory = dataStoreFactory;
    this.userEntityDataMapper = userEntityDataMapper;
  }

  @Override public void getUserList(UserListCallback userListCallback) {

  }

  @Override public void getUserById(int userId, UserCallback userCallback) {

  }
}
