/*
 * Created on 18 Jan 2007
 * with Eclipse
 */
package org.tango.script.evalution;

import fr.esrf.Tango.DevFailed;

public interface IContextVariable {

    String getName();

    Object getValue() throws DevFailed;

}
