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
package org.tango.logging;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.server.Constants;
import org.tango.server.ExceptionMessages;
import org.tango.utils.DevFailedUtils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import ch.qos.logback.core.util.FileSize;
import fr.esrf.Tango.DevFailed;

/**
 * Manage tango logging. Based on LogBack logging system
 *
 * @author ABEILLE
 *
 */
public final class LoggingManager {
    private final Logger logger = LoggerFactory.getLogger(LoggingManager.class);
    public static final String LOGGING_TARGET_SEPARATOR = "::";
    public static final String LOGGING_TARGET_DEVICE = "device";
    public static final String LOGGING_TARGET_FILE = "file";
    private final Map<String, FileAppender> fileAppenders = new HashMap<String, FileAppender>();
    private final Map<String, DeviceAppender> deviceAppenders = new HashMap<String, DeviceAppender>();
    private int rootLoggingLevel = 0;
    private final Map<String, Integer> loggingLevels = new HashMap<String, Integer>();
    private static LoggingManager instance = new LoggingManager();
    private ch.qos.logback.classic.Logger rootLoggerBack;

    private LoggingManager() {
        final Logger rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        if (rootLogger instanceof ch.qos.logback.classic.Logger) {
            rootLoggerBack = (ch.qos.logback.classic.Logger) rootLogger;
            try {
                rootLoggingLevel = LoggingLevel.getLevel(rootLoggerBack.getLevel()).toInt();
            } catch (final RuntimeException e) {
            }
        }

    }

    public static LoggingManager getInstance() {
        return instance;
    }

    public int getRootLoggingLevel() {
        return rootLoggingLevel;
    }

    public int getLoggingLevel(final String deviceName) {
        int level = rootLoggingLevel;
        if (loggingLevels.containsKey(deviceName)) {
            level = loggingLevels.get(deviceName);
        }
        return level;
    }

    /**
     * Set the logging level of a device
     *
     * @param deviceName the device name
     * @param loggingLevel the level
     */
    public void setLoggingLevel(final String deviceName, final int loggingLevel) {
        System.out.println("set logging level " + deviceName + "-" + LoggingLevel.getLevelFromInt(loggingLevel));
        logger.debug("set logging level to {} on {}", LoggingLevel.getLevelFromInt(loggingLevel), deviceName);
        if (rootLoggingLevel < loggingLevel) {
            setRootLoggingLevel(loggingLevel);
        }
        // setLoggingLevel(loggingLevel);
        loggingLevels.put(deviceName, loggingLevel);
        for (final DeviceAppender appender : deviceAppenders.values()) {
            if (deviceName.equalsIgnoreCase(appender.getDeviceName())) {
                appender.setLevel(loggingLevel);
                break;
            }
        }
        for (final FileAppender appender : fileAppenders.values()) {
            if (deviceName.equalsIgnoreCase(appender.getDeviceName())) {
                appender.setLevel(loggingLevel);
                break;
            }
        }
    }

    /**
     * Set the level of the root logger
     *
     * @param loggingLevel
     */
    public void setRootLoggingLevel(final int loggingLevel) {
        rootLoggingLevel = loggingLevel;
        if (rootLoggerBack != null) {
            rootLoggerBack.setLevel(LoggingLevel.getLevelFromInt(loggingLevel));
        }
    }

    /**
     * Set the level of all loggers of JTangoServer
     *
     * @param loggingLevel
     */
    public void setLoggingLevel(final int loggingLevel, final Class<?>... deviceClassNames) {

        if (rootLoggingLevel < loggingLevel) {
            setRootLoggingLevel(loggingLevel);
        }
        System.out.println("set logging to " + LoggingLevel.getLevelFromInt(loggingLevel));
        final Logger tangoLogger = LoggerFactory.getLogger("org.tango.server");
        if (tangoLogger instanceof ch.qos.logback.classic.Logger) {
            final ch.qos.logback.classic.Logger logbackLogger = (ch.qos.logback.classic.Logger) tangoLogger;
            logbackLogger.setLevel(LoggingLevel.getLevelFromInt(loggingLevel));
        }
        final Logger blackboxLogger = LoggerFactory.getLogger(Constants.CLIENT_REQUESTS_LOGGER);
        if (blackboxLogger instanceof ch.qos.logback.classic.Logger) {
            final ch.qos.logback.classic.Logger logbackLogger = (ch.qos.logback.classic.Logger) blackboxLogger;
            logbackLogger.setLevel(LoggingLevel.getLevelFromInt(loggingLevel));
        }
        for (int i = 0; i < deviceClassNames.length; i++) {
            final Logger deviceLogger = LoggerFactory.getLogger(deviceClassNames[i]);
            if (deviceLogger instanceof ch.qos.logback.classic.Logger) {
                final ch.qos.logback.classic.Logger logbackLogger = (ch.qos.logback.classic.Logger) deviceLogger;
                logbackLogger.setLevel(LoggingLevel.getLevelFromInt(loggingLevel));
            }
        }

    }

    /**
     * Logging of device sent to logviewer device
     *
     * @param deviceTargetName
     * @param deviceClassName
     * @param loggingDeviceName
     * @throws DevFailed
     */
    public void addDeviceAppender(final String deviceTargetName, final Class<?> deviceClassName,
            final String loggingDeviceName) throws DevFailed {
        if (rootLoggerBack != null) {
            logger.debug("add device appender {} on {}", deviceTargetName, loggingDeviceName);
            final DeviceAppender appender = new DeviceAppender(deviceTargetName, loggingDeviceName);
            deviceAppenders.put(loggingDeviceName.toLowerCase(Locale.ENGLISH), appender);
            rootLoggerBack.addAppender(appender);
            // debug level by default
            setLoggingLevel(LoggingLevel.DEBUG.toInt(), deviceClassName);
            setLoggingLevel(loggingDeviceName, LoggingLevel.DEBUG.toInt());
            appender.start();
        }
    }

    /**
     * Add an file appender for a device
     *
     * @param fileName
     * @param deviceName
     * @throws DevFailed
     */
    public void addFileAppender(final String fileName, final String deviceName) throws DevFailed {
        if (rootLoggerBack != null) {
            logger.debug("add file appender of {} in {}", deviceName, fileName);
            final String deviceNameLower = deviceName.toLowerCase(Locale.ENGLISH);
            final File f = new File(fileName);
            if (!f.exists()) {
                try {
                    f.createNewFile();
                } catch (final IOException e) {
                    throw DevFailedUtils.newDevFailed(ExceptionMessages.CANNOT_OPEN_FILE, "impossible to open file "
                            + fileName);
                }
            }
            if (!f.canWrite()) {
                throw DevFailedUtils.newDevFailed(ExceptionMessages.CANNOT_OPEN_FILE, "impossible to open file " + fileName);
            }
            // debug level by default
            // setLoggingLevel(deviceName, LoggingLevel.DEBUG.toInt());
            System.out.println("create file  " + f);
            final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            final FileAppender rfAppender = new FileAppender(deviceNameLower);
            fileAppenders.put(deviceNameLower, rfAppender);
            rfAppender.setName("FILE-" + deviceNameLower);
            rfAppender.setLevel(rootLoggingLevel);
            // rfAppender.setContext(appender.getContext());
            rfAppender.setFile(fileName);
            rfAppender.setAppend(true);
            rfAppender.setContext(loggerContext);
            final FixedWindowRollingPolicy rollingPolicy = new FixedWindowRollingPolicy();
            // rolling policies need to know their parent
            // it's one of the rare cases, where a sub-component knows about its parent
            rollingPolicy.setParent(rfAppender);
            rollingPolicy.setContext(loggerContext);
            rollingPolicy.setFileNamePattern(fileName + "%i");
            rollingPolicy.setMaxIndex(1);
            rollingPolicy.setMaxIndex(3);
            rollingPolicy.start();

            final SizeBasedTriggeringPolicy<ILoggingEvent> triggeringPolicy = new SizeBasedTriggeringPolicy<ILoggingEvent>();
            triggeringPolicy.setMaxFileSize(FileSize.valueOf("5 mb"));
            triggeringPolicy.setContext(loggerContext);
            triggeringPolicy.start();

            final PatternLayoutEncoder encoder = new PatternLayoutEncoder();
            encoder.setContext(loggerContext);
            encoder.setPattern("%-5level %d %X{deviceName} - %thread | %logger{25}.%M:%L - %msg%n");
            encoder.start();

            rfAppender.setEncoder(encoder);
            rfAppender.setRollingPolicy(rollingPolicy);
            rfAppender.setTriggeringPolicy(triggeringPolicy);
            rfAppender.start();

            rootLoggerBack.addAppender(rfAppender);
            rfAppender.start();

            // OPTIONAL: print logback internal status messages
            // StatusPrinter.print(loggerContext);

        }
    }

    public void removeAppender(final String loggingDeviceName, final String targetName) {
        final String loggingDeviceNameLower = loggingDeviceName.toLowerCase(Locale.ENGLISH);
        if (targetName.equalsIgnoreCase(LOGGING_TARGET_DEVICE) && deviceAppenders.containsKey(loggingDeviceNameLower)) {
            final DeviceAppender appender = deviceAppenders.get(loggingDeviceNameLower);
            appender.stop();
            deviceAppenders.remove(loggingDeviceNameLower);
        }
        if (targetName.equalsIgnoreCase(LOGGING_TARGET_FILE) && fileAppenders.containsKey(loggingDeviceNameLower)) {
            final FileAppender appender = fileAppenders.get(loggingDeviceNameLower);
            appender.stop();
            fileAppenders.remove(loggingDeviceNameLower);
        }

    }

    public String[] getLoggingTarget(final String loggingDeviceName) {
        final List<String> targets = new ArrayList<String>();
        final String loggingDeviceNameLower = loggingDeviceName.toLowerCase(Locale.ENGLISH);
        if (deviceAppenders.containsKey(loggingDeviceNameLower)) {
            final DeviceAppender appender = deviceAppenders.get(loggingDeviceNameLower);
            targets.add(LOGGING_TARGET_DEVICE + LOGGING_TARGET_SEPARATOR + appender.getLoggingDeviceName());
        }
        if (fileAppenders.containsKey(loggingDeviceNameLower)) {
            final FileAppender appender = fileAppenders.get(loggingDeviceNameLower);
            targets.add(LOGGING_TARGET_FILE + LOGGING_TARGET_SEPARATOR + appender.getFile());
        }
        return targets.toArray(new String[0]);
    }

    @SuppressWarnings("unchecked")
    public void startAll() {
        for (final ITangoAppender appender : deviceAppenders.values()) {
            if (rootLoggerBack != null) {
                rootLoggerBack.addAppender((Appender<ILoggingEvent>) appender);
                ((AppenderBase<?>) appender).start();
            }
        }
    }

    public void stopAll() {
        for (final ITangoAppender appender : deviceAppenders.values()) {
            ((AppenderBase<?>) appender).stop();
        }
    }
}
