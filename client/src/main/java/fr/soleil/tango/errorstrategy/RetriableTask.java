package fr.soleil.tango.errorstrategy;

import fr.esrf.Tango.DevFailed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.utils.DevFailedUtils;

/**
 * This class is a wrapper for a Callable that adds retry functionality. The
 * user supplies an existing Callable, a maximum number of tries, and optionally
 * a Logger to which exceptions will be logged. Calling the call() method of
 * RetriableTask causes the wrapped object's call() method to be called, and any
 * exceptions thrown from the inner call() will cause the entire inner call() to
 * be repeated from scratch, as long as the maximum number of tries hasn't been
 * exceeded. InterruptedException and CancellationException are allowed to
 * propogate instead of causing retries, in order to allow cancellation by an
 * executor service etc.
 *
 * @param <T>
 *            the return type of the call() method
 */
public final class RetriableTask<T> {

    private final int tries;

    private final int delay;

    private final Logger logger = LoggerFactory.getLogger(RetriableTask.class);

    /**
     * Creates a new RetriableTask around an existing Callable. Supplying zero
     * or a negative number for the tries parameter will allow the task to retry
     * an infinite number of times -- use with caution!
     *
     * @param taskToWrap
     *            the Callable to wrap
     * @param tries
     *            the max number of tries
     * @param delay
     *            time in ms before retrying
     */
    public RetriableTask(final int tries, final int delay) {
        this.tries = tries;
        this.delay = delay;
    }

    /**
     * Invokes the wrapped Callable's call method, optionally retrying if an
     * exception occurs. See class documentation for more detail.
     *
     * @return the return value of the wrapped call() method
     */
    public T execute(final Task<T> taskToWrap) throws DevFailed {
        int triesLeft = tries;
        do {
            try {
                return taskToWrap.call();
            } catch (final DevFailed e) {
                triesLeft--;
                // Are we allowed to try again?
                if (triesLeft <= 0) {
                    // No -- throw
                    logger.error("Caught exception, all retries done for error: {}", DevFailedUtils.toString(e));
                    throw e;
                }

                // Yes -- log and allow to loop
                logger.info("Caught exception, retrying... Error was: {}" + DevFailedUtils.toString(e));
                try {
                    Thread.sleep(delay);
                } catch (final InterruptedException e1) {

                }
            }
        } while (triesLeft > 0);
        return null;

    }
}
