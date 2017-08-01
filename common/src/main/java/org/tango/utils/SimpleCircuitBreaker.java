package org.tango.utils;

import fr.esrf.Tango.DevFailed;

public class SimpleCircuitBreaker {

    private final CircuitBreakerCommand command;
    private static final int RETRIES_BEFORE_OPEN_CIRCUIT_BREAKER = 3;
    private static final int WAIT_CIRCUIT_BREAKER_OPEN = 30000;
    private static final int WAIT_CIRCUIT_BREAKER_CLOSE = 3000;
    private int retryNr = 0;

    public SimpleCircuitBreaker(final CircuitBreakerCommand command) {
        this.command = command;
    }

    public void execute(final int retriesBeforeOpen, final int waitCircuitOpen, final int waitCircuitClose)
            throws DevFailed {
        boolean ok = false;
        retryNr = 0;
        while (!ok) {
            try {
                command.execute();
                ok = true;
            } catch (final DevFailed e) {
                ok = false;
                command.notifyError(e);
                getFallback(retriesBeforeOpen, waitCircuitOpen, waitCircuitClose);
            }
        }
    }

    private void getFallback(final int retriesBeforeOpen, final int waitCircuitOpen, final int waitCircuitClose)
            throws DevFailed {
        command.getFallback();
        retryNr++;
        // wait before retrying
        if (retryNr == retriesBeforeOpen) {
            retryNr = 0;
            try {
                Thread.sleep(waitCircuitOpen);
            } catch (final InterruptedException e1) {
                Thread.currentThread().interrupt();
            }
        } else {
            try {
                Thread.sleep(waitCircuitClose);
            } catch (final InterruptedException e1) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void execute() throws DevFailed {
        this.execute(RETRIES_BEFORE_OPEN_CIRCUIT_BREAKER, WAIT_CIRCUIT_BREAKER_OPEN, WAIT_CIRCUIT_BREAKER_CLOSE);
    }

}
