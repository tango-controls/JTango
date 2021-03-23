package org.tango.server.testserver;

import org.tango.server.ServerManager;
import org.tango.server.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Device
public class TestDevice {

    private final Logger logger = LoggerFactory.getLogger(TestDevice.class);

    public static final String SERVER_NAME = TestDevice.class.getSimpleName();
    public static final String INSTANCE_NAME = "1";

    /**
     * Attribute testAttribute READ WRITE, type DevDouble.
     * Default polling period configured like also archive event
     */
    @Attribute(isPolled=true, pollingPeriod = 3000)
    @AttributeProperties(archiveEventAbsolute = "10")
    public double testAttribute;

    /**
     * Starts the server.
     */
    public static void main(final String[] args) {
        ServerManager.getInstance().addClass(TestDevice.class.getCanonicalName(), TestDevice.class);
        ServerManager.getInstance().start(new String[]{INSTANCE_NAME}, SERVER_NAME);
    }

    /**
     * init device
     */
    @Init
    public void init() {
        logger.debug("Init device");
    }

    /**
     * delete device
     */
    @Delete
    public void delete() {
        logger.debug("delete command executed");
    }

    /**
     * Execute command start. Type VOID-VOID
     */
    @Command
    public void start() {
        logger.debug("start command executed");
    }

    /**
     * Read attribute myAttribute.
     *
     * @return
     */
    public double getTestAttribute() {
        logger.debug("Get Test Attribute {}", testAttribute);
        return testAttribute;
    }

    /**
     * Write new attribute value for TestAttribute
     *
     * @param testAttribute
     */
    public void setTestAttribute(final double testAttribute) {
        logger.debug("setMyAttribute {}", testAttribute);
        this.testAttribute = testAttribute;
    }
}