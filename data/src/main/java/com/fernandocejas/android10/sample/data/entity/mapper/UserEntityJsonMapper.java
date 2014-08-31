/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.entity.mapper;

import com.fernandocejas.android10.sample.data.entity.UserEntity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Class used to transform from Strings representing json to valid objects.
 */
public class UserEntityJsonMapper {

  private final Gson gson;

  public UserEntityJsonMapper() {
    this.gson = new Gson();
  }

  /**
   * Transform from valid json string to {@link UserEntity}.
   *
   * @param userJsonResponse A json representing a user profile.
   * @return {@link UserEntity}.
   * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
   */
  public UserEntity transformUserEntity(String userJsonResponse) throws JsonSyntaxException {
    try {
      Type userEntityType = new TypeToken<UserEntity>() {}.getType();
      UserEntity userEntity = this.gson.fromJson(userJsonResponse, userEntityType);

      return userEntity;
    } catch (JsonSyntaxException jsonException) {
      throw jsonException;
    }
  }

  /**
   * Transform from valid json string to Collection of {@link UserEntity}.
   *
   * @param userListJsonResponse A json representing a collection of users.
   * @return Collection of {@link UserEntity}.
   * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
   */
  public Collection<UserEntity> transformUserEntityCollection(String userListJsonResponse)
      throws JsonSyntaxException {

    Collection<UserEntity> userEntityCollection;
    try {
      Type listOfUserEntityType = new TypeToken<Collection<UserEntity>>() {}.getType();
      userEntityCollection = this.gson.fromJson(userListJsonResponse, listOfUserEntityType);

      return userEntityCollection;
    } catch (JsonSyntaxException jsonException) {
      throw jsonException;
    }
  }
}
