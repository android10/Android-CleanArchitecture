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
import com.fernandocejas.android10.sample.domain.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class UserEntityDataMapperTest {

  private static final int FAKE_USER_ID = 123;
  private static final String FAKE_FULLNAME = "Tony Stark";

  private UserEntityDataMapper userEntityDataMapper;

  @Before
  public void setUp() throws Exception {
    userEntityDataMapper = new UserEntityDataMapper();
  }

  @Test
  public void testTransformUserEntity() {
    UserEntity userEntity = createFakeUserEntity();
    User user = userEntityDataMapper.transform(userEntity);

    assertThat(user, is(instanceOf(User.class)));
    assertThat(user.getUserId(), is(FAKE_USER_ID));
    assertThat(user.getFullName(), is(FAKE_FULLNAME));
  }

  @Test
  public void testTransformUserEntityCollection() {
    UserEntity mockUserEntityOne = mock(UserEntity.class);
    UserEntity mockUserEntityTwo = mock(UserEntity.class);

    List<UserEntity> userEntityList = new ArrayList<UserEntity>(5);
    userEntityList.add(mockUserEntityOne);
    userEntityList.add(mockUserEntityTwo);

    Collection<User> userCollection = userEntityDataMapper.transform(userEntityList);

    assertThat(userCollection.toArray()[0], is(instanceOf(User.class)));
    assertThat(userCollection.toArray()[1], is(instanceOf(User.class)));
    assertThat(userCollection.size(), is(2));
  }

  private UserEntity createFakeUserEntity() {
    UserEntity userEntity = new UserEntity();
    userEntity.setUserId(FAKE_USER_ID);
    userEntity.setFullname(FAKE_FULLNAME);

    return userEntity;
  }
}
