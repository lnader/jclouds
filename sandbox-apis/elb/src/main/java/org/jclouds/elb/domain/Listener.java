package org.jclouds.elb.domain;


public class Listener
{
    //Specifies the TCP port on which the instance server is listening. This property 
    //cannot be modified for the life of the LoadBalancer.
    protected Integer instancePort ;  
    //Specifies the external LoadBalancer port number. This property cannot be modified
    //for the life of the LoadBalancer.
    protected Integer loadBalancerPort;    
    //Specifies the LoadBalancer transport protocol to use for routing - TCP or HTTP. 
    //This property cannot be modified for the life of the LoadBalancer.  
    protected String protocol;
    //The ID of the SSL certificate chain to use. For more information on SSL certificates, 
    //see Managing Keys and Certificates in the AWS Identity and Access Management documentation.
    protected String sslCertificateId;

    public Listener(){}
    
    public Listener(Integer instancePort, Integer loadBalancerPort,
            String protocol, String sslCertificateId)
    {
        this.instancePort = instancePort;
        this.loadBalancerPort = loadBalancerPort;
        this.protocol = protocol;
        this.sslCertificateId = sslCertificateId;
    }
    public Integer getInstancePort()
    {
        return instancePort;
    }
    public void setInstancePort(Integer instancePort)
    {
        this.instancePort = instancePort;
    }
    public Integer getLoadBalancerPort()
    {
        return loadBalancerPort;
    }
    public void setLoadBalancerPort(Integer loadBalancerPort)
    {
        this.loadBalancerPort = loadBalancerPort;
    }
    public String getProtocol()
    {
        return protocol;
    }
    public void setProtocol(String protocol)
    {
        this.protocol = protocol;
    }
    public String getSslCertificateId()
    {
        return sslCertificateId;
    }
    public void setSslCertificateId(String sslCertificateId)
    {
        this.sslCertificateId = sslCertificateId;
    }
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((instancePort == null) ? 0 : instancePort.hashCode());
        result = prime
                * result
                + ((loadBalancerPort == null) ? 0 : loadBalancerPort.hashCode());
        result = prime * result
                + ((protocol == null) ? 0 : protocol.hashCode());
        result = prime
                * result
                + ((sslCertificateId == null) ? 0 : sslCertificateId.hashCode());
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
        Listener other = (Listener) obj;
        if (instancePort == null)
        {
            if (other.instancePort != null)
                return false;
        }
        else if (!instancePort.equals(other.instancePort))
            return false;
        if (loadBalancerPort == null)
        {
            if (other.loadBalancerPort != null)
                return false;
        }
        else if (!loadBalancerPort.equals(other.loadBalancerPort))
            return false;
        if (protocol == null)
        {
            if (other.protocol != null)
                return false;
        }
        else if (!protocol.equals(other.protocol))
            return false;
        if (sslCertificateId == null)
        {
            if (other.sslCertificateId != null)
                return false;
        }
        else if (!sslCertificateId.equals(other.sslCertificateId))
            return false;
        return true;
    }
    @Override
    public String toString()
    {
        return "Listener [instancePort=" + instancePort + ", loadBalancerPort="
                + loadBalancerPort + ", protocol=" + protocol
                + ", sslCertificateId=" + sslCertificateId + "]";
    }
   
}
