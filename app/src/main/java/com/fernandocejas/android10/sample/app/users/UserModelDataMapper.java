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
package com.fernandocejas.android10.sample.app.users;

import com.fernandocejas.android10.sample.app.core.di.PerActivity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.inject.Inject;

/**
 * Mapper class used to transform {@link User} (in the domain layer) to {@link UserModel} in the
 * presentation layer.
 */
@PerActivity
class UserModelDataMapper {

  @Inject
  UserModelDataMapper() {}

  /**
   * Transform a {@link User} into an {@link UserModel}.
   *
   * @param user Object to be transformed.
   * @return {@link UserModel}.
   */
  UserModel transform(User user) {
    if (user == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    final UserModel userModel = new UserModel(user.getUserId());
    userModel.setCoverUrl(user.getCoverUrl());
    userModel.setFullName(user.getFullName());
    userModel.setEmail(user.getEmail());
    userModel.setDescription(user.getDescription());
    userModel.setFollowers(user.getFollowers());

    return userModel;
  }

  /**
   * Transform a Collection of {@link User} into a Collection of {@link UserModel}.
   *
   * @param usersCollection Objects to be transformed.
   * @return List of {@link UserModel}.
   */
  Collection<UserModel> transform(Collection<User> usersCollection) {
    Collection<UserModel> userModelsCollection;

    if (usersCollection != null && !usersCollection.isEmpty()) {
      userModelsCollection = new ArrayList<>();
      for (User user : usersCollection) {
        userModelsCollection.add(transform(user));
      }
    } else {
      userModelsCollection = Collections.emptyList();
    }

    return userModelsCollection;
  }
}
