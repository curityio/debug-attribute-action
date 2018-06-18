/*
 * Copyright (C) 2018 Curity AB. All rights reserved.
 *
 *  The contents of this file are the property of Curity AB.
 *  You may not copy or use this file, in either source code
 *  or executable form, except in compliance with terms
 *  set by Curity AB.
 *
 *  For further information, please contact Curity AB.
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
