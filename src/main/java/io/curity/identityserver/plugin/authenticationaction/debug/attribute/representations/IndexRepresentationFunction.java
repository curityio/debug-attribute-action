package io.curity.identityserver.plugin.authenticationaction.debug.attribute.representations;

import se.curity.identityserver.sdk.haapi.HaapiContract;
import se.curity.identityserver.sdk.haapi.Message;
import se.curity.identityserver.sdk.haapi.RepresentationFactory;
import se.curity.identityserver.sdk.haapi.RepresentationFunction;
import se.curity.identityserver.sdk.haapi.RepresentationModel;
import se.curity.identityserver.sdk.http.HttpMethod;
import se.curity.identityserver.sdk.web.Representation;

import java.net.URI;

public final class IndexRepresentationFunction implements RepresentationFunction
{
    private static final Message MSG_CONTINUE = Message.ofLiteral("Continue");
    private static final Message MSG_SUBJECT_ATTRS = Message.ofLiteral("Subject Attributes");
    private static final Message MSG_CONTEXT_ATTRS = Message.ofLiteral("Context Attributes");
    private static final Message MSG_ACTION_ATTRS = Message.ofLiteral("Action Attributes");

    @Override
    public Representation apply(RepresentationModel model, RepresentationFactory factory)
    {
        return factory.newAuthenticationStep(step -> {
            step.addMessage(Message.ofLiteral("Subject: " + model.getString("_subject")), HaapiContract.MessageClasses.HEADING);

            step.addMessage(MSG_SUBJECT_ATTRS, HaapiContract.MessageClasses.INFO);
            step.addMessage(Message.ofLiteral(model.getString("_subjectAttributesJson")), HaapiContract.MessageClasses.INFO, "json");

            step.addMessage(MSG_CONTEXT_ATTRS, HaapiContract.MessageClasses.INFO);
            step.addMessage(Message.ofLiteral(model.getString("_contextAttributesJson")), HaapiContract.MessageClasses.INFO, "json");

            step.addMessage(MSG_ACTION_ATTRS, HaapiContract.MessageClasses.INFO);
            step.addMessage(Message.ofLiteral(model.getString("_actionAttributesJson")), HaapiContract.MessageClasses.INFO, "json");

            step.addFormAction(HaapiContract.Actions.Kinds.CONTINUE,
                    URI.create(model.getString("_actionUrl")), HttpMethod.POST, null,
                    null, MSG_CONTINUE);
        });
    }
}
