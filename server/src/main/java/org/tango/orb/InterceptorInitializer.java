/**
 * Copyright (C) :     2012
 *
 * 	Synchrotron Soleil
 * 	L'Orme des merisiers
 * 	Saint Aubin
 * 	BP48
 * 	91192 GIF-SUR-YVETTE CEDEX
 *
 * This file is part of Tango.
 *
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.orb;

import org.omg.PortableInterceptor.ORBInitInfo;
import org.omg.PortableInterceptor.ORBInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Register CORBA inteceptors
 * 
 * @author ABEILLE
 * 
 */
public final class InterceptorInitializer extends org.omg.CORBA.LocalObject implements ORBInitializer {
    private final Logger logger = LoggerFactory.getLogger(InterceptorInitializer.class);
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InterceptorInitializer() {
    }

    /**
     * This method resolves the NameService and registers the interceptor.
     */

    public void post_init(final ORBInitInfo info) {
	try {
	    info.add_server_request_interceptor(ServerRequestInterceptor.getInstance());
	} catch (final Exception e) {
	    logger.error("error registering server interceptor", e);
	}
    }

    public void pre_init(final ORBInitInfo info) {
    }
}
