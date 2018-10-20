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

import se.curity.identityserver.sdk.config.Configuration;
import se.curity.identityserver.sdk.service.SessionManager;

public interface DebugAttributeAuthenticationActionConfiguration extends Configuration
{
    SessionManager getSessionManager();
}
