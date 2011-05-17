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
package org.jclouds.cloudstack.parse;

import org.jclouds.cloudstack.domain.Capabilities;
import org.jclouds.json.BaseItemParserTest;
import org.jclouds.rest.annotations.Unwrap;
import org.testng.annotations.Test;

/**
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit")
public class ListCapabilitiesResponseTest extends BaseItemParserTest<Capabilities> {

   @Override
   public String resource() {
      return "/listcapabilitiesresponse.json";
   }

   @Override
   @Unwrap(depth = 2)
   public Capabilities expected() {
      return Capabilities.builder().securityGroupsEnabled(true).sharedTemplatesEnabled(true).cloudStackVersion("2.2")
            .build();
   }

}
