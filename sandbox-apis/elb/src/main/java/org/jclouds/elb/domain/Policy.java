package org.jclouds.elb.domain;

public abstract class Policy
{
    protected String policyName;

    public String getPolicyName()
    {
        return policyName;
    }

    public void setPolicyName(String policyName)
    {
        this.policyName = policyName;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((policyName == null) ? 0 : policyName.hashCode());
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
        Policy other = (Policy) obj;
        if (policyName == null)
        {
            if (other.policyName != null)
                return false;
        }
        else if (!policyName.equals(other.policyName))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Policy [policyName=" + policyName + "]";
    }
    
}
