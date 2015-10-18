/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.sample.data.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * User Entity used in the data layer.
 */
public class UserEntity {

  @SerializedName("id") @Getter @Setter private int userId;

  @SerializedName("cover_url") @Getter @Setter private String coverUrl;

  @SerializedName("full_name") @Getter @Setter private String fullname;

  @SerializedName("description") @Getter @Setter private String description;

  @SerializedName("followers") @Getter @Setter private int followers;

  @SerializedName("email") @Getter @Setter private String email;

  @Override public String toString() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("***** User Entity Details *****\n");
    stringBuilder.append("id=" + userId + "\n");
    stringBuilder.append("cover url=" + coverUrl + "\n");
    stringBuilder.append("fullname=" + fullname + "\n");
    stringBuilder.append("email=" + email + "\n");
    stringBuilder.append("description=" + description + "\n");
    stringBuilder.append("followers=" + followers + "\n");
    stringBuilder.append("*******************************");

    return stringBuilder.toString();
  }
}
