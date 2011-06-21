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
package org.jclouds.elb.xml;

import java.util.Set;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.jclouds.aws.util.AWSUtils;
import org.jclouds.date.DateService;
import org.jclouds.elb.domain.AppCookieStickinessPolicy;
import org.jclouds.elb.domain.HealthCheck;
import org.jclouds.elb.domain.LBCookieStickinessPolicy;
import org.jclouds.elb.domain.ListenerDescription;
import org.jclouds.elb.domain.LoadBalancer;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.functions.ParseSax;
import org.jclouds.logging.Logger;
import org.xml.sax.Attributes;

import com.google.common.collect.Sets;

/**
 * 
 * @author Lili Nadar
 */
public class DescribeLoadBalancersResponseHandler extends
        ParseSax.HandlerForGeneratedRequestWithResult<Set<LoadBalancer>>
{
    private final DateService dateService;

    @Inject
    public DescribeLoadBalancersResponseHandler(DateService dateService)
    {
        this.dateService = dateService;
    }

    @Resource
    protected Logger logger = Logger.NULL;

    private Set<LoadBalancer> contents = Sets.newLinkedHashSet();
    private StringBuilder currentText = new StringBuilder();

    private boolean inListenerDescriptions = false;
    private boolean inInstances = false;
    private boolean inAppCookieStickinessPolicies = false;
    private boolean inLBCookieStickinessPolicies = false;
    private boolean inAvailabilityZones = false;
    private boolean inPolicies = false;
    private boolean inPolicyNames = false;
    private LoadBalancer elb;
    private ListenerDescription listener;
    private AppCookieStickinessPolicy appPolicy;
    private LBCookieStickinessPolicy lbPolicy;
    public void startElement(String uri, String localName, String qName,
            Attributes attributes)
    {

        if (qName.equals("ListenerDescriptions"))
        {
            inListenerDescriptions = true;
        }
        else if (qName.equals("AppCookieStickinessPolicies"))
        {
            inAppCookieStickinessPolicies = true;
            appPolicy = new AppCookieStickinessPolicy();
        }
        else if (qName.equals("LBCookieStickinessPolicies"))
        {
            inLBCookieStickinessPolicies = true;
            lbPolicy = new LBCookieStickinessPolicy();
        }
        else if (qName.equals("Instances"))
        {
            inInstances = true;
        }
        else if (qName.equals("AvailabilityZones"))
        {
            inAvailabilityZones = true;
        }
        else if (qName.equals("Policies"))
        {
            inPolicies = true;
        }
        else if (qName.equals("PolicyNames"))
        {
            inPolicyNames = true;
        }

        if (qName.equals("HealthCheck"))
        {
            HealthCheck healthCheck = new HealthCheck();
            elb.setHealthCheck(healthCheck);
        }

        if (qName.equals("member"))
        {
            if (!(inListenerDescriptions || inInstances
                    || inAppCookieStickinessPolicies
                    || inLBCookieStickinessPolicies || inAvailabilityZones
                    || inPolicies || inPolicyNames))
            {
                elb = new LoadBalancer();
                try
                {
                    String region = AWSUtils
                            .findRegionInArgsOrNull(getRequest());
                    elb.setRegion(region);
                    contents.add(elb);
                }
                catch (NullPointerException e)
                {
                    logger.warn(e, "malformed load balancer: %s", localName);
                }
            }
            else if (inListenerDescriptions && !inPolicyNames)
            {
                listener = new ListenerDescription();
            }
        }
    }

    public void endElement(String uri, String localName, String qName)
    {
        // if end tag is one of below then set inXYZ to false
        if (qName.equals("ListenerDescriptions"))
        {
            inListenerDescriptions = false;
        }
        else if (qName.equals("AppCookieStickinessPolicies"))
        {
            inAppCookieStickinessPolicies = false;
            elb.getPolicies().add(appPolicy);
        }
        else if (qName.equals("LBCookieStickinessPolicies"))
        {
            inLBCookieStickinessPolicies = false;
            elb.getPolicies().add(lbPolicy);
        }
        else if (qName.equals("Instances"))
        {
            inInstances = false;
        }
        else if (qName.equals("AvailabilityZones"))
        {
            inAvailabilityZones = false;
        }
        else if (qName.equals("Policies"))
        {
            inPolicies = false;
        }
        else if (qName.equals("PolicyNames"))
        {
            inPolicyNames = false;
        }

        // get values
        if (qName.equals("DNSName"))
        {
            elb.setDnsName(currentText.toString().trim());
        }
        else if (qName.equals("LoadBalancerName"))
        {
            elb.setName(currentText.toString().trim());
        }
        else if (qName.equals("InstanceId"))
        {
            elb.getInstanceIds().add(currentText.toString().trim());
        }
        else if (qName.equals("CreatedTime"))
        {
            elb.setCreatedTime(dateService.iso8601DateParse(currentText
                    .toString().trim()));
        }
        else if (qName.equals("Interval"))
        {
            elb.getHealthCheck().setInterval(
                    Integer.parseInt(currentText.toString().trim()));
        }
        else if (qName.equals("Target"))
        {
            elb.getHealthCheck().setTarget((currentText.toString().trim()));
        }
        else if (qName.equals("HealthyThreshold"))
        {
            elb.getHealthCheck().setHealthThreshold(
                    Integer.parseInt(currentText.toString().trim()));
        }
        else if (qName.equals("Timeout"))
        {
            elb.getHealthCheck().setTimeout(
                    Integer.parseInt(currentText.toString().trim()));
        }
        else if (qName.equals("UnhealthyThreshold"))
        {
            elb.getHealthCheck().setUnhealthyThreshold(
                    Integer.parseInt(currentText.toString().trim()));
        }
        else if (qName.equals("Protocol"))
        {
            listener.setProtocol(currentText.toString().trim());
        }
        else if (qName.equals("LoadBalancerPort"))
        {
            listener.setLoadBalancerPort(Integer.parseInt((currentText
                    .toString().trim())));
        }
        else if (qName.equals("InstancePort"))
        {
            listener.setInstancePort(Integer.parseInt((currentText.toString()
                    .trim())));
        }
        else if (qName.equals("SSLCertificateId"))
        {
            listener.setSslCertificateId(currentText.toString().trim());
        }
        else if (qName.equals("CookieName"))
        {
            appPolicy.setCookieName(currentText.toString().trim());
        }
        else if (qName.equals("PolicyName"))
        {
            if(inAppCookieStickinessPolicies)
                appPolicy.setPolicyName(currentText.toString().trim());
            if(inLBCookieStickinessPolicies)
                lbPolicy.setPolicyName(currentText.toString().trim());
        }
        else if(qName.equals("CookieExpirationPeriod"))
        {
            lbPolicy.setCookieExpirationPeriod(Long.parseLong(currentText.toString().trim()));
        }
        else if (qName.equals("member"))
        {

            if (inAvailabilityZones)
            {
                elb.getAvailabilityZones().add(currentText.toString().trim());
            }
            if (inListenerDescriptions && !inPolicyNames)
            {
                elb.getListeners().add(listener);
            }
            if (inListenerDescriptions && inPolicyNames)
            {
                listener.getPolicyNames().add(currentText.toString().trim());
            }
            if (!(inListenerDescriptions || inInstances
                    || inAppCookieStickinessPolicies
                    || inLBCookieStickinessPolicies || inAvailabilityZones
                    || inPolicies || inPolicyNames))
            {

                this.elb = null;

            }

        }

        currentText = new StringBuilder();
    }

    @Override
    public Set<LoadBalancer> getResult()
    {
        return contents;
    }

    public void characters(char ch[], int start, int length)
    {
        currentText.append(ch, start, length);
    }

    @Override
    public DescribeLoadBalancersResponseHandler setContext(HttpRequest request)
    {
        super.setContext(request);
        return this;
    }
}
