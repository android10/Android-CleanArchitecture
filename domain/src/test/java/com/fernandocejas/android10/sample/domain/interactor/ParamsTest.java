/**
 * Copyright (C) 2016 Fernando Cejas Open Source Project
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
package com.fernandocejas.android10.sample.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ParamsTest {

  private Params params;

  @Before
  public void setUp() {
    params = Params.create();
  }

  @Test
  public void testShouldReturnIntValue() {
    params.putInt("key01", 3);

    assertThat(params.getInt("key01", 5)).isEqualTo(3);
  }

  @Test
  public void testShouldReturnIntDefaultValue() {
    params.putInt("key01", 3);
    params.putInt("key02", 4);

    assertThat(params.getInt("key03", 5)).isEqualTo(5);
  }
}
