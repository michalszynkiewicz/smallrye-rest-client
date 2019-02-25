/*
 * Copyright 2019 Red Hat, Inc, and individual contributors.
 *
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
 */
package io.smallrye.restclient.tests.async;

import io.smallrye.restclient.app.HelloResource;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.jaxrs.ProxyBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

import static io.smallrye.restclient.app.HelloResource.HELLO_WITH_PARAM_FMT;
import static org.junit.Assert.assertEquals;

/**
 * @author Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * <br>
 * Date: 2/25/19
 */
@RunWith(Arquillian.class)
public class AsyncWithParamTest {

    HelloClient client;

    @ArquillianResource
    URL url;

    @Deployment
    public static WebArchive createTestArchive() {
        return ShrinkWrap.create(WebArchive.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(HelloResource.class.getPackage())
                .addPackage(AsyncWithParamTest.class.getPackage());
    }


    @Test
    public void shouldPassPathParam() throws Exception {
        HelloClient client =
                new ResteasyClientBuilder()
                        .build()
                        .target(url.toURI())
                        .proxyBuilder(HelloClient.class)
                        .build();
        String name = "testname";
        String result = client.helloAsync(name)
                .toCompletableFuture()
                .get();

        String expectedResponse = String.format(HELLO_WITH_PARAM_FMT, name);
        assertEquals(expectedResponse, result);
    }

}