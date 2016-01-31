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
package com.fernandocejas.android10.sample.app.data.cache.serializer;

import com.fernandocejas.android10.sample.app.ApplicationTestCase;
import com.fernandocejas.android10.sample.app.data.entity.UserEntity;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JsonSerializerTest extends ApplicationTestCase {

  private static final String JSON_RESPONSE = "{\n"
      + "    \"id\": 1,\n"
      + "    \"cover_url\": \"http://www.android10.org/myapi/cover_1.jpg\",\n"
      + "    \"full_name\": \"Simon Hill\",\n"
      + "    \"description\": \"Curabitur gravida nisi at nibh. In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem.\\n\\nInteger tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat.\\n\\nPraesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede.\",\n"
      + "    \"followers\": 7484,\n"
      + "    \"email\": \"jcooper@babbleset.edu\"\n"
      + "}";

  private JsonSerializer jsonSerializer;

  @Before
  public void setUp() {
    jsonSerializer = new JsonSerializer();
  }

  @Test
  public void testSerializeHappyCase() {
    UserEntity userEntityOne = jsonSerializer.deserialize(JSON_RESPONSE);
    String jsonString = jsonSerializer.serialize(userEntityOne);
    UserEntity userEntityTwo = jsonSerializer.deserialize(jsonString);

    assertThat(userEntityOne.getUserId(), is(userEntityTwo.getUserId()));
    assertThat(userEntityOne.getFullname(), is(equalTo(userEntityTwo.getFullname())));
    assertThat(userEntityOne.getFollowers(), is(userEntityTwo.getFollowers()));
  }

  @Test
  public void testDesearializeHappyCase() {
    UserEntity userEntity = jsonSerializer.deserialize(JSON_RESPONSE);

    assertThat(userEntity.getUserId(), is(1));
    assertThat(userEntity.getFullname(), is("Simon Hill"));
    assertThat(userEntity.getFollowers(), is(7484));
  }
}
