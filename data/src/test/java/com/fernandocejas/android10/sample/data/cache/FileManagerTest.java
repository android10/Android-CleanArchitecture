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
package com.fernandocejas.android10.sample.data.cache;

import com.fernandocejas.android10.sample.data.ApplicationTestCase;
import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileManagerTest extends ApplicationTestCase {

  private FileManager fileManager;

  @Before
  public void setUp() {
    fileManager = new FileManager();
  }

  @After
  public void tearDown() {
    if (cacheDir() != null) {
      fileManager.clearDirectory(cacheDir());
    }
  }

  @Test
  public void testWriteToFile() {
    File fileToWrite = createDummyFile();
    String fileContent = "content";

    fileManager.writeToFile(fileToWrite, fileContent);

    assertThat(fileToWrite.exists(), is(true));
  }

  @Test
  public void testFileContent() {
    File fileToWrite = createDummyFile();
    String fileContent = "content\n";

    fileManager.writeToFile(fileToWrite, fileContent);
    String expectedContent = fileManager.readFileContent(fileToWrite);

    assertThat(expectedContent, is(equalTo(fileContent)));
  }

  private File createDummyFile() {
    String dummyFilePath = cacheDir().getPath() + File.separator + "dummyFile";
    return new File(dummyFilePath);
  }
}
