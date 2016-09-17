package fr.soleil.tango.clientapi;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.soleil.tango.clientapi.command.MockCommand;

@RunWith(Parameterized.class)
public class TangoCommandMockTest {

    @Parameters
    public static List<Object[]> getParametres() throws DevFailed {

	final List<Object[]> result = new ArrayList<Object[]>();
	result.add(new Object[] { new TangoCommand(new MockCommand("VoidVoid")) });
	result.add(new Object[] { new TangoCommand(new MockCommand(false, "VoidScalar", 10)) });
	result.add(new Object[] { new TangoCommand(new MockCommand(true, "ScalarVoid", 10)) });
	result.add(new Object[] { new TangoCommand(new MockCommand("ScalarScalar", 10.2, 15.3)) });
	result.add(new Object[] { new TangoCommand(new MockCommand("SpectrumSpectrum", new double[] { 10 },
		new double[] { 15.3 })) });
	return result;
    }

    private final TangoCommand command;

    public TangoCommandMockTest(final TangoCommand command) {
	this.command = command;
    }

    @Test
    public void test() throws DevFailed {
	try {
	    if (command.isArgoutScalar()) {
		System.out.println(command.getCommandName());
		assertThat(command.execute(Double.class, 150), equalTo(150.0));
	    } else if (command.isArgoutSpectrum()) {
		assertThat(command.execute(double[].class, new double[] { 10, 20 }), equalTo(new double[] { 10, 20 }));
	    } else {// void
		assertThat(command.execute(Double.class, 150), equalTo(null));
	    }
	} catch (final DevFailed e) {
	    System.out.println(DevFailedUtils.toString(e));
	    throw e;
	}
    }

}
