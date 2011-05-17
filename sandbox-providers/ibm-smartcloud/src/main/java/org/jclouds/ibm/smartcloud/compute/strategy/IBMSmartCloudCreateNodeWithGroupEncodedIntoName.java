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
package org.jclouds.ibm.smartcloud.compute.strategy;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.jclouds.ibm.smartcloud.options.CreateInstanceOptions.Builder.authorizePublicKey;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.strategy.CreateNodeWithGroupEncodedIntoName;
import org.jclouds.ibm.smartcloud.IBMSmartCloudClient;
import org.jclouds.ibm.smartcloud.compute.options.IBMSmartCloudTemplateOptions;
import org.jclouds.ibm.smartcloud.domain.Instance;
import org.jclouds.ibm.smartcloud.options.CreateInstanceOptions;

import com.google.common.base.Function;

/**
 * @author Adrian Cole
 */
@Singleton
public class IBMSmartCloudCreateNodeWithGroupEncodedIntoName implements CreateNodeWithGroupEncodedIntoName {

   private final IBMSmartCloudClient client;
   private final Function<Instance, NodeMetadata> instanceToNodeMetadata;

   @Inject
   protected IBMSmartCloudCreateNodeWithGroupEncodedIntoName(IBMSmartCloudClient client,
            Function<Instance, NodeMetadata> instanceToNodeMetadata) {
      this.client = checkNotNull(client, "client");
      this.instanceToNodeMetadata = checkNotNull(instanceToNodeMetadata, "instanceToNodeMetadata");
   }

   @Override
   public NodeMetadata createNodeWithGroupEncodedIntoName(String group, String name, Template template) {
      IBMSmartCloudTemplateOptions templateOptions = template.getOptions().as(IBMSmartCloudTemplateOptions.class);
      CreateInstanceOptions options = authorizePublicKey(templateOptions.getKeyPair()).isMiniEphemeral(
               IBMSmartCloudTemplateOptions.class.cast(template.getOptions()).isMiniEphemeral());
      Instance instance = client.createInstanceInLocation(template.getLocation().getId(), name, template.getImage()
               .getProviderId(), template.getHardware().getProviderId(), options);
      return instanceToNodeMetadata.apply(instance);
   }
}
