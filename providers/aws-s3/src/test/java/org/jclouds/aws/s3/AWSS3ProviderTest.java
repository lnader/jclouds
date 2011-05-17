/**
 *
 * Copyright (C) 2011 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
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
 * ====================================================================
 */
package org.jclouds.aws.s3;

import org.jclouds.providers.BaseProviderMetadataTest;
import org.jclouds.providers.ProviderMetadata;
import org.testng.annotations.Test;

/**
 * The AWSS3ProviderTest tests the org.jclouds.providers.AWSS3Provider class.
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "AWSS3ProviderTest")
public class AWSS3ProviderTest extends BaseProviderMetadataTest {

   public AWSS3ProviderTest() {
      super(new AWSS3ProviderMetadata(), ProviderMetadata.BLOBSTORE_TYPE);
   }
}