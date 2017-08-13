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
package com.fernandocejas.android10.sample.test.mapper;

import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.presentation.mapper.UserModelDataMapper;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import junit.framework.TestCase;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class UserModelDataMapperTest extends TestCase {

  private static final int FAKE_USER_ID = 123;
  private static final String FAKE_FULL_NAME = "Tony Stark";

  private UserModelDataMapper userModelDataMapper;

  @Override protected void setUp() throws Exception {
    super.setUp();
    userModelDataMapper = new UserModelDataMapper();
  }

  public void testTransformUser() {
    User user = createFakeUser();
    UserModel userModel = userModelDataMapper.transform(user);

    assertThat(userModel, is(instanceOf(UserModel.class)));
    assertThat(userModel.getUserId(), is(FAKE_USER_ID));
    assertThat(userModel.getFullName(), is(FAKE_FULL_NAME));
  }

  public void testTransformUserCollection() {
    User mockUserOne = mock(User.class);
    User mockUserTwo = mock(User.class);

    List<User> userList = new ArrayList<User>(5);
    userList.add(mockUserOne);
    userList.add(mockUserTwo);

    Collection<UserModel> userModelList = userModelDataMapper.transform(userList);

    assertThat(userModelList.toArray()[0], is(instanceOf(UserModel.class)));
    assertThat(userModelList.toArray()[1], is(instanceOf(UserModel.class)));
    assertThat(userModelList.size(), is(2));
  }

  private User createFakeUser() {
    User user = new User(FAKE_USER_ID);
    user.setFullName(FAKE_FULL_NAME);

    return user;
  }
}
