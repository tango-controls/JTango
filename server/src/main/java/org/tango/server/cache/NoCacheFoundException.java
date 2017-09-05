package org.tango.server.cache;

/**
 * @author ingvord
 * @since 05.09.17.
 */
class NoCacheFoundException extends RuntimeException {
    NoCacheFoundException(String message) {
        super(message);
    }
}
