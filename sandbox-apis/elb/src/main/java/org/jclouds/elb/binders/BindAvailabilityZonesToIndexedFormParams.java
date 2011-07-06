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

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.inject.Singleton;

import org.jclouds.http.HttpRequest;
import org.jclouds.http.utils.ModifyRequest;
import org.jclouds.rest.Binder;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;

/**
 * Binds the String [] to form parameters named with InstanceId.index
 * 
 * @author Adrian Cole
 */
@Singleton
public class BindAvailabilityZonesToIndexedFormParams implements Binder
{
    @Override
    public <R extends HttpRequest> R bindToRequest(R request, Object input)
    {
        String format = "AvailabilityZones.member.%s";
        @SuppressWarnings("unchecked")
        List<String> zones = (List<String>) input;
        Builder<String, String> builder = ImmutableMultimap
                .<String, String> builder();
        int i = 1;
        for (String zone : zones)
        {
            builder.put(String.format(format, i),
                    checkNotNull(zone, format.toLowerCase() + "s[" + i + "]"));
            i++;
        }
        ImmutableMultimap<String, String> forms = builder.build();
        return forms.size() == 0 ? request : ModifyRequest.putFormParams(
                request, forms);
    }

}
