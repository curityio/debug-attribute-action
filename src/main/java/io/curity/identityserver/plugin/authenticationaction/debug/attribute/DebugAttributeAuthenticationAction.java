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

package io.curity.identityserver.plugin.authenticationaction.debug.attribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.curity.identityserver.sdk.Nullable;
import se.curity.identityserver.sdk.attribute.Attribute;
import se.curity.identityserver.sdk.attribute.AuthenticationAttributes;
import se.curity.identityserver.sdk.authentication.AuthenticatedSessions;
import se.curity.identityserver.sdk.authenticationaction.AuthenticationAction;
import se.curity.identityserver.sdk.authenticationaction.AuthenticationActionResult;
import se.curity.identityserver.sdk.service.SessionManager;
import se.curity.identityserver.sdk.service.authenticationaction.AuthenticatorDescriptor;

import static se.curity.identityserver.sdk.authenticationaction.AuthenticationActionResult.successfulResult;
import static se.curity.identityserver.sdk.authenticationaction.completions.RequiredActionCompletion.PromptUser.prompt;

public class DebugAttributeAuthenticationAction implements AuthenticationAction
{
    private final static Logger _logger = LoggerFactory.getLogger(DebugAttributeAuthenticationAction.class);

    public final static String SESSION_KEY = "ATTRIBUTE_VIEW";
    private final SessionManager _sessionManager;
    private final DebugAttributeAuthenticationActionConfiguration _configuration;

    public DebugAttributeAuthenticationAction(DebugAttributeAuthenticationActionConfiguration configuration)
    {
        _sessionManager = configuration.getSessionManager();
        _configuration = configuration;
    }

    @Override
    public AuthenticationActionResult apply(AuthenticationAttributes authenticationAttributes,
                                            AuthenticatedSessions authenticatedSessions,
                                            String authenticationTransactionId,
                                            AuthenticatorDescriptor authenticatorDescriptor)
    {
        @Nullable Attribute attributeView = _sessionManager.get(SESSION_KEY);

        if (attributeView != null)
        {
            _sessionManager.remove(SESSION_KEY);
            return successfulResult(authenticationAttributes);
        }

        return AuthenticationActionResult.pendingResult(prompt());
    }
}
