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
package com.fernandocejas.android10.sample.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Class that represents a User in the domain layer.
 */
public class User {

  private final @Getter int userId;

  public User(int userId) {
    this.userId = userId;
  }

  private @Getter @Setter String coverUrl;
  private @Getter @Setter String fullName;
  private @Getter @Setter String email;
  private @Getter @Setter String description;
  private @Getter @Setter int followers;

  @Override public String toString() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("***** User Details *****\n");
    stringBuilder.append("id=" + this.getUserId() + "\n");
    stringBuilder.append("cover url=" + this.getCoverUrl() + "\n");
    stringBuilder.append("fullname=" + this.getFullName() + "\n");
    stringBuilder.append("email=" + this.getEmail() + "\n");
    stringBuilder.append("description=" + this.getDescription() + "\n");
    stringBuilder.append("followers=" + this.getFollowers() + "\n");
    stringBuilder.append("*******************************");

    return stringBuilder.toString();
  }
}
