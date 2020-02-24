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
