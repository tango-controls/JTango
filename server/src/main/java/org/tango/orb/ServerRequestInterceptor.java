/**
 * Copyright (C) :     2012
 * <p>
 * Synchrotron Soleil
 * L'Orme des merisiers
 * Saint Aubin
 * BP48
 * 91192 GIF-SUR-YVETTE CEDEX
 * <p>
 * This file is part of Tango.
 * <p>
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
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
import org.tango.server.ServerManager;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A CORBA Server Interceptor to retrieve the identity of clients
 *
 * @author ABEILLE
 */
public final class ServerRequestInterceptor extends org.omg.CORBA.LocalObject implements
        org.omg.PortableInterceptor.ServerRequestInterceptor {
    private static final String GIOP_TCP = "giop:tcp:";
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final ServerRequestInterceptor INSTANCE = new ServerRequestInterceptor();
    private final Logger logger = LoggerFactory.getLogger(ServerRequestInterceptor.class);
    private final ThreadLocal<String> clientHostName = new ThreadLocal<>();
    private final ThreadLocal<String> giopHostAddress = new ThreadLocal<>();
    private final ThreadLocal<String> clientIPAddress = new ThreadLocal<>();
    private ThreadLocal<CompletableFuture<String>> clientHostNameFuture = new ThreadLocal<>();

    private ServerRequestInterceptor() {
    }

    public static ServerRequestInterceptor getInstance() {
        return INSTANCE;
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
                        // // local information
                        // final String localIP = sock.getLocalAddress().getHostAddress();
                        // final int localPort = sock.getLocalPort();
                        // remote information
                        String clientIPAddressTmp = sock.getInetAddress().getHostAddress();
                        clientIPAddress.set(clientIPAddressTmp);
                        final int remotePort = sock.getPort();
                        giopHostAddress.set(GIOP_TCP + clientIPAddressTmp + ":" + remotePort);
                        //  Workaround to resolve potential slowness of getCanonicalHostName()
                        CompletableFuture<String> clientHostNameFutureTmp = CompletableFuture.supplyAsync(() -> {
                            String hostName;
                            try {
                                hostName = InetAddress.getByName(clientIPAddressTmp).getCanonicalHostName();
                            } catch (UnknownHostException e) {
                                // use IP address if unknown host
                                hostName = clientIPAddressTmp;
                            }
                            return hostName;
                        });
                        clientHostNameFuture.set(clientHostNameFutureTmp);
                    }
                } else {
                    // when client is in the same process as the server, connection instance of
                    // org.jacorb.orb.iiop.IIOPListener$LoopbackAcceptor
                    clientIPAddress.set(ServerManager.getInstance().getHostIPAddress());
                    clientHostName.set(ServerManager.getInstance().getHostName());
                    giopHostAddress.set(GIOP_TCP + ServerManager.getInstance().getHostIPAddress());
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

    /**
     * @return the client host name or the IP address if not resolvable
     */
    public String getClientHostName() {
        String clientHostNameS = "unresolvable host";
        if (clientHostNameFuture != null && clientHostNameFuture.get() != null) {
            try {
                clientHostNameS = clientHostNameFuture.get().get(1, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                // ignore
            }
        } else if (clientHostName.get() != null) {
            clientHostNameS = clientHostName.get();
        }
        return clientHostNameS;
    }

    @Override
    public void destroy() {
    }

    public String getClientIPAddress() {
        return clientIPAddress.get();
    }

    public String getGiopHostAddress() {
        return giopHostAddress.get();
    }

}
