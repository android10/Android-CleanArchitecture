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

/**
 * Class that represents a user in the presentation layer.
 */
public class UserModel {

  private final int userId;

  public UserModel(int userId) {
    this.userId = userId;
  }

  private String coverUrl;
  private String fullName;
  private String email;
  private String description;
  private int followers;

  public int getUserId() {
    return userId;
  }

  String getCoverUrl() {
    return coverUrl;
  }

  void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
  }

  String getFullName() {
    return fullName;
  }

  void setFullName(String fullName) {
    this.fullName = fullName;
  }

  String getEmail() {
    return email;
  }

  void setEmail(String email) {
    this.email = email;
  }

  String getDescription() {
    return description;
  }

  void setDescription(String description) {
    this.description = description;
  }

  int getFollowers() {
    return followers;
  }

  void setFollowers(int followers) {
    this.followers = followers;
  }
}
