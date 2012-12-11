package fr.soleil.tango.clientapi;

import fr.soleil.tango.errorstrategy.RetriableTask;

public final class Properties {
    private static int delay = 0;
    private static int retries = 0;

    private Properties() {

    }

    public static int getDelay() {
	return delay;
    }

    /**
     * Delay between 2 retries in case of Tango error
     * 
     * @see RetriableTask
     * @param delay
     *            delay in ms
     */
    public static void setDelay(final int delay) {
	Properties.delay = delay;
    }

    public static int getRetries() {
	return retries;
    }

    /**
     * The number of retries in case of Tango error
     * 
     * @see RetriableTask
     * @param retries
     */
    public static void setRetries(final int retries) {
	Properties.retries = retries;
    }
}
