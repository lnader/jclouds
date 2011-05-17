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
package org.jclouds.atmos.functions;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.jclouds.util.Throwables2.propagateOrNull;

import java.net.URI;

import javax.annotation.Nullable;

import org.jclouds.blobstore.KeyAlreadyExistsException;
import org.jclouds.http.HttpRequest;
import org.jclouds.rest.InvocationContext;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;

/**
 * 
 * @author Adrian Cole
 */
public class ReturnEndpointIfAlreadyExists implements Function<Exception, URI>,
      InvocationContext<ReturnEndpointIfAlreadyExists> {

   private URI endpoint;

   public URI apply(Exception from) {
      if (checkNotNull(from, "exception") instanceof KeyAlreadyExistsException) {
         return endpoint;
      }
      return URI.class.cast(propagateOrNull(from));
   }

   @Override
   public ReturnEndpointIfAlreadyExists setContext(HttpRequest request) {
      return setEndpoint(request == null ? null : request.getEndpoint());
   }

   @VisibleForTesting
   ReturnEndpointIfAlreadyExists setEndpoint(@Nullable URI endpoint) {
      this.endpoint = endpoint;
      return this;
   }

}
