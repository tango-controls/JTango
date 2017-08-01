package org.tango.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.jacorb.orb.iiop.IIOPConnection;
import org.jacorb.orb.iiop.IIOPLoopbackConnection;
import org.omg.ETF.Connection;

import fr.esrf.Tango.ClntIdent;
import fr.esrf.Tango.JavaClntIdent;
import fr.esrf.Tango.LockerLanguage;

/**
 * Utility for client informations
 * 
 * @author ABEILLE
 * 
 */
public final class ClientIDUtil {

    private ClientIDUtil() {

    }

    /**
     * Deep copy of {@link ClntIdent}
     * 
     * @param clt
     * @return
     */
    public static ClntIdent copyClntIdent(final ClntIdent clt) {
        final ClntIdent copy = new ClntIdent();
        if (clt != null) {
            if (clt.discriminator().equals(LockerLanguage.CPP)) {
                copy.cpp_clnt(clt.cpp_clnt());
            } else {
                final JavaClntIdent copyJava = new JavaClntIdent();
                copyJava.uuid = Arrays.copyOf(clt.java_clnt().uuid, clt.java_clnt().uuid.length);
                copyJava.MainClass = clt.java_clnt().MainClass;
                copy.java_clnt(copyJava);
            }
        }
        return copy;
    }

    /**
     * Compare if two {@link ClntIdent} are coming from the same client
     * 
     * @param ident1
     *            The first ident
     * @param ident2
     *            The second ident
     * @return
     */
    public static boolean clientIdentEqual(final ClntIdent ident1, final ClntIdent ident2) {
        boolean areEqual = true;
        if (ident1 == null || ident2 == null || ident1.discriminator() == null || ident2.discriminator() == null) {
            areEqual = false;
        } else if (ident1.discriminator().value() != ident2.discriminator().value()) {
            areEqual = false;
        } else if (ident1.discriminator().equals(LockerLanguage.CPP) && ident1.cpp_clnt() != ident2.cpp_clnt()) {
            areEqual = false;
        } else if (ident1.discriminator().equals(LockerLanguage.JAVA)
                && ident1.java_clnt().MainClass.equalsIgnoreCase(ident2.java_clnt().MainClass)) {
            areEqual = false;
        } else if (ident1.discriminator().equals(LockerLanguage.JAVA)
                && !Arrays.equals(ident1.java_clnt().uuid, ident2.java_clnt().uuid)) {
            areEqual = false;
        }
        return areEqual;
    }

    public static String toString(final ClntIdent ident) {
        final StringBuilder sb = new StringBuilder();
        if (ident == null || ident.discriminator() == null) {
            sb.append("empty ClntIdent");
        } else if (ident.discriminator().equals(LockerLanguage.CPP)) {
            sb.append("CPP client with PID ").append(ident.cpp_clnt());
        } else {
            sb.append("Java client with Main class ").append(ident.java_clnt().MainClass);
        }
        return sb.toString();
    }

    /**
     * Get host name from a {@link Connection}
     * 
     * @param connection
     * @return
     */
    public static String hostNameFrom(final Connection connection) {
        if (connection instanceof IIOPConnection) {
            final IIOPConnection iiopConnection = (IIOPConnection) connection;
            return iiopConnection.getSocket().getInetAddress().getHostName();
        } else if (connection instanceof IIOPLoopbackConnection) {
            try {
                return InetAddress.getLocalHost().getCanonicalHostName();
            } catch (final UnknownHostException e) {
                return "localhost";
            }
        }
        throw new IllegalStateException("Unknown connection type: " + connection.getClass());
    }
}
