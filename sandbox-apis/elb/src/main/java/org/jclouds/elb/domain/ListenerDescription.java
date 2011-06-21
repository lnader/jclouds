package org.jclouds.elb.domain;

import java.util.HashSet;
import java.util.Set;


public class ListenerDescription extends Listener
{
    private Set<String> policyNames = new HashSet<String>();

    public ListenerDescription()
    {
        super();
    }

    public ListenerDescription(Integer instancePort, Integer loadBalancerPort,
            String protocol, String sslCertificateId, Set<String> policyNames)
    {
        super(instancePort, loadBalancerPort, protocol, sslCertificateId);
        this.policyNames = policyNames;
    }

    public Set<String> getPolicyNames()
    {
        return policyNames;
    }

    public void setPolicyNames(Set<String> policyNames)
    {
        this.policyNames = policyNames;
    }

    @Override
    public String toString()
    {
        return "ListenerDescription [policyNames=" + policyNames
                + ", instancePort=" + instancePort + ", loadBalancerPort="
                + loadBalancerPort + ", protocol=" + protocol
                + ", sslCertificateId=" + sslCertificateId + "]";
    }
}
