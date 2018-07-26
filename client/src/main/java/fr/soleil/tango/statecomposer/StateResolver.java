package fr.soleil.tango.statecomposer;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.tango.utils.CaseInsensitiveMap;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoApi.DeviceData;
import fr.esrf.TangoApi.StateUtilities;
import fr.esrf.TangoApi.Group.Group;
import fr.esrf.TangoApi.Group.GroupCmdReply;
import fr.esrf.TangoApi.Group.GroupCmdReplyList;

/**
 * Compose several states with a priority between states synchronoulsy or not
 *
 * @author ABEILLE
 */
public final class StateResolver {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private final Map<String, String> errorReportMap = Collections.synchronizedMap(new CaseInsensitiveMap<String>());
    private Group group;
    private final PriorityStateManager priorityStateManager;

    private final boolean isSynchronous;

    private static class ThreadFact implements ThreadFactory {

        private static final AtomicInteger THREAD_NR = new AtomicInteger(0);
        private final String threadName;

        public ThreadFact(final String threadName) {
            this.threadName = threadName;
        }

        @Override
        public Thread newThread(final Runnable r) {
            return new Thread(r, threadName + "-StateResolver-" + THREAD_NR.incrementAndGet());
        }
    }

    private ScheduledExecutorService executor;
    private ScheduledFuture<?> future;
    private final StateRefresher refresher = new StateRefresher();
    private final long period;

    private class StateRefresher implements Runnable {
        private DevState state = DevState.UNKNOWN;

        @Override
        public void run() {
            state = stateReader();
        }

        public DevState getState() {
            return state;
        }
    }

    /**
     * Configure the state priorities
     *
     * @param priorities
     *            array of String. eg, STATE,priorityValue
     */
    public void configurePriorities(final String[] priorities) {
        // Set the non defined state in the property at 0 priority
        // Enumeration of existing state
        int priority;
        // Get the custom priority
        for (final String state : priorities) {
            // count the token separated by ","
            final StringTokenizer tmpPriorityTokens = new StringTokenizer(state.trim(), ",");
            if (tmpPriorityTokens.countTokens() == 2) {
                // To avoid the the pb of case
                final String tmpState = tmpPriorityTokens.nextToken().trim().toUpperCase();
                // If the custom state exist
                if (StateUtilities.isStateExist(tmpState)) {
                    try {
                        priority = Integer.valueOf(tmpPriorityTokens.nextToken().trim());
                    } catch (final NumberFormatException e) {
                        priority = 0;
                    }
                    putStatePriority(StateUtilities.getStateForName(tmpState), priority);
                }
            }
        }
    }

    public StateResolver(final long period, final boolean isSynchronous) {
        priorityStateManager = new PriorityStateManager();
        this.isSynchronous = isSynchronous;
        this.period = period;

    }

    public void putStatePriority(final DevState state, final int priority) {
        priorityStateManager.putStatePriority(state, priority);
    }

    public void setMonitoredDevicesGroup(final Group group) {
        this.group = group;
    }

    public void setMonitoredDevices(final int timeout, final String... deviceNameList) throws DevFailed {
        if (deviceNameList.length > 0 && !deviceNameList[0].equals("")) {
            // remove the double entries thanks to a Set
            final Set<String> deviceNameSet = new LinkedHashSet<String>();

            for (final String element : deviceNameList) {
                deviceNameSet.add(element.trim());
            }
            // for (final String deviceName : deviceNameSet) {
            // // check if device exists and is alive
            // // ProxyFactory.getInstance().createDeviceProxy(deviceName).ping();
            // new DeviceProxy(deviceName).ping();
            // }
            // Create the group
            // group = ProxyFactory.getInstance().createGroup("statecomposer",
            // deviceNameSet.toArray(new String[deviceNameSet.size()]));
            group = new Group("statecomposer");
            group.add(deviceNameSet.toArray(new String[deviceNameSet.size()]));
            try {
                group.set_timeout_millis(timeout, true);
            } catch (final DevFailed e) {
                // not important, could failed if some devices are down
            }
        } else {
            throw DevFailedUtils.newDevFailed("INIT_ERROR", "property DeviceNameList is not configured");
        }
    }

    public void start(final String threadName) {
        if (!isSynchronous) {
            executor = Executors.newScheduledThreadPool(1, new ThreadFact(threadName));
            future = executor.scheduleAtFixedRate(refresher, 0L, period, TimeUnit.MILLISECONDS);
        }
    }

    public void start() {
        start("");
    }

    public boolean isStarted() {
        boolean isStarted = true;
        if (priorityStateManager.getDeviceStateNumberArray().length == 0) {
            isStarted = false;
        }
        // if (!isSynchronous && future != null) {
        // isStarted = !future.isDone();
        // }
        return isStarted;

    }

    public void stop() {
        if (!isSynchronous && future != null) {
            future.cancel(true);
            executor.shutdownNow();
        }
    }

    public Map<String, String> getErrorReportMap() {
        return new CaseInsensitiveMap<String>(errorReportMap);
    }

    public DevState getState() {
        DevState stateResult;
        if (isSynchronous) {
            stateResult = stateReader();
        } else {
            stateResult = refresher.getState();
        }
        return stateResult;
    }

    public short[] getDeviceStateNumberArray() {
        return priorityStateManager.getDeviceStateNumberArray();
    }

    public Map<String, DevState> getDeviceStates() {
        return priorityStateManager.getDeviceStateMap();
    }

    public String[] getDeviceStateArray() {
        return priorityStateManager.getDeviceStateArray();
    }

    public int getPriorityForState(final DevState state) {
        return priorityStateManager.getPriorityForState(state);
    }

    private DevState stateReader() {
        GroupCmdReplyList tmpReplyList = null;
        DevState stateResult = null;
        try {
            tmpReplyList = group.command_inout("State", true);
        } catch (final DevFailed e) {
            errorReportMap.put(group.get_name(), DATE_FORMAT.format(new Date()) + " : Received error for State "
                    + DevFailedUtils.toString(e));

        }

        if (tmpReplyList != null) {
            final Enumeration<?> tmpReplyEnumeration = tmpReplyList.elements();
            while (tmpReplyEnumeration.hasMoreElements()) {
                final GroupCmdReply tmpReply = (GroupCmdReply) tmpReplyEnumeration.nextElement();
                try {
                    final DeviceData tmpDeviceData = tmpReply.get_data();
                    final DevState tmpState = tmpDeviceData.extractDevState();
                    priorityStateManager.putDeviceState(tmpReply.dev_name(), tmpState);
                } catch (final DevFailed e) {
                    errorReportMap.put(tmpReply.dev_name(), DATE_FORMAT.format(new Date())
                            + " : Received error for State " + DevFailedUtils.toString(e));
                    priorityStateManager.putDeviceState(tmpReply.dev_name(), DevState.UNKNOWN);
                }
                stateResult = priorityStateManager.getHighestPriorityState();
            }
        }
        return stateResult;
    }

    public Group getGroup() {
        return group;
    }
}
