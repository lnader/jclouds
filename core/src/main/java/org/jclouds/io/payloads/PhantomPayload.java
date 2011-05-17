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
package org.jclouds.io.payloads;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.jclouds.io.ContentMetadata;
import org.jclouds.io.MutableContentMetadata;

/**
 * @author Adrian Cole
 */
public class PhantomPayload extends BasePayload<Object> {

   public PhantomPayload() {
      super(Object.class);
   }

   public PhantomPayload(ContentMetadata contentMetadata) {
      this(BaseMutableContentMetadata.fromContentMetadata(checkNotNull(contentMetadata, "contentMetadata")));
   }

   public PhantomPayload(MutableContentMetadata contentMetadata) {
      super(Object.class, checkNotNull(contentMetadata, "contentMetadata"));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public InputStream getInput() {
      throw new UnsupportedOperationException();
   }

   @Override
   public void writeTo(OutputStream outstream) throws IOException {
      throw new UnsupportedOperationException();
   }

}