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
package org.jclouds.cloudsigma.functions;

import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Singleton;

import org.jclouds.cloudsigma.domain.StaticIPInfo;
import org.jclouds.logging.Logger;

import com.google.common.base.Function;
import com.google.common.base.Splitter;

/**
 * 
 * @author Adrian Cole
 */
@Singleton
public class MapToStaticIPInfo implements Function<Map<String, String>, StaticIPInfo> {

   @Resource
   protected Logger logger = Logger.NULL;

   @Override
   public StaticIPInfo apply(Map<String, String> from) {
      if (from.size() == 0)
         return null;
      if (from.size() == 0)
         return null;
      StaticIPInfo.Builder builder = new StaticIPInfo.Builder();
      builder.ip(from.get("resource"));
      builder.user(from.get("user"));
      builder.netmask(from.get("netmask"));
      builder.nameservers(Splitter.on(' ').split(from.get("nameserver")));
      builder.gateway(from.get("gateway"));

      try {
         return builder.build();
      } catch (NullPointerException e) {
         logger.trace("entry missing data: %s; %s", e.getMessage(), from);
         return null;
      }
   }
}