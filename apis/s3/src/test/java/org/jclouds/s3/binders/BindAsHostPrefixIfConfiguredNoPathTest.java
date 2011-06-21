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
package org.jclouds.s3.binders;

import static org.jclouds.s3.reference.S3Constants.PROPERTY_S3_VIRTUAL_HOST_BUCKETS;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.jclouds.rest.internal.GeneratedHttpRequest;
import org.jclouds.rest.internal.RestAnnotationProcessor;
import org.jclouds.s3.BaseS3AsyncClientTest;
import org.jclouds.s3.S3AsyncClient;
import org.testng.annotations.Test;

import com.google.inject.TypeLiteral;

/**
 * Tests behavior of {@code BindAsHostPrefixIfConfigured}
 * 
 * @author Adrian Cole
 */
// NOTE:without testName, this will not call @Before* and fail w/NPE during surefire
@Test(groups = "unit", testName = "BindAsHostPrefixIfConfiguredNoPathTest")
public class BindAsHostPrefixIfConfiguredNoPathTest extends BaseS3AsyncClientTest<S3AsyncClient> {

   @Override
   protected TypeLiteral<RestAnnotationProcessor<S3AsyncClient>> createTypeLiteral() {
      return new TypeLiteral<RestAnnotationProcessor<S3AsyncClient>>() {
      };
   }

   public void testBucketWithHostnameStyle() throws IOException, SecurityException, NoSuchMethodException {

      Method method = S3AsyncClient.class.getMethod("deleteObject", String.class, String.class);
      GeneratedHttpRequest<S3AsyncClient> request = processor.createRequest(method, "testbucket.example.com", "test.jpg");
      assertRequestLineEquals(request, "DELETE https://s3.amazonaws.com/testbucket.example.com/test.jpg HTTP/1.1");
   }


   @Override
   protected Properties getProperties() {
      Properties properties = super.getProperties();
      properties.setProperty(PROPERTY_S3_VIRTUAL_HOST_BUCKETS, "false");
      return properties;
   }

}
