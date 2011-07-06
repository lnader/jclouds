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

import java.util.Set;

import javax.inject.Singleton;

import org.jclouds.elb.domain.Listener;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.utils.ModifyRequest;
import org.jclouds.rest.Binder;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;

@Singleton
public class BindListenersToIndexedFormParams implements Binder
{
    @Override
    public <R extends HttpRequest> R bindToRequest(R request, Object input)
    {
        @SuppressWarnings("unchecked")
        Set<Listener> listeners = (Set<Listener> )input;
        Builder<String, String> builder = ImmutableMultimap
                .<String, String> builder();
        
        int i = 1;
        for (Listener listener:listeners)
        {
            builder.put(
                    String.format("Listeners.member.%s.%s", i, "InstancePort") , String.valueOf(listener.getInstancePort()));
            builder.put(
                    String.format("Listeners.member.%s.%s", i, "Protocol") , listener.getProtocol());
            builder.put(
                    String.format("Listeners.member.%s.%s", i, "LoadBalancerPort") , String.valueOf(listener.getLoadBalancerPort()));
            i++;
        }
        
        ImmutableMultimap<String, String> forms = builder.build();
        return forms.size() == 0 ? request : ModifyRequest.putFormParams(
                request, forms);
    }

}
