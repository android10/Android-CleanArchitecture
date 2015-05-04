/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codecomputerlove.androidbase.data.entity.mapper;

import com.codecomputerlove.androidbase.data.entity.UserEntity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import javax.inject.Inject;

/**
 * Class used to transform from Strings representing json to valid objects.
 */
public class UserEntityJsonMapper {

  private final Gson gson;

  @Inject
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
   * Transform from valid json string to List of {@link UserEntity}.
   *
   * @param userListJsonResponse A json representing a collection of users.
   * @return List of {@link UserEntity}.
   * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
   */
  public List<UserEntity> transformUserEntityCollection(String userListJsonResponse)
      throws JsonSyntaxException {

    List<UserEntity> userEntityCollection;
    try {
      Type listOfUserEntityType = new TypeToken<List<UserEntity>>() {}.getType();
      userEntityCollection = this.gson.fromJson(userListJsonResponse, listOfUserEntityType);

      return userEntityCollection;
    } catch (JsonSyntaxException jsonException) {
      throw jsonException;
    }
  }
}
