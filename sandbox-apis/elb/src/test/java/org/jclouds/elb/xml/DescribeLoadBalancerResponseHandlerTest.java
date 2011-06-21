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

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.testng.Assert.assertEquals;

import java.io.InputStream;
import java.util.Set;

import org.jclouds.date.DateService;
import org.jclouds.elb.domain.AppCookieStickinessPolicy;
import org.jclouds.elb.domain.HealthCheck;
import org.jclouds.elb.domain.LBCookieStickinessPolicy;
import org.jclouds.elb.domain.ListenerDescription;
import org.jclouds.elb.domain.LoadBalancer;
import org.jclouds.http.functions.BaseHandlerTest;
import org.jclouds.http.functions.ParseSax;
import org.jclouds.rest.internal.GeneratedHttpRequest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

/**
 * Tests behavior of {@code DescribeLoadBalancerResponseHandler}
 * 
 * @author Adrian Cole
 */
// NOTE:without testName, this will not call @Before* and fail w/NPE during surefire
@Test(groups = "unit", testName = "DescribeLoadBalancerResponseHandlerTest")
public class DescribeLoadBalancerResponseHandlerTest extends BaseHandlerTest {

    private DateService dateService;

    @BeforeTest
    @Override
    protected void setUpInjector() {
       super.setUpInjector();
       dateService = injector.getInstance(DateService.class);
       assert dateService != null;
    }
   public void testParse() {
      InputStream is = getClass().getResourceAsStream("/describe_loadbalancers.xml");

      Set<LoadBalancer> contents = Sets.newHashSet();
      LoadBalancer dummy = new LoadBalancer(null, "my-load-balancer", ImmutableSet.of("i-5b33e630",
              "i-8f26d7e4", "i-5933e632"), ImmutableSet.of("us-east-1a", "us-east-1c"), "my-load-balancer-1400212309.us-east-1.elb.amazonaws.com", 
              dateService.iso8601DateParse("2011-05-25T23:47:23.590Z"));
      HealthCheck healthCheck = new HealthCheck(10, 30,
              "HTTP:80/index.html", 5, 2);
      dummy.setHealthCheck(healthCheck);
      
      ListenerDescription ld1 = new ListenerDescription(80, 80,
              "HTTP", null, ImmutableSet.of("AWSConsolePolicy-1"));
      dummy.getListeners().add(ld1);
      ListenerDescription ld2 = new ListenerDescription(8080, 8080,
              "HTTP", null, ImmutableSet.of("AWSConsolePolicy-2"));
      dummy.getListeners().add(ld2);
      
      AppCookieStickinessPolicy appPolicy = new AppCookieStickinessPolicy("AWSConsolePolicy-2","JSESSIONID");
      dummy.getPolicies().add(appPolicy);
      LBCookieStickinessPolicy lbPolicy = new LBCookieStickinessPolicy("AWSConsolePolicy-1",3600L);
      dummy.getPolicies().add(lbPolicy);
      
      contents.add(dummy);

      Set<LoadBalancer> result = parseLoadBalancers(is);

      assertEquals(result, contents);
   }

   private Set<LoadBalancer> parseLoadBalancers(InputStream is) {
      DescribeLoadBalancersResponseHandler handler = injector.getInstance(DescribeLoadBalancersResponseHandler.class);
      addDefaultRegionToHandler(handler);
      Set<LoadBalancer> result = factory.create(handler).parse(is);
      return result;
   }

   private void addDefaultRegionToHandler(ParseSax.HandlerWithResult<?> handler) {
      GeneratedHttpRequest<?> request = createMock(GeneratedHttpRequest.class);
      expect(request.getArgs()).andReturn(ImmutableList.<Object> of());
      replay(request);
      handler.setContext(request);
   }
}
