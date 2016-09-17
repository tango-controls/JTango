package fr.soleil.tango.attributecomposer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import fr.soleil.tango.clientapi.TangoGroupAttribute;

/**
 * Schedule a period read on a group of attributes.
 * 
 * @author ABEILLE
 * 
 */
public class AttributeGroupScheduler {

    private ScheduledExecutorService executor;
    private ScheduledFuture<?> future;
    private AttributeGroupReader valueReader;
    private long readingPeriod;

    /**
     * Start the periodic update
     * 
     * @param valueReader the group reader
     * @param readingPeriod the period in milliseconds
     */
    public void start(final AttributeGroupReader valueReader, final long readingPeriod) {
        this.valueReader = valueReader;
        this.readingPeriod = readingPeriod;
        // create a timer to read attributes
        executor = Executors.newScheduledThreadPool(1);
        future = executor.scheduleAtFixedRate(valueReader, 0L, readingPeriod, TimeUnit.MILLISECONDS);
    }

    /**
     * Stop the refresh
     */
    public void stop() {
        if (future != null) {
            future.cancel(true);
        }
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    /**
     * Update the group of attributes
     * 
     * @param attributeGroup
     */
    public void updateAttributeGroup(final TangoGroupAttribute attributeGroup) {
        stop();
        final AttributeGroupReader newValueReader = new AttributeGroupReader(valueReader.getAttributeGroupListener(),
                attributeGroup, valueReader.isReadWriteValue(), valueReader.isReadQuality(),
                valueReader.isReadAttributeInfo());
        start(newValueReader, readingPeriod);
    }
}
