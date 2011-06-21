package org.jclouds.elb.domain;

public class InstanceState
{
    private String description;
    private String instanceId;
    private String reasonCode;
    private String state;
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public String getInstanceId()
    {
        return instanceId;
    }
    public void setInstanceId(String instanceId)
    {
        this.instanceId = instanceId;
    }
    public String getReasonCode()
    {
        return reasonCode;
    }
    public void setReasonCode(String reasonCode)
    {
        this.reasonCode = reasonCode;
    }
    public String getState()
    {
        return state;
    }
    public void setState(String state)
    {
        this.state = state;
    }
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((description == null) ? 0 : description.hashCode());
        result = prime * result
                + ((instanceId == null) ? 0 : instanceId.hashCode());
        result = prime * result
                + ((reasonCode == null) ? 0 : reasonCode.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
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
        InstanceState other = (InstanceState) obj;
        if (description == null)
        {
            if (other.description != null)
                return false;
        }
        else if (!description.equals(other.description))
            return false;
        if (instanceId == null)
        {
            if (other.instanceId != null)
                return false;
        }
        else if (!instanceId.equals(other.instanceId))
            return false;
        if (reasonCode == null)
        {
            if (other.reasonCode != null)
                return false;
        }
        else if (!reasonCode.equals(other.reasonCode))
            return false;
        if (state == null)
        {
            if (other.state != null)
                return false;
        }
        else if (!state.equals(other.state))
            return false;
        return true;
    }
    
    
}
