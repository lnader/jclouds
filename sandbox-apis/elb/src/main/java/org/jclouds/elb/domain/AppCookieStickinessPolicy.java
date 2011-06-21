package org.jclouds.elb.domain;

public class AppCookieStickinessPolicy extends Policy
{
    private String cookieName;

    public AppCookieStickinessPolicy(){}
    public AppCookieStickinessPolicy(String policyName, String cookieName)
    {
        this.policyName = policyName;
        this.cookieName = cookieName;
    }

    public String getCookieName()
    {
        return cookieName;
    }

    public void setCookieName(String cookieName)
    {
        this.cookieName = cookieName;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((cookieName == null) ? 0 : cookieName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        AppCookieStickinessPolicy other = (AppCookieStickinessPolicy) obj;
        if (cookieName == null)
        {
            if (other.cookieName != null)
                return false;
        }
        else if (!cookieName.equals(other.cookieName))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "AppCookieStickinessPolicy [cookieName=" + cookieName
                + ", policyName=" + policyName + "]";
    }


}
