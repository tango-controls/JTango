//+============================================================================
//
// file :               TangoAppender.java
//
// description :        Java source code for the TangoAppender interface. 
//
// project :            TANGO
//
// author(s) :          N.Leclercq
//
// copyleft :           European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
//-============================================================================

package fr.esrf.TangoDs;

 
public interface TangoAppender 
{
/**
 * Returns true if the appender is (still) valid, false otherwise.
 */
	public boolean isValid();
}
