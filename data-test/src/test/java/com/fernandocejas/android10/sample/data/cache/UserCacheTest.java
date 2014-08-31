/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.cache;

import com.fernandocejas.android10.sample.data.ApplicationTestCase;
import com.fernandocejas.android10.sample.data.cache.serializer.JsonSerializer;
import com.fernandocejas.android10.sample.data.entity.UserEntity;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import java.io.File;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

public class UserCacheTest extends ApplicationTestCase {

  private static final int FAKE_USER_ID = 123;

  private UserCache userCache;

  @Mock
  private JsonSerializer mockJsonSerializer;
  @Mock
  private FileManager mockFileManager;
  @Mock
  private ThreadExecutor mockThreadExecutor;
  @Mock
  private UserCache.UserCacheCallback mockUserCacheCallback;
  @Mock
  private UserEntity mockUserEntity;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    userCache = UserCacheImpl.getInstance(Robolectric.application, mockJsonSerializer,
        mockFileManager, mockThreadExecutor);
  }

  @Test
  public void testGetFromCacheHappyCase() {
    given(mockJsonSerializer.deserialize(anyString())).willReturn(mockUserEntity);

    userCache.get(FAKE_USER_ID, mockUserCacheCallback);

    verify(mockFileManager).readFileContent(any(File.class));
    verify(mockJsonSerializer).deserialize(anyString());
    verify(mockUserCacheCallback).onUserEntityLoaded(mockUserEntity);
  }
}
