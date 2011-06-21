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
package org.jclouds.elb.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.common.annotations.Beta;

/**
 * AvailabilityZones:       Specifies a list of Availability Zones. Type: String list
 * CreatedTime:             Provides the date and time the LoadBalancer was created. Type: DateTime
 * DNSName:                 Specifies the external DNS name associated with the LoadBalancer. Type: String 
 * HealthCheck:             Specifies information regarding the various health probes conducted on the LoadBalancer. Type: HealthCheck
 * Instances:               Provides a list of EC2 instance IDs for the LoadBalancer. Type: Instance list 
 * ListenerDescriptions:    LoadBalancerPort, InstancePort, Protocol, and PolicyNames are returned in a list 
 *                          of tuples in the ListenerDescriptions element. Type: ListenerDescription list 
 * LoadBalancerName:        Specifies the name associated with the LoadBalancer. Type: String 
 * Policies:                Provides a list of policies defined for the LoadBalancer. Type: Policies
 * 
 * @author Lili Nader
 */
@Beta
public class LoadBalancer
{

    private String region;
    private String name;
    private Set<String> instanceIds = new HashSet<String>();
    private Set<String> availabilityZones = new HashSet<String>();
    private String dnsName;
    private Set<ListenerDescription> listeners = new HashSet<ListenerDescription>();
    private HealthCheck healthCheck;
    private Set<Policy> policies = new HashSet<Policy>();
    private Date createdTime;
    
    public LoadBalancer(){}

    public LoadBalancer(String region, String name, Set<String> instanceIds,
            Set<String> availabilityZones, String dnsName,
            Set<ListenerDescription> listeners, HealthCheck healthCheck,
            Set<Policy> policies, Date createdTime)
    {
        this.region = region;
        this.name = name;
        this.instanceIds = instanceIds;
        this.availabilityZones = availabilityZones;
        this.dnsName = dnsName;
        this.listeners = listeners;
        this.healthCheck = healthCheck;
        this.policies = policies;
        this.createdTime = createdTime;
    }
    
    public LoadBalancer(String region, String name, Set<String> instanceIds,
            Set<String> availabilityZones, String dnsName, Date createdTime)
    {
        this.region = region;
        this.name = name;
        this.instanceIds = instanceIds;
        this.availabilityZones = availabilityZones;
        this.dnsName = dnsName;
        this.createdTime = createdTime;
    }

    public String getRegion()
    {
        return region;
    }

    public void setRegion(String region)
    {
        this.region = region;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Set<String> getInstanceIds()
    {
        return instanceIds;
    }

    public void setInstanceIds(Set<String> instanceIds)
    {
        this.instanceIds = instanceIds;
    }

    public Set<String> getAvailabilityZones()
    {
        return availabilityZones;
    }

    public void setAvailabilityZones(Set<String> availabilityZones)
    {
        this.availabilityZones = availabilityZones;
    }

    public String getDnsName()
    {
        return dnsName;
    }

    public void setDnsName(String dnsName)
    {
        this.dnsName = dnsName;
    }

    public Set<ListenerDescription> getListeners()
    {
        return listeners;
    }

    public void setListeners(Set<ListenerDescription> listeners)
    {
        this.listeners = listeners;
    }

    public HealthCheck getHealthCheck()
    {
        return healthCheck;
    }

    public void setHealthCheck(HealthCheck healthCheck)
    {
        this.healthCheck = healthCheck;
    }

    public Set<Policy> getPolicies()
    {
        return policies;
    }

    public void setPolicies(Set<Policy> policies)
    {
        this.policies = policies;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    
    @Override
    public String toString()
    {
        return "LoadBalancer [region=" + region + ", name=" + name
                + ", instanceIds=" + instanceIds + ", availabilityZones="
                + availabilityZones + ", dnsName=" + dnsName + ", listeners="
                + listeners + ", healthCheck=" + healthCheck + ", policies="
                + policies + ", createdTime=" + createdTime + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((availabilityZones == null) ? 0 : availabilityZones
                        .hashCode());
        result = prime * result
                + ((createdTime == null) ? 0 : createdTime.hashCode());
        result = prime * result + ((dnsName == null) ? 0 : dnsName.hashCode());
        result = prime * result
                + ((healthCheck == null) ? 0 : healthCheck.hashCode());
        result = prime * result
                + ((instanceIds == null) ? 0 : instanceIds.hashCode());
        result = prime * result
                + ((listeners == null) ? 0 : listeners.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result
                + ((policies == null) ? 0 : policies.hashCode());
        result = prime * result + ((region == null) ? 0 : region.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LoadBalancer other = (LoadBalancer) obj;
        if (availabilityZones == null)
        {
            if (other.availabilityZones != null)
                return false;
        }
        else if (!availabilityZones.equals(other.availabilityZones))
            return false;
        if (createdTime == null)
        {
            if (other.createdTime != null)
                return false;
        }
        else if (!createdTime.equals(other.createdTime))
            return false;
        if (dnsName == null)
        {
            if (other.dnsName != null)
                return false;
        }
        else if (!dnsName.equals(other.dnsName))
            return false;
        if (healthCheck == null)
        {
            if (other.healthCheck != null)
                return false;
        }
        else if (!healthCheck.equals(other.healthCheck))
            return false;
        if (instanceIds == null)
        {
            if (other.instanceIds != null)
                return false;
        }
        else if (!instanceIds.equals(other.instanceIds))
            return false;
        if (listeners == null)
        {
            if (other.listeners != null)
                return false;
        }
        else if (!listeners.equals(other.listeners))
            return false;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (policies == null)
        {
            if (other.policies != null)
                return false;
        }
        else if (!policies.equals(other.policies))
            return false;
        if (region == null)
        {
            if (other.region != null)
                return false;
        }
        else if (!region.equals(other.region))
            return false;
        return true;
    }
}
