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
package org.jclouds.elb.binders;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Singleton;

import org.jclouds.http.HttpRequest;
import org.jclouds.http.utils.ModifyRequest;
import org.jclouds.rest.Binder;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;


@Singleton
public class BindListenerPortsToIndexedFormParams implements Binder {
   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object input) {
      return indexLongArrayToFormValuesWithStringFormat(request, "LoadBalancerPorts.member.%s", input);
   }
   
   public static <R extends HttpRequest> R indexLongArrayToFormValuesWithStringFormat(R request, String format,
           Object input) {
        checkArgument(checkNotNull(input, "input") instanceof Long[], "this binder is only valid for Long[] : "
              + input.getClass());
        Long[] values = (Long[]) input;
        Builder<String, String> builder = ImmutableMultimap.<String, String> builder();
        for (int i = 0; i < values.length; i++) {
           builder.put(String.format(format, (i + 1)), checkNotNull(String.valueOf(values[i]), format.toLowerCase() + "s[" + i + "]"));
        }
        ImmutableMultimap<String, String> forms = builder.build();
        return forms.size() == 0 ? request : ModifyRequest.putFormParams(request, forms);
     }

}
