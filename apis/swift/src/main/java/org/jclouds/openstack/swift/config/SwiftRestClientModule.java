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
package org.jclouds.openstack.swift.config;

import java.net.URI;
import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.jclouds.Constants;
import org.jclouds.http.RequiresHttp;
import org.jclouds.openstack.OpenStackAuthAsyncClient.AuthenticationResponse;
import org.jclouds.openstack.config.OpenStackAuthenticationModule.GetAuthenticationResponse;
import org.jclouds.openstack.reference.AuthHeaders;
import org.jclouds.openstack.swift.CommonSwiftAsyncClient;
import org.jclouds.openstack.swift.CommonSwiftClient;
import org.jclouds.openstack.swift.SwiftAsyncClient;
import org.jclouds.openstack.swift.SwiftClient;
import org.jclouds.rest.AsyncClientFactory;
import org.jclouds.rest.ConfiguresRestClient;

import com.google.inject.Provides;

/**
 * 
 * @author Adrian Cole
 */
@ConfiguresRestClient
@RequiresHttp
public class SwiftRestClientModule extends BaseSwiftRestClientModule<SwiftClient, SwiftAsyncClient> {

   public SwiftRestClientModule() {
      super(SwiftClient.class, SwiftAsyncClient.class);
   }

   @Provides
   @Singleton
   CommonSwiftClient provideCommonSwiftClient(SwiftClient in) {
      return in;
   }

   @Provides
   @Singleton
   CommonSwiftAsyncClient provideCommonSwiftClient(SwiftAsyncClient in) {
      return in;
   }

   @Singleton
   public static class GetAuthenticationResponseForStorage extends GetAuthenticationResponse {
      @Inject
      public GetAuthenticationResponseForStorage(AsyncClientFactory factory,
               @Named(Constants.PROPERTY_IDENTITY) String user, @Named(Constants.PROPERTY_CREDENTIAL) String key) {
         super(factory, user, key);
      }

      protected Future<AuthenticationResponse> authenticate() {
         return client.authenticateStorage(user, key);
      }

   }

   protected URI provideStorageUrl(AuthenticationResponse response) {
      return response.getServices().get(AuthHeaders.STORAGE_URL);
   }
   
   @Override
   protected void configure() {
      bind(GetAuthenticationResponse.class).to(GetAuthenticationResponseForStorage.class);
      super.configure();
   }
}
