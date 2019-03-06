package org.tango.utils;

import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.ErrSeverity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

public final class DevFailedUtils {
    private static final String TANGO_ERROR = "TANGO_ERROR";
    private static final Logger LOGGER = LoggerFactory.getLogger(DevFailedUtils.class);

    private DevFailedUtils() {

    }

    public static DevFailed newDevFailed(final String reason, final String desc) {
        LOGGER.error("{}, {}", reason, desc);
        final DevFailed ex = new DevFailed(reason, buildDevError(reason, desc, 3));
        LOGGER.error("", ex);
        return ex;
    }

    public static DevFailed newDevFailed(final String msg) {
        LOGGER.error(msg);
        final DevFailed ex = new DevFailed(msg, buildDevError(TANGO_ERROR, msg, 3));
        LOGGER.error("", ex);
        return ex;
    }

    @Deprecated
    public static void throwDevFailed(final String msg) throws DevFailed {
        final DevFailed e = new DevFailed(msg, buildDevError(TANGO_ERROR, msg, 3));
        LOGGER.error(msg);
        LOGGER.error("", e);
        throw e;
    }

    @Deprecated
    public static void throwDevFailed(final String reason, final String desc) throws DevFailed {
        final DevFailed e = new DevFailed(reason, buildDevError(reason, desc, 3));
        LOGGER.error("{}, {}", reason, desc);
        LOGGER.error("", e);
        throw e;
    }

    public static DevError[] buildDevError(final String reason, final String desc, final int stackLevel) {
        final DevError[] err = new DevError[1];
        err[0] = new DevError();
        err[0].desc = desc;
        err[0].severity = ErrSeverity.ERR;
        err[0].reason = reason;
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        // for (final StackTraceElement element : stackTrace) {
        // System.err.println(element.toString());
        // }
        err[0].origin = stackTrace[stackLevel].toString();
        return err;
    }

    public static DevFailed newDevFailed(final Throwable origin) {
        final DevError[] err = new DevError[1];
        err[0] = new DevError();
        err[0].desc = Optional.ofNullable(origin.getLocalizedMessage()).orElse("NA");
        err[0].severity = ErrSeverity.PANIC;
        err[0].reason = origin.getClass().getCanonicalName();
        final StringWriter sw = new StringWriter();
        origin.printStackTrace(new PrintWriter(sw));
        err[0].origin = sw.toString();
        final DevFailed e = new DevFailed(origin.getLocalizedMessage(), err);
        // DevFailedUtils.printDevFailed(e);

        return e;
    }

    @Deprecated
    public static void throwDevFailed(final Throwable origin) throws DevFailed {
        final DevFailed e = newDevFailed(origin);
        // LOGGER.error("exception origin {}, at {}", origin.getClass());
        LOGGER.error("throwing DevFailed from ", origin);
        throw e;
    }

    /**
     * Convert a DevFailed to a String
     *
     * @param e
     * @return
     */
    public static String toString(final DevFailed e) {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("exception message is: ").append(e.getLocalizedMessage());
        if (e.errors != null) {
            for (int i = 0; i < e.errors.length; i++) {
                buffer.append("\nError Level ").append(i).append(":\n");
                buffer.append("\t - desc: ").append(e.errors[i].desc).append("\n");
                buffer.append("\t - origin: ").append(e.errors[i].origin).append("\n");
                buffer.append("\t - reason: ").append(e.errors[i].reason).append("\n");
                String sev = "";
                if (e.errors[i].severity.value() == ErrSeverity.ERR.value()) {
                    sev = "ERROR";
                } else if (e.errors[i].severity.value() == ErrSeverity.PANIC.value()) {
                    sev = "PANIC";
                } else if (e.errors[i].severity.value() == ErrSeverity.WARN.value()) {
                    sev = "WARN";
                }
                buffer.append("\t - severity: ").append(sev).append("\n");
            }
        } else {
            buffer.append("EMPTY DevFailed");
        }
        return buffer.toString();
    }

    /**
     * Convert a DevFailed to a String
     *
     * @param e
     * @return
     */
    public static void logDevFailed(final DevFailed e, final Logger logger) {
        if (e.errors != null) {
            for (int i = 0; i < e.errors.length; i++) {
                logger.error("Error Level {} :", i);
                logger.error("\t - desc: {}", e.errors[i].desc);
                logger.error("\t - origin: {}", e.errors[i].origin);
                logger.error("\t - reason: {}", e.errors[i].reason);
                String sev = "";
                if (e.errors[i].severity.value() == ErrSeverity.ERR.value()) {
                    sev = "ERROR";
                } else if (e.errors[i].severity.value() == ErrSeverity.PANIC.value()) {
                    sev = "PANIC";
                } else if (e.errors[i].severity.value() == ErrSeverity.WARN.value()) {
                    sev = "WARN";
                }
                logger.error("\t - severity: {}", sev);
            }
        } else {
            logger.error("EMPTY DevFailed");
        }
    }

    public static void printDevFailed(final DevFailed e) {
        System.err.println(toString(e));
    }

    public static final String TANGO_BUILD_FAILED = "TANGO_BUILD_FAILED";
}
