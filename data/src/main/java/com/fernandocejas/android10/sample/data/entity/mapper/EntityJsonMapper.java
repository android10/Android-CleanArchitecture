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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Class used to transform from Strings representing json to valid objects.
 */
public class EntityJsonMapper<T> {

  private final Gson gson;
  private final Type type;
  private ParameterizedType parameterizedType;

  public EntityJsonMapper(Class<T> clazz) {
    this.gson = new Gson();
    this.type = clazz;
  }

  /**
   * Transform from valid json string to an object type {@link T}.
   *
   * @param json A json representing an object type {@link T}.
   * @return {@link T}.
   * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
   */
  public T transformEntity(String json) throws JsonSyntaxException {
    try {
      return gson.fromJson(json, type);
    } catch (JsonSyntaxException jsonException) {
      throw jsonException;
    }
  }

  /**
   * Transform from valid json string to List of {@link T}.
   *
   * @param json A json representing a collection of objects type {@link T}.
   * @return List of {@link T}.
   * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
   */
  public List<T> transformEntityCollection(String json)
      throws JsonSyntaxException {
    try {
      return gson.fromJson(json, getParameterizedType());
    } catch (JsonSyntaxException jsonException) {
      throw jsonException;
    }
  }

  private ParameterizedType getParameterizedType() {
    if (parameterizedType == null) {
      parameterizedType = new ParameterizedType() {
        @Override public Type[] getActualTypeArguments() {
          return new Type[] {type};
        }

        @Override public Type getOwnerType() {
          return null;
        }

        @Override public Type getRawType() {
          return List.class;
        }
      };
    }

    return parameterizedType;
  }
}
