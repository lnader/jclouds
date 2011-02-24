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

package org.jclouds.softlayer;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.jclouds.concurrent.Timeout;
import org.jclouds.softlayer.domain.VirtualGuest;

/**
 * Provides synchronous access to SoftLayer.
 * <p/>
 * 
 * @see SoftLayerAsyncClient
 * @see <a href="http://sldn.softlayer.com/wiki/index.php/REST" />
 * @author Adrian Cole
 */
@Timeout(duration = 4, timeUnit = TimeUnit.SECONDS)
public interface SoftLayerClient {

   /**
    * 
    * @return an account's associated virtual guest objects.
    */
   Set<VirtualGuest> listVirtualGuests();

   /**
    * 
    * @param id
    *           id of the virtual guest
    * @return virtual guest or null if not found
    */
   VirtualGuest getVirtualGuest(long id);

}
