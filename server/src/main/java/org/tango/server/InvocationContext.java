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
package org.tango.server;

import java.util.Arrays;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.tango.orb.ServerRequestInterceptor;
import org.tango.server.annotation.AroundInvoke;
import org.tango.utils.ClientIDUtil;

import fr.esrf.Tango.ClntIdent;
import fr.esrf.Tango.DevSource;

/**
 * @see AroundInvoke
 * @author ABEILLE
 *
 */
public final class InvocationContext {
    /**
     * Define the possible invoke contextes.
     *
     * @author ABEILLE
     *
     */
    public enum ContextType {
        /**
         * Before read attributes loop
         */
        PRE_READ_ATTRIBUTES,
        /**
         * Before read attribute
         */
        PRE_READ_ATTRIBUTE,
        /**
         * After read attribute
         */
        POST_READ_ATTRIBUTE,
        /**
         * After read attributes loop
         */
        POST_READ_ATTRIBUTES,
        /**
         * Before write attribute
         */
        PRE_WRITE_ATTRIBUTE,
        /**
         * After write attribute
         */
        POST_WRITE_ATTRIBUTE,
        /**
         * Before execute command
         */
        PRE_COMMAND,
        /**
         * After execute command
         */
        POST_COMMAND,
        /**
         * Before a write_read request
         */
        PRE_WRITE_READ_ATTRIBUTES,
        /**
         *
         */
        PRE_PIPE_WRITE,
        /**
         *
         */
        POST_PIPE_WRITE,
        /**
         *
         */
        PRE_PIPE_READ,
        /**
         *
         */
        POST_PIPE_READ,
        /**
         *
         */
        PRE_PIPE_WRITE_READ,
        /**
         *
         */
        POST_PIPE_WRITE_READ,
        /**
         * Before a write_read request
         */
        POST_WRITE_READ_ATTRIBUTES
    }

    /**
     * Describe how the value retrieval is performed
     *
     * @author ABEILLE
     *
     */
    public enum CallType {
        /**
         * Call will be done on the device
         */
        DEV,
        /**
         * retrieve value in cache (updated by polling)
         */
        CACHE,
        /**
         * If polling is on, retrieve value in cache, in device otherwise
         */
        CACHE_DEV,
        /**
         * The call is done from the polling threads.
         */
        POLLING,
        /**
         * Unknown source call.
         */
        UNKNOWN;

        public static CallType getFromDevSource(final DevSource devSource) {
            if (devSource == null) {
                return CallType.UNKNOWN;
            }
            switch (devSource.value()) {
                case DevSource._CACHE:
                    return CallType.CACHE;
                case DevSource._DEV:
                    return CallType.DEV;
                case DevSource._CACHE_DEV:
                    return CallType.CACHE_DEV;
                default:
                    return CallType.UNKNOWN;
            }
        }
    }

    private final ContextType context;
    private final CallType callType;

    private final String[] names;
    private final ClntIdent clientID;
    private final String clientHostName;

    /**
     * Ctr
     *
     * @param context
     *            {@link ContextType}
     * @param names
     *            Command name or attributes names
     */
    public InvocationContext(final ContextType context, final CallType callType, final ClntIdent clientID,
            final String... names) {
        this.context = context;
        this.callType = callType;
        this.names = names;
        this.clientID = ClientIDUtil.copyClntIdent(clientID);
        this.clientHostName = ServerRequestInterceptor.getInstance().getClientHostName();
    }

    /**
     * Command names or attributes names
     *
     * @return Command names or attributes names
     */
    public String[] getNames() {
        return Arrays.copyOf(names, names.length);
    }

    /**
     * {@link CallType}
     *
     * @return The call type
     */
    public CallType getCallType() {
        return callType;
    }

    /**
     * {@link ContextType}
     *
     * @return The context
     */
    public ContextType getContext() {
        return context;
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        builder.append("context", context);
        builder.append("callType", callType);
        builder.append("clientID", ClientIDUtil.toString(clientID));
        builder.append("names", Arrays.toString(names));
        return builder.toString();
    }

    /**
     * client ID. Works only for read_attribute(s), write_attribute(s), write_read_attribute(s), command_inout and IDL
     * >=4 and when request is not performed by the polling threads.
     *
     * @return the client id
     */
    public ClntIdent getClientID() {
        return clientID;
    }

    /**
     * Get the client host name
     *
     * @return
     */
    public String getClientHostName() {
        return clientHostName;
    }
}
