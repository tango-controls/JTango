package fr.soleil.tango.util;

import java.util.concurrent.TimeoutException;

import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoApi.DeviceProxy;
import fr.esrf.TangoDs.TangoConst;

/**
 * Utility to wait on Tango states
 * 
 * @author HARDION
 * 
 */
public class WaitStateUtilities {
    /**
     * Wait while waitState does not change
     * 
     * @param proxy
     *            the proxy on which to monitor the state
     * @param waitState
     *            the state to wait
     * @param timeout
     *            a timeout in ms
     * @throws DevFailed
     * @throws TimeoutException
     */
    public static void waitWhileState(final DeviceProxy proxy, final DevState waitState, final long timeout)
	    throws DevFailed, TimeoutException {
	waitWhileState(proxy, waitState, timeout, 300);
    }

    /**
     * Wait while waitState does not change
     * 
     * @param proxy
     *            the proxy on which to monitor the state
     * @param waitState
     *            the state to wait
     * @param timeout
     *            a timeout in ms
     * @param pollingTime
     *            the polling period in ms
     * @throws DevFailed
     * @throws TimeoutException
     */
    public static void waitWhileState(final DeviceProxy proxy, final DevState waitState, final long timeout,
	    final long pollingTime) throws DevFailed, TimeoutException {
	final long time0 = System.currentTimeMillis();

	while (proxy.state().equals(waitState)) {
	    if (System.currentTimeMillis() - time0 > timeout) {
		throw new TimeoutException(
			"Stateutilities.waitWhileState : timeout reached while waiting end of state "
				+ TangoConst.Tango_DevStateName[waitState.value()]);
	    }
	    try {
		Thread.sleep(pollingTime);
	    } catch (final InterruptedException e) {
		// ignore
	    }
	}

    }

    /**
     * Wait for waitState
     * 
     * @param proxy
     *            the proxy on which to monitor the state
     * @param waitState
     *            the state to wait
     * @param timeout
     *            a timeout in ms
     * @throws DevFailed
     * @throws TimeoutException
     */
    public static void waitForState(final DeviceProxy proxy, final DevState waitState, final long timeout)
	    throws DevFailed, TimeoutException {
	waitForState(proxy, waitState, timeout, 300);
    }

    /**
     * Wait for waitState
     * 
     * @param proxy
     *            the proxy on which to monitor the state
     * @param waitState
     *            the state to wait
     * @param timeout
     *            a timeout in ms
     * @param pollingTime
     *            the polling period in ms
     * @throws DevFailed
     * @throws TimeoutException
     */
    public static void waitForState(final DeviceProxy proxy, final DevState waitState, final long timeout,
	    final long pollingTime) throws DevFailed, TimeoutException {
	final long time0 = System.currentTimeMillis();

	while (!proxy.state().equals(waitState)) {
	    if (System.currentTimeMillis() - time0 > timeout) {
		throw new TimeoutException("timeout reached while waiting for state "
			+ TangoConst.Tango_DevStateName[waitState.value()]);
	    }
	    try {
		Thread.sleep(pollingTime);
	    } catch (final InterruptedException e) {
		// ignore
	    }
	}

    }

    /**
     * Wait while waitState does not change, and check the final finale
     * 
     * @param deviceProxy
     *            the proxy on which to monitor the state
     * @param expected
     *            the final expected state. Throw DevFailed the final state f is not the expected one
     * @param waitState
     *            the state to wait
     * @param timeout
     *            a timeout in ms
     * @param polling
     *            the polling period in ms
     * @throws DevFailed
     * @throws TimeoutException
     */
    public static void failIfWrongStateAfterWhileState(final DeviceProxy deviceProxy, final DevState expected,
	    final DevState waitState, final long timeout, final long polling) throws DevFailed, TimeoutException {
	waitWhileState(deviceProxy, waitState, timeout, polling);
	if (!expected.equals(deviceProxy.state())) {
		throw DevFailedUtils.newDevFailed("State not reach", "fail to reach state "
		    + TangoConst.Tango_DevStateName[expected.value()] + " after wait "
		    + TangoConst.Tango_DevStateName[waitState.value()]);
	}
    }

}
