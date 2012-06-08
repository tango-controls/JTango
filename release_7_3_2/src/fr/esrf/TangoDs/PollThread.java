//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description: Class to store all the necessary information for the
//              polling thread. It's run() method is the thread code
//
// $Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009
//						European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
// This file is part of Tango.
//
// Tango is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Tango is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License
// along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
// $Revision$
//
// $Log$
// Revision 1.4  2009/03/25 13:33:28  pascal_verdier
// ...
//
// Revision 1.3  2009/01/16 12:37:13  pascal_verdier
// IntelliJIdea warnings removed.
//
// Revision 1.2  2008/10/10 11:30:46  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.1  2007/08/23 08:33:25  ounsy
// updated change from api/java
//
// Revision 3.3  2005/12/02 09:55:46  pascal_verdier
// java import have been optimized.
//
// Revision 3.2  2005/06/13 09:08:42  pascal_verdier
// Attribute historic buffer can be filled by trigger.
//
// Revision 3.1  2004/05/14 13:47:58  pascal_verdier
// Compatibility with Tango-2.2.0 cpp
// (polling commands and attibites).
//-======================================================================
package fr.esrf.TangoDs;

/**
 *	Class to store all the necessary information for the
 *	polling thread. It's run() method is the thread code.
 *
 * @author	$Author$
 * @version	$Revision$
 */

import java.util.Vector;

import org.omg.CORBA.Any;

import fr.esrf.Tango.AttributeValue;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.TimeVal;

public class PollThread extends Thread implements TangoConst {
    PollThCmd shared_cmd;
    PollThCmd local_cmd;
    TangoMonitor p_mon;
    Vector works;
    Vector ext_trig_works;
    TimeVal now;
    TimeVal after;
    long sleep;
    boolean polling_stop;

    static DeviceImpl dev_to_del = null;
    static String name_to_del = "";
    static int type_to_del = Tango_POLL_CMD;

    private final String[] attr_names;

    private static final int POLL_COMMAND = 0;
    private static final int POLL_TRIGGER = 1;
    private static final int POLL_TIME_OUT = 2;

    // ===============================================================
    // ===============================================================
    PollThread(final PollThCmd cmd, final TangoMonitor m) {
        super("Tango poll thread");
        shared_cmd = cmd;
        p_mon = m;
        sleep = 1;
        polling_stop = false;
        attr_names = new String[1];
        now = new TimeVal();
        final long ctm = System.currentTimeMillis();
        now.tv_sec = (int) (ctm / 1000);
        now.tv_usec = (int) (ctm - 1000 * now.tv_sec) * 1000;
        now.tv_nsec = 0;

        after = new TimeVal();
        after.tv_nsec = 0;
        works = new Vector();
        ext_trig_works = new Vector();
    }

    // ===============================================================
    /**
     * This method wait on the shared monitor for a new command to be sent to the polling thread.
     * The thread waits with a timeout. If the thread is awaken due to the timeout, false is
     * returned. If the work list is empty, the thread waits for ever.
     */
    // ===============================================================
    int get_command(final long tout) {
        int ret;

        // Wait on monitor
        if (shared_cmd.cmd_pending == false && shared_cmd.trigger == false) {
            if (works.size() == 0) {
                p_mon.wait_it();
            }
            else if (tout > 0) {
                p_mon.wait_it(tout);
            }
        }

        // Test if it is a new command. If yes, copy its data locally
        if (shared_cmd.cmd_pending == true) {
            local_cmd = shared_cmd;
            ret = POLL_COMMAND;
        }
        else if (shared_cmd.trigger == true) {
            local_cmd = shared_cmd;
            ret = POLL_TRIGGER;
        }
        else {
            ret = POLL_TIME_OUT;
        }

        return ret;
    }

    // ===============================================================
    boolean pred_dev(final WorkItem w) {
        return w.dev == PollThread.dev_to_del;
    }

    // ===============================================================
    boolean pred(final WorkItem w) {
        if (w.dev == PollThread.dev_to_del) {
            if (w.type == PollThread.type_to_del) {
                return w.name.equals(PollThread.name_to_del);
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    // ===============================================================
    // ===============================================================
    void execute_cmd() {
        final WorkItem wo = new WorkItem();
        int nb_elt;
        int i;

        switch (local_cmd.cmd_code) {
            // Add a new object
            case Tango_POLL_ADD_OBJ:
                Util.out5.println("Received a Add object command");
                wo.dev = local_cmd.dev;
                wo.poll_list = wo.dev.get_poll_obj_list();
                final PollObj poll_obj = (PollObj) wo.poll_list.elementAt(local_cmd.index);
                wo.type = poll_obj.get_type();
                wo.update = poll_obj.get_upd();
                wo.name = poll_obj.get_name();

                if (wo.update != 0) {
                    // Add a random delay to do not start all at same time
                    now.tv_usec += new java.util.Random().nextInt(500000);
                    wo.wake_up_date = now;
                    insert_in_list(wo);
                }
                else {
                    wo.wake_up_date.tv_sec = 0;
                    wo.wake_up_date.tv_usec = 0;
                    ext_trig_works.add(wo);
                }
                break;

            // Remove an already polled object
            case Tango_POLL_REM_OBJ:
                Util.out5.println("---> Received a Rem object command");

                dev_to_del = local_cmd.dev;
                name_to_del = local_cmd.name;
                type_to_del = local_cmd.type;
                nb_elt = works.size();
                for (i = 0; i < nb_elt; i++) {
                    final WorkItem item = (WorkItem) works.elementAt(i);
                    if (item.dev == dev_to_del) {
                        if (item.type == type_to_del) {
                            if (item.name.equals(name_to_del)) {
                                works.remove(i);
                                break;
                            }
                        }
                    }
                }
                break;

            // Remove all objects belonging to a device.
            // Take care, the same device could have several objects --> No break after
            // the successfull if in loop
            case Tango_POLL_REM_DEV:
                Util.out5.println("Received a Rem device command");

                dev_to_del = local_cmd.dev;
                nb_elt = works.size();
                for (i = 0; i < nb_elt; i++) {
                    final WorkItem item = (WorkItem) works.elementAt(i);
                    if (item.dev == dev_to_del) {
                        works.remove(i);
                        i--;
                    }
                }
                break;

            // Update polling period
            case Tango_POLL_UPD_PERIOD:
                Util.out5.println("Received a update polling period command");

                dev_to_del = local_cmd.dev;
                name_to_del = local_cmd.name;
                type_to_del = local_cmd.type;

                nb_elt = works.size();
                for (i = 0; i < nb_elt; i++) {
                    final WorkItem item = (WorkItem) works.elementAt(i);
                    if (item.dev == dev_to_del) {
                        if (item.type == type_to_del) {
                            if (item.name.equals(name_to_del)) {
                                item.update = local_cmd.new_upd;
                                break;
                            }
                        }
                    }
                }
                break;

            // Start polling
            case Tango_POLL_START:
                Util.out5.println("Received a Start polling command");
                polling_stop = false;
                break;

            // Stop polling
            case Tango_POLL_STOP:
                Util.out5.println("Received a Stop polling command");
                polling_stop = true;
                break;
        }

        // Inform requesting thread that the work is done
        {
            // omni_mutex_lock sync(p_mon);
            shared_cmd.cmd_pending = false;
            p_mon.signal();
        }

        if (Util._tracelevel >= 5) {
            print_list();
        }

    }

    // ===============================================================
    // ===============================================================
    void one_more_poll() throws DevFailed {
        if (works.size() > 0) {
            final WorkItem item = (WorkItem) works.elementAt(0);
            works.remove(0);

            if (polling_stop == false) {
                if (item.type == Tango_POLL_CMD) {
                    poll_cmd(item);
                }
                else {
                    poll_attr(item);
                }
            }
            item.wake_up_date = compute_new_date(item.wake_up_date, item.update);
            insert_in_list(item);
        }
    }

    // ===============================================================
    // ===============================================================
    void print_list() {
        for (int i = 0; i < works.size(); i++) {
            final WorkItem item = (WorkItem) works.elementAt(i);
            Util.out4.println("Dev name = " + item.dev.get_name() + ", obj name = " + item.name
                    + ", next wake_up at " + item.wake_up_date.tv_sec + ","
                    + item.wake_up_date.tv_usec);
        }
    }

    // ===============================================================
    /**
     * To insert (at the correct place) a new Work Item in the work list
     * 
     * @param new_work The new work item
     */
    // ===============================================================
    void insert_in_list(final WorkItem new_work) {
        int i;
        boolean done = false;
        for (i = 0; i < works.size() && !done; i++) {
            final WorkItem item = (WorkItem) works.elementAt(i);
            if (item.wake_up_date.tv_sec < new_work.wake_up_date.tv_sec) {
                continue;
            }
            else if (item.wake_up_date.tv_sec == new_work.wake_up_date.tv_sec) {
                if (item.wake_up_date.tv_usec < new_work.wake_up_date.tv_usec) {
                    continue;
                }
                else {
                    works.insertElementAt(new_work, i);
                    done = true;
                }
            }
            else {
                works.insertElementAt(new_work, i);
                done = true;
            }
        }
        if (i == works.size()) {
            works.add(new_work);
        }
    }

    // ===============================================================
    /**
     * This method computes the new poll date.
     * 
     * @param time The actual date
     * @param upd : The polling update period (mS)
     */
    // ===============================================================
    TimeVal compute_new_date(final TimeVal time, final long upd) {
        final double ori_d = time.tv_sec + (double) time.tv_usec / 1000000;
        final double new_d = ori_d + (double) upd / 1000;

        final TimeVal ret = new TimeVal();
        ret.tv_sec = (int) new_d;
        ret.tv_usec = (int) ((new_d - ret.tv_sec) * 1000000);
        return ret;
    }

    // ===============================================================
    /**
     * This method computes the differnece between two TimeVal.
     * 
     * @param before First time.
     * @param after_t Second time.
     * @return the difference between first and second time.
     */
    // ===============================================================
    TimeVal time_diff(final TimeVal before, final TimeVal after_t) {
        final double bef_d = before.tv_sec + (double) before.tv_usec / 1000000;
        final double aft_d = after_t.tv_sec + (double) after_t.tv_usec / 1000000;
        final double diff_d = aft_d - bef_d;

        final TimeVal result = new TimeVal();
        result.tv_sec = (int) diff_d;
        result.tv_usec = (int) ((diff_d - result.tv_sec) * 1000000);
        return result;
    }

    // ===============================================================
    /**
     * This method computes how many mS the thread should sleep before the next poll time. If this
     * time is negative and greater than a pre-defined threshold, the polling is discarded.
     */
    // ===============================================================
    void compute_sleep_time() {
        if (works.size() > 0) {
            double next, after_d, diff;
            WorkItem item = (WorkItem) works.elementAt(0);

            next = item.wake_up_date.tv_sec + (double) item.wake_up_date.tv_usec / 1000000;
            after_d = after.tv_sec + (double) after.tv_usec / 1000000;
            diff = next - after_d;

            if (diff < 0) {
                if (Util.fabs(diff) < Tango_DISCARD_THRESHOLD) {
                    sleep = 0;
                }
                else {
                    while (diff < 0 && Util.fabs(diff) > Tango_DISCARD_THRESHOLD) {
                        Util.out5.println(diff + " > " + Tango_DISCARD_THRESHOLD);
                        Util.out5.println("Discard one elt !!!!!!!!!!!!!");
                        item = (WorkItem) works.elementAt(0);
                        compute_new_date(item.wake_up_date, item.update);
                        insert_in_list(item);
                        works.remove(0);

                        item = (WorkItem) works.elementAt(0);
                        next = item.wake_up_date.tv_sec + (double) item.wake_up_date.tv_usec
                                / 1000000;
                        diff = next - after_d;
                    }
                    if (Util.fabs(diff) < Tango_DISCARD_THRESHOLD) {
                        sleep = 0;
                    }
                    else {
                        sleep = (long) (diff * 1000);
                    }
                }
            }
            else {
                sleep = (long) (diff * 1000);
            }
            Util.out5.println("Sleep for : " + sleep);
        }
    }

    // ===============================================================
    /**
     * Execute a command and store the result in the device ring buffer
     */
    // ===============================================================
    void poll_cmd(final WorkItem to_do) throws DevFailed {
        Util.out5.println("poll_cmd  --> Time = " + now.tv_sec + "," + now.tv_usec + " Dev name = "
                + to_do.dev.get_name() + ", Cmd name = " + to_do.name);

        Any argout;
        final TimeVal before_cmd = new TimeVal();
        final TimeVal after_cmd = new TimeVal();
        TimeVal needed_time;
        try {
            long ctm = System.currentTimeMillis();
            before_cmd.tv_sec = (int) (ctm / 1000);
            before_cmd.tv_usec = (int) (ctm - 1000 * before_cmd.tv_sec) * 1000;
            before_cmd.tv_sec = before_cmd.tv_sec - Tango_DELTA_T;

            // Execute the command
            final Any in_any = fr.esrf.TangoApi.ApiUtil.get_orb().create_any();
            argout = to_do.dev.command_inout(to_do.name, in_any);

            ctm = System.currentTimeMillis();
            after_cmd.tv_sec = (int) (ctm / 1000);
            after_cmd.tv_usec = (int) (ctm - 1000 * after_cmd.tv_sec) * 1000;
            after_cmd.tv_sec = after_cmd.tv_sec - Tango_DELTA_T;

            needed_time = time_diff(before_cmd, after_cmd);

            to_do.dev.get_dev_monitor().get_monitor();
            final PollObj poll_obj = to_do.dev.get_polled_obj_by_type_name(to_do.type, to_do.name);
            poll_obj.insert_data(argout, before_cmd, needed_time);
            to_do.dev.get_dev_monitor().rel_monitor();
        }
        catch (final DevFailed e) {
            final long ctm = System.currentTimeMillis();
            after_cmd.tv_sec = (int) (ctm / 1000);
            after_cmd.tv_usec = (int) (ctm - 1000 * after_cmd.tv_sec) * 1000;
            after_cmd.tv_sec = after_cmd.tv_sec - Tango_DELTA_T;

            needed_time = time_diff(before_cmd, after_cmd);

            // to_do.dev.get_dev_monitor().get_monitor();
            try {
                final PollObj poll_obj = to_do.dev.get_polled_obj_by_type_name(to_do.type,
                        to_do.name);
                poll_obj.insert_except(e, before_cmd, needed_time);
            }
            catch (final DevFailed ex) {
            }
            to_do.dev.get_dev_monitor().rel_monitor();
        }
    }

    // ===============================================================
    /**
     * Read attribute and store the result in the device ring buffer
     */
    // ===============================================================
    void poll_attr(final WorkItem to_do) throws DevFailed {
        Util.out5.println("----------> Time = " + now.tv_sec + "," + now.tv_usec + " Dev name = "
                + to_do.dev.get_name() + ", Attr name = " + to_do.name);

        final TimeVal before_cmd = new TimeVal();
        final TimeVal after_cmd = new TimeVal();
        TimeVal needed_time;
        AttributeValue[] argout;
        // vector<PollObj *>::iterator ite = NULL;
        try {

            long ctm = System.currentTimeMillis();
            before_cmd.tv_sec = (int) (ctm / 1000);
            before_cmd.tv_usec = (int) (ctm - 1000 * before_cmd.tv_sec) * 1000;
            before_cmd.tv_sec = before_cmd.tv_sec - Tango_DELTA_T;

            // Read the attributes
            attr_names[0] = to_do.name;
            argout = to_do.dev.read_attributes(attr_names);

            ctm = System.currentTimeMillis();
            after_cmd.tv_sec = (int) (ctm / 1000);
            after_cmd.tv_usec = (int) (ctm - 1000 * after_cmd.tv_sec) * 1000;
            after_cmd.tv_sec = after_cmd.tv_sec - Tango_DELTA_T;

            needed_time = time_diff(before_cmd, after_cmd);

            // Insert only if update period > 0
            to_do.dev.get_dev_monitor().get_monitor();
            final PollObj poll_obj = to_do.dev.get_polled_obj_by_type_name(to_do.type, to_do.name);
            if (poll_obj.get_upd() > 0) {
                poll_obj.insert_data(argout[0], before_cmd, needed_time);
            }
            to_do.dev.get_dev_monitor().rel_monitor();
        }
        catch (final DevFailed e) {
            Except.print_exception(e);

            final long ctm = System.currentTimeMillis();
            after_cmd.tv_sec = (int) (ctm / 1000);
            after_cmd.tv_usec = (int) (ctm - 1000 * after_cmd.tv_sec) * 1000;
            after_cmd.tv_sec = after_cmd.tv_sec - Tango_DELTA_T;

            needed_time = time_diff(before_cmd, after_cmd);

            // to_do.dev.get_dev_monitor().get_monitor();
            try {
                final PollObj poll_obj = to_do.dev.get_polled_obj_by_type_name(to_do.type,
                        to_do.name);
                poll_obj.insert_except(e, before_cmd, needed_time);
            }
            catch (final DevFailed ex) {
            }
            to_do.dev.get_dev_monitor().rel_monitor();
        }
    }

    // ===============================================================
    /**
     * This method is called when a trigger command has been received
     */
    // ===============================================================
    void one_more_trigg() throws DevFailed {
        Util.out5.println("Polling thread has received a trigger");

        WorkItem item = null;
        for (int i = 0; i < ext_trig_works.size(); i++) {
            final WorkItem w = (WorkItem) ext_trig_works.elementAt(i);
            if (w.dev == local_cmd.dev && w.type == local_cmd.type && w.name.equals(local_cmd.name)) {
                item = w;
            }
        }

        // Check that the object to poll has been installed.
        // If not, simply returns. This case should never happens because it is
        // tested in the Util::trigger_polling() method before the trigger is
        // effectively sent to this thread.
        if (item == null) {
            Util.out5.println("Object externally triggered not found !!!");
            {
                shared_cmd.trigger = false;
                p_mon.signal();
            }
            return;
        }

        // Inform requesting thread that the work is done
        {
            shared_cmd.trigger = false;
            p_mon.signal();
        }

        // Do the job
        final WorkItem tmp = item;
        if (polling_stop == false) {
            if (tmp.type == Tango_POLL_CMD) {
                poll_cmd(tmp);
            }
            else {
                poll_attr(tmp);
            }
        }
    }

    // ===============================================================
    /**
     * Polling thread loop.
     */
    // ===============================================================
    @Override
    public void run() {
        int received;

        // noinspection InfiniteLoopStatement
        while (true) {
            // System.out.println("In PollThread loop (sleep=" + sleep + ")....");
            try {
                if (sleep != 0) {
                    received = get_command(sleep);
                }
                else {
                    received = POLL_TIME_OUT;
                }

                long ctm = System.currentTimeMillis();
                now.tv_sec = (int) (ctm / 1000);
                now.tv_usec = (int) (ctm - 1000 * now.tv_sec) * 1000;
                now.tv_sec = now.tv_sec - Tango_DELTA_T;

                switch (received) {
                    case POLL_COMMAND:
                        execute_cmd();
                        break;

                    case POLL_TIME_OUT:
                        one_more_poll();
                        break;

                    case POLL_TRIGGER:
                        one_more_trigg();
                        break;
                }

                ctm = System.currentTimeMillis();
                after.tv_sec = (int) (ctm / 1000);
                after.tv_usec = (int) (ctm - 1000 * after.tv_sec) * 1000;
                after.tv_sec = after.tv_sec - Tango_DELTA_T;

                compute_sleep_time();
            }
            catch (final DevFailed e) {
                Util.out2.println("OUPS !! A thread fatal exception !!!!!!!!");
                Except.print_exception(e);
                Util.out2.println("Trying to re-enter the main loop");
            }
        }
    }

    // =============================================================================
    // =============================================================================

    // =============================================================================
    /**
     * The working Item class definition.
     */
    // =============================================================================
    class WorkItem {
        /**
         * The device pointer (servant)
         */
        DeviceImpl dev;
        /**
         * The device poll list (PollObj vector)
         */
        Vector poll_list;
        /**
         * The next wake up date
         */
        TimeVal wake_up_date;
        /**
         * The update period (mS)
         */
        long update;
        /**
         * Object type (command/attr)
         */
        int type;
        /**
         * Object name
         */
        String name;

        WorkItem() {
            wake_up_date = new TimeVal();
            poll_list = new Vector();
        }

        @Override
        public String toString() {
            return name + " - " + update + " ms ";
        }
    }
}
