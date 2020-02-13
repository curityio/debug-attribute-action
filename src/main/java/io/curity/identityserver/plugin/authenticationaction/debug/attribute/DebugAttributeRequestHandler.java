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
import se.curity.identityserver.sdk.service.Json;
import se.curity.identityserver.sdk.service.SessionManager;
import se.curity.identityserver.sdk.web.Request;
import se.curity.identityserver.sdk.web.Response;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static io.curity.identityserver.plugin.authenticationaction.debug.attribute.DebugAttributeAuthenticationAction.SESSION_KEY;
import static java.util.Collections.emptyMap;
import static se.curity.identityserver.sdk.authenticationaction.completions.ActionCompletionResult.complete;
import static se.curity.identityserver.sdk.web.ResponseModel.templateResponseModel;

public class DebugAttributeRequestHandler implements ActionCompletionRequestHandler<Request>
{
    private final IntermediateAuthenticationState _intermediateAuthenticationState;
    private final SessionManager _sessionManager;
    private final Json _json;

    public DebugAttributeRequestHandler(IntermediateAuthenticationState intermediateAuthenticationState,
                                        DebugAttributeAuthenticationActionConfiguration configuration)
    {
        _intermediateAuthenticationState = intermediateAuthenticationState;
        _sessionManager = configuration.getSessionManager();
        _json = configuration.getJson();
    }

    @Override
    public Optional<ActionCompletionResult> get(Request request, Response response)
    {
        response.setResponseModel(templateResponseModel(emptyMap(), "index"),
                Response.ResponseModelScope.ANY);

        response.putViewData("_subject", _intermediateAuthenticationState.getAuthenticationAttributes().getSubject(),
                Response.ResponseModelScope.ANY);

        Map<String, Object> authenticationAttributes = _intermediateAuthenticationState.getAuthenticationAttributes().asMap();
        response.putViewData("_attributesMap", authenticationAttributes, Response.ResponseModelScope.ANY);

        response.putViewData("_attributesJson",
                _json.fromAttributes(_intermediateAuthenticationState.getAuthenticationAttributes()),
                Response.ResponseModelScope.ANY);

        response.putViewData("_subjectAttributesJson",
                _json.toJson(_intermediateAuthenticationState.getAuthenticationAttributes()
                        .asMap().getOrDefault("subject", emptyMap())),
                Response.ResponseModelScope.ANY);

        response.putViewData("_contextAttributesJson",
                _json.toJson(_intermediateAuthenticationState.getAuthenticationAttributes()
                        .asMap().getOrDefault("context", emptyMap())),
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
