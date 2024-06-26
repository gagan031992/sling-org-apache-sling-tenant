/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.sling.tenant.internal;

import java.lang.annotation.Annotation;
import java.util.Iterator;

import junit.framework.TestCase;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.tenant.Tenant;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.osgi.framework.BundleContext;

public class TenantProviderImplTest {

    @Test
    public void testListTenantsWithoutTenantRoot() throws Exception {
        final ResourceResolverFactory rrf = Mockito.mock(ResourceResolverFactory.class);
        final BundleContext context = Mockito.mock(BundleContext.class);
        final ResourceResolver rr = Mockito.mock(ResourceResolver.class);
        Mockito.when(rrf.getServiceResourceResolver(
                ArgumentMatchers.any())).thenReturn(rr);
        TenantProviderImpl.Configuration configuration = new TenantProviderImpl.Configuration() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }
            @Override
            public String tenant_root() {
                return "/etc/tenants";
            }
            @Override
            public String[] tenant_path_matcher() {
                return new String[] {};
            }
        };
        TenantProviderImpl provider = new TenantProviderImpl(context, null, rrf, configuration);
        Iterator<Tenant> tenants = provider.getTenants();
        TestCase.assertNotNull(tenants);
        TestCase.assertFalse(tenants.hasNext());
    }
}
