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
package com.codecomputerlove.androidbase.domain.exception;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class DefaultErrorBundleTest {
  private DefaultErrorBundle defaultErrorBundle;

  @Mock
  private Exception mockException;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    defaultErrorBundle = new DefaultErrorBundle(mockException);
  }

  @Test
  public void testGetErrorMessageInteraction() {
    defaultErrorBundle.getErrorMessage();

    verify(mockException).getMessage();
  }
}