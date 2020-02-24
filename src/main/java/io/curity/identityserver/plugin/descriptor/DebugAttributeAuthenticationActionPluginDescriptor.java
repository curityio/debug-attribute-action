/*
 *  Copyright 2018 Curity AB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.curity.identityserver.plugin.descriptor;

import io.curity.identityserver.plugin.authenticationaction.debug.attribute.DebugAttributeAuthenticationAction;
import io.curity.identityserver.plugin.authenticationaction.debug.attribute.DebugAttributeAuthenticationActionConfiguration;
import io.curity.identityserver.plugin.authenticationaction.debug.attribute.DebugAttributeRequestHandler;
import se.curity.identityserver.sdk.authenticationaction.AuthenticationAction;
import se.curity.identityserver.sdk.authenticationaction.completions.ActionCompletionRequestHandler;
import se.curity.identityserver.sdk.plugin.descriptor.AuthenticationActionPluginDescriptor;

import java.util.HashMap;
import java.util.Map;

public class DebugAttributeAuthenticationActionPluginDescriptor
        implements AuthenticationActionPluginDescriptor<DebugAttributeAuthenticationActionConfiguration>
{
    @Override
    public Class<? extends AuthenticationAction> getAuthenticationAction()
    {
        return DebugAttributeAuthenticationAction.class;
    }

    @Override
    public String getPluginImplementationType()
    {
        return "debug-attribute";
    }

    @Override
    public Class<? extends DebugAttributeAuthenticationActionConfiguration> getConfigurationType()
    {
        return DebugAttributeAuthenticationActionConfiguration.class;
    }

    @Override
    public Map<String, Class<? extends ActionCompletionRequestHandler<?>>> getAuthenticationActionRequestHandlerTypes()
    {
        Map<String, Class<? extends ActionCompletionRequestHandler<?>>> endpoints = new HashMap<>();
        endpoints.put("index", DebugAttributeRequestHandler.class);
        return endpoints;
    }
}
