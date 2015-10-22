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
package com.fernandocejas.android10.sample.data.entity.mapper;

import com.fernandocejas.android10.sample.data.entity.UserEntity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import javax.inject.Inject;

/**
 * Class used to transform from Strings representing json to valid objects.
 */
public class UserEntityJsonMapper implements EntityJsonMapper<UserEntity>{

  private final Gson gson;

  @Inject
  public UserEntityJsonMapper() {
    this.gson = new Gson();
  }

  @Override
  public UserEntity jsonToEntity(String userJsonResponse) throws JsonSyntaxException {

    Type userEntityType = new TypeToken<UserEntity>() {}.getType();

    return this.gson.fromJson(userJsonResponse, userEntityType);
  }

  @Override
  public List<UserEntity> jsonToEntityList(String userListJsonResponse)
      throws JsonSyntaxException {

    List<UserEntity> userEntityCollection;
    Type listOfUserEntityType = new TypeToken<List<UserEntity>>() {}.getType();
    userEntityCollection = this.gson.fromJson(userListJsonResponse, listOfUserEntityType);

    return userEntityCollection;
  }
}
