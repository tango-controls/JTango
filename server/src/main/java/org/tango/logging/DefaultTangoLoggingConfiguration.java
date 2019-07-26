package org.tango.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.joran.action.JMXConfiguratorAction;
import ch.qos.logback.classic.spi.Configurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.joran.spi.ActionException;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.util.StatusPrinter;
import org.xml.sax.helpers.AttributesImpl;

public class DefaultTangoLoggingConfiguration extends ContextAwareBase implements Configurator {
    @Override
    public void configure(LoggerContext loggerContext) {

        addInfo("Setting up default configuration from ");

        ConsoleAppender<ILoggingEvent> ca = new ConsoleAppender<ILoggingEvent>();
        ca.setContext(loggerContext);
        ca.setName("console");
        LayoutWrappingEncoder<ILoggingEvent> encoder = new LayoutWrappingEncoder<ILoggingEvent>();
        encoder.setContext(loggerContext);

        PatternLayout layout = new PatternLayout();
        layout.setPattern("%-5level %d [%thread - %X{deviceName}] %logger{36}.%M:%L - %msg%n");

        layout.setContext(loggerContext);
        layout.start();
        encoder.setLayout(layout);

        ca.setEncoder(encoder);
        ca.start();

        JMXConfiguratorAction jmxConfiguratorAction = new JMXConfiguratorAction();
        jmxConfiguratorAction.setContext(loggerContext);
        try {
            jmxConfiguratorAction.begin(null, null, new AttributesImpl());
        } catch (ActionException e) {
            addError("The Logback JMX configuration failed", e);
        }

        Logger jacorbLogger = loggerContext.getLogger("org.jacorb");
        jacorbLogger.setLevel(Level.ERROR);

        Logger tangoLogger = loggerContext.getLogger("org.tango.server");
        tangoLogger.setLevel(Level.INFO);

        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.ERROR);
        rootLogger.addAppender(ca);

        StatusPrinter.print(loggerContext);
    }
}
