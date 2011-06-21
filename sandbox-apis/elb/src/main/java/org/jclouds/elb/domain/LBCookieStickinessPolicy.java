package org.jclouds.elb.domain;

public class LBCookieStickinessPolicy extends Policy
{
    private Long CookieExpirationPeriod;

    public LBCookieStickinessPolicy(){}
    public LBCookieStickinessPolicy(String policyName, Long cookieExpirationPeriod)
    {
        this.policyName = policyName;
        this.CookieExpirationPeriod = cookieExpirationPeriod;
    }

    public Long getCookieExpirationPeriod()
    {
        return CookieExpirationPeriod;
    }

    public void setCookieExpirationPeriod(Long cookieExpirationPeriod)
    {
        CookieExpirationPeriod = cookieExpirationPeriod;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime
                * result
                + ((CookieExpirationPeriod == null) ? 0
                        : CookieExpirationPeriod.hashCode());
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
        LBCookieStickinessPolicy other = (LBCookieStickinessPolicy) obj;
        if (CookieExpirationPeriod == null)
        {
            if (other.CookieExpirationPeriod != null)
                return false;
        }
        else if (!CookieExpirationPeriod.equals(other.CookieExpirationPeriod))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "LBCookieStickinessPolicy [CookieExpirationPeriod="
                + CookieExpirationPeriod + ", policyName=" + policyName + "]";
    }
    
}
