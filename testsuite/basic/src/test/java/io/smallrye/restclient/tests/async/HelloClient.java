/*
 * Copyright 2018 Red Hat, Inc, and individual contributors.
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

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import java.util.concurrent.CompletionStage;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

/**
 * @author Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * <br>
 * Date: 2/25/19
 */
@Path("/v1")
@RegisterRestClient  // Required to enable injection of this interface
@RequestScoped
public interface HelloClient {

        @GET
        @Path("/with-param/{name}")
        @Produces(TEXT_PLAIN)
        @Consumes(TEXT_PLAIN)
        CompletionStage<String> helloAsync(@PathParam("name") String name);
}
