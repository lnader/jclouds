package org.jclouds.elb.domain;

public class HealthCheck {

    private Integer healthThreshold;
    private Integer interval;
    private String target;
    private Integer timeout;
    private Integer unhealthyThreshold;
    
    public HealthCheck(){}
    
    public HealthCheck(Integer healthThreshold, Integer interval,
            String target, Integer timeout, Integer unhealthyThreshold)
    {
        this.healthThreshold = healthThreshold;
        this.interval = interval;
        this.target = target;
        this.timeout = timeout;
        this.unhealthyThreshold = unhealthyThreshold;
    }


    public Integer getHealthThreshold()
    {
        return healthThreshold;
    }


    public void setHealthThreshold(Integer healthThreshold)
    {
        this.healthThreshold = healthThreshold;
    }


    public Integer getInterval()
    {
        return interval;
    }


    public void setInterval(Integer interval)
    {
        this.interval = interval;
    }


    public String getTarget()
    {
        return target;
    }


    public void setTarget(String target)
    {
        this.target = target;
    }


    public Integer getTimeout()
    {
        return timeout;
    }


    public void setTimeout(Integer timeout)
    {
        this.timeout = timeout;
    }


    public Integer getUnhealthyThreshold()
    {
        return unhealthyThreshold;
    }


    public void setUnhealthyThreshold(Integer unhealthyThreshold)
    {
        this.unhealthyThreshold = unhealthyThreshold;
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((healthThreshold == null) ? 0 : healthThreshold.hashCode());
        result = prime * result
                + ((interval == null) ? 0 : interval.hashCode());
        result = prime * result + ((target == null) ? 0 : target.hashCode());
        result = prime * result + ((timeout == null) ? 0 : timeout.hashCode());
        result = prime
                * result
                + ((unhealthyThreshold == null) ? 0 : unhealthyThreshold
                        .hashCode());
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
        HealthCheck other = (HealthCheck) obj;
        if (healthThreshold == null)
        {
            if (other.healthThreshold != null)
                return false;
        }
        else if (!healthThreshold.equals(other.healthThreshold))
            return false;
        if (interval == null)
        {
            if (other.interval != null)
                return false;
        }
        else if (!interval.equals(other.interval))
            return false;
        if (target == null)
        {
            if (other.target != null)
                return false;
        }
        else if (!target.equals(other.target))
            return false;
        if (timeout == null)
        {
            if (other.timeout != null)
                return false;
        }
        else if (!timeout.equals(other.timeout))
            return false;
        if (unhealthyThreshold == null)
        {
            if (other.unhealthyThreshold != null)
                return false;
        }
        else if (!unhealthyThreshold.equals(other.unhealthyThreshold))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "HealthCheck [healthThreshold=" + healthThreshold
                + ", interval=" + interval + ", target=" + target
                + ", timeout=" + timeout + ", unhealthyThreshold="
                + unhealthyThreshold + "]";
    }

}
