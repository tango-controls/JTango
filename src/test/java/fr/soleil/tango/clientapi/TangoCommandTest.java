package fr.soleil.tango.clientapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.CommandInfo;
import fr.esrf.TangoDs.Except;

@RunWith(Parameterized.class)
@Ignore
public class TangoCommandTest {

    @Parameters
    public static List<Object[]> getParametres() {
	final String deviceName = "tango/tangotest/1";
	CommandInfo[] cmdList = null;
	TangoDevice dev = null;
	final List<Object[]> result = new ArrayList<Object[]>();
	try {
	    dev = new TangoDevice(deviceName);
	    cmdList = dev.getDeviceProxy().command_list_query();
	} catch (final DevFailed e) {
	    e.printStackTrace();
	    Except.print_exception(e);
	    Assert.fail();
	}
	for (final CommandInfo cmdInfo : cmdList) {
	    final String cmdName = cmdInfo.cmd_name;
	    result.add(new Object[] { dev, cmdName });
	}
	return result;
    }

    private final String commandeName;
    TangoDevice dev;

    public TangoCommandTest(final TangoDevice dev, final String commandName) {
	commandeName = commandName;
	this.dev = dev;
    }

    @BeforeClass
    public static void setUp() {
	System.setProperty("TANGO_HOST", "calypso:20001");
    }

    @Test
    @Ignore(value = "a tangotest must be started automatically")
    public void testContructor() {
	try {
	    final TangoCommand cmd = new TangoCommand("tango/tangotest/1/SwitchStates");
	    cmd.execute();
	} catch (final DevFailed e) {
	    System.out.println(DevFailedUtils.toString(e));
	    // e.printStackTrace();
	    // Except.print_exception(e);
	    System.err.println("erreur " + commandeName);
	    // Except.print_exception(e);
	    Assert.fail("impossible to write or read " + commandeName + "\n " + DevFailedUtils.toString(e));
	}
    }

    @Test
    @Ignore(value = "a tangotest must be started automatically")
    public void testArgs() {
	try {
	    final TangoCommand cmd = new TangoCommand("tango/tangotest/1/DevDouble");
	    System.out.println(cmd.executeExtractNumber(12.3));
	} catch (final DevFailed e) {
	    System.out.println(DevFailedUtils.toString(e));
	    // e.printStackTrace();
	    // Except.print_exception(e);
	    System.err.println("erreur " + commandeName);
	    // Except.print_exception(e);
	    Assert.fail("impossible to write or read " + commandeName + "\n " + DevFailedUtils.toString(e));
	}
    }

    @Test
    // @Ignore(value = "a tangotest must be started automatically")
    public void testCommand() throws Exception {

	if (!commandeName.equals("CrashFromDevelopperThread") && !commandeName.equals("SwitchStates")
		&& !commandeName.equals("DevVarLongStringArray") && !commandeName.equals("DevVarDoubleStringArray")
		&& !commandeName.equals("CrashFromOmniThread")) {
	    try {
		// final TangoCommand tc = dev.getTangoCommand(commandeName);
		final TangoCommand tc = new TangoCommand(dev.getDeviceProxy().name() + "/" + commandeName);
		System.out.println("#### " + tc + ": ");
		if (tc.isArginScalar()) {
		    System.out.println(tc.execute(String.class, "1.2"));
		} else {
		    System.out.println(Arrays.toString(tc.execute(String[].class, "1.2")));
		}
		System.out.println(tc.executeExtractList(String.class, "1.2"));
		// } else {
		// System.out.println(tc.executeExtractList(String.class, "1.2",
		// "2", "3"));
		// }
		System.out.println("TOSTRING: " + tc.extractToString(","));
	    } catch (final DevFailed e) {
		e.printStackTrace();
		// Except.print_exception(e);
		System.err.println("erreur " + commandeName);
		System.err.println(DevFailedUtils.toString(e));
		// Except.print_exception(e);
		// Assert.fail("impossible to write or read " + commandeName +
		// "\n "
		// + TangoUtil.getDevFailedString(e));
		throw e;
	    } catch (final Exception e) {
		e.printStackTrace();
		throw e;
	    }
	}
    }
    // }

}
