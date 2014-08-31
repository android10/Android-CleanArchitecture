/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.entity.mapper;

import com.fernandocejas.android10.sample.data.ApplicationTestCase;
import com.fernandocejas.android10.sample.data.entity.UserEntity;
import com.fernandocejas.android10.sample.domain.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class UserEntityDataMapperTest extends ApplicationTestCase {

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
