/*
 *  Copyright (C) 2018 Curity AB. All rights reserved.
 *
 *  The contents of this file are the property of Curity AB.
 *  You may not copy or use this file, in either source code
 *  or executable form, except in compliance with terms
 *  set by Curity AB.
 *
 * For further information, please contact Curity AB.
 */

package io.curity.identityserver.plugin.authenticationaction.debug.attribute;

import se.curity.identityserver.sdk.attribute.Attribute;
import se.curity.identityserver.sdk.authenticationaction.completions.ActionCompletionRequestHandler;
import se.curity.identityserver.sdk.authenticationaction.completions.ActionCompletionResult;
import se.curity.identityserver.sdk.authenticationaction.completions.IntermediateAuthenticationState;
import se.curity.identityserver.sdk.service.SessionManager;
import se.curity.identityserver.sdk.web.Request;
import se.curity.identityserver.sdk.web.Response;

import java.util.Collections;
import java.util.Optional;

import static io.curity.identityserver.plugin.authenticationaction.debug.attribute.DebugAttributeAuthenticationAction.SESSION_KEY;
import static se.curity.identityserver.sdk.authenticationaction.completions.ActionCompletionResult.complete;
import static se.curity.identityserver.sdk.web.ResponseModel.templateResponseModel;

public class DebugAttributeRequestHandler implements ActionCompletionRequestHandler<Request>
{
    private final IntermediateAuthenticationState _intermediateAuthenticationState;
    private final SessionManager _sessionManager;

    public DebugAttributeRequestHandler(IntermediateAuthenticationState intermediateAuthenticationState,
                                        DebugAttributeAuthenticationActionConfiguration configuration)
    {
        _intermediateAuthenticationState = intermediateAuthenticationState;
        _sessionManager = configuration.getSessionManager();
    }

    @Override
    public Optional<ActionCompletionResult> get(Request request, Response response)
    {
        response.setResponseModel(templateResponseModel(Collections.emptyMap(), "index"),
                Response.ResponseModelScope.ANY);

        response.putViewData("_subject", _intermediateAuthenticationState.getAuthenticationAttributes().getSubject(),
                Response.ResponseModelScope.ANY);

        response.putViewData("_attributes", _intermediateAuthenticationState.getAuthenticationAttributes().asMap(),
                Response.ResponseModelScope.ANY);

        return Optional.empty();
    }

    @Override
    public Optional<ActionCompletionResult> post(Request request, Response response)
    {
        _sessionManager.put(Attribute.of(SESSION_KEY, true));

        return Optional.of(complete());
    }

    @Override
    public Request preProcess(Request request, Response response)
    {
        return request;
    }
}
