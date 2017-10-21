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

import org.jacorb.orb.iiop.ServerIIOPConnection;
import org.jacorb.orb.portableInterceptor.ServerRequestInfoImpl;
import org.omg.ETF.Connection;
import org.omg.PortableInterceptor.ForwardRequest;
import org.omg.PortableInterceptor.ServerRequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.Socket;

/**
 * A CORBA Server Interceptor to retrieve the identity of clients
 *
 * @author ABEILLE
 *
 */
public final class ServerRequestInterceptor extends org.omg.CORBA.LocalObject implements
        org.omg.PortableInterceptor.ServerRequestInterceptor {
    private static final String GIOP_TCP = "giop:tcp:";

    private final Logger logger = LoggerFactory.getLogger(ServerRequestInterceptor.class);

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final ThreadLocal<String> clientHostName = new ThreadLocal<String>();
    private volatile ThreadLocal<String> giopHostAddress = new ThreadLocal<String>();
    private volatile ThreadLocal<String> clientIPAddress = new ThreadLocal<String>();
    private static final ServerRequestInterceptor INSTANCE = new ServerRequestInterceptor();

    private ServerRequestInterceptor() {

    }

    @Override
    public void receive_request_service_contexts(final ServerRequestInfo ri) throws ForwardRequest {
    }

    @Override
    public void receive_request(final ServerRequestInfo ri) throws ForwardRequest {
        try {
            if (ri instanceof ServerRequestInfoImpl) {
                final ServerRequestInfoImpl infoImpl = (ServerRequestInfoImpl) ri;
                final Connection connection = infoImpl.getConnection().getTransport();
                // final Connection connection = infoImpl.request.getConnection().getTransport();
                if (connection instanceof ServerIIOPConnection) {
                    final ServerIIOPConnection connex = (ServerIIOPConnection) connection;
                    final Socket sock = connex.getSocket();
                    if (sock != null) {
                        // // local informations
                        // final String localIP = sock.getLocalAddress().getHostAddress();
                        // final int localPort = sock.getLocalPort();

                        // remote informations
                        clientIPAddress.set(sock.getInetAddress().getHostAddress());
                        final int remotePort = sock.getPort();
                        clientHostName.set(InetAddress.getByName(clientIPAddress.get()).getCanonicalHostName());
                        giopHostAddress.set(GIOP_TCP + clientIPAddress.get() + ":" + remotePort);
                    }
                } else {
                    // when client is in the same process as the server, connection instance of
                    // org.jacorb.orb.iiop.IIOPListener$LoopbackAcceptor
                    final InetAddress addr = InetAddress.getLocalHost();
                    clientIPAddress.set(addr.getHostAddress());
                    clientHostName.set(addr.getCanonicalHostName());
                    giopHostAddress.set(GIOP_TCP + clientIPAddress.get());
                }
            }
        } catch (final Exception e) {
            logger.error("", e);
        }

    }

    @Override
    public void send_reply(final ServerRequestInfo ri) {
    }

    @Override
    public void send_exception(final ServerRequestInfo ri) throws ForwardRequest {
    }

    @Override
    public void send_other(final ServerRequestInfo ri) throws ForwardRequest {
    }

    @Override
    public String name() {
        return this.getClass().getCanonicalName();
    }

    @Override
    public void destroy() {
    }

    public String getClientHostName() {
        return clientHostName.get();
    }

    public String getClientIPAddress() {
        return clientIPAddress.get();
    }

    public String getGiopHostAddress() {
        return giopHostAddress.get();
    }

    public static ServerRequestInterceptor getInstance() {
        return INSTANCE;
    }

}
