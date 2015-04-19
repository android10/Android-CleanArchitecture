/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.repository.datasource;

import com.fernandocejas.android10.sample.data.ApplicationTestCase;
import com.fernandocejas.android10.sample.data.cache.UserCache;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class DiskUserDataStoreTest extends ApplicationTestCase {

  private static final int FAKE_USER_ID = 11;

  private DiskUserDataStore diskUserDataStore;

  @Mock private UserCache mockUserCache;

  @Rule public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    diskUserDataStore = new DiskUserDataStore(mockUserCache);
  }

  @Test
  public void testGetUserEntityListUnsupported() {
    expectedException.expect(UnsupportedOperationException.class);
    diskUserDataStore.getUserEntityList();
  }

  @Test
  public void testGetUserEntityDetailesFromCache() {
    diskUserDataStore.getUserEntityDetails(FAKE_USER_ID);
    verify(mockUserCache).get(FAKE_USER_ID);
  }
}
