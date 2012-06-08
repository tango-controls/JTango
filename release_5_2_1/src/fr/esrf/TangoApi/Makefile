#+======================================================================
# $Source$
#
# Project:      Tango Device Server
#
# Description:  Makefile to generate the JAVA Tango API classes package
#
# $Author$
#
# $Version: $
#
# $Log$
# Revision 1.1  2007/11/22 07:58:15  pascal_verdier
# *** empty log message ***
#
# Revision 1.1  2007/08/23 08:33:26  ounsy
# updated change from api/java
#
# Revision 3.11  2006/09/19 13:25:29  pascal_verdier
# Access control management added.
#
# Revision 3.10  2005/08/10 09:12:26  pascal_verdier
# Initial Revision
#
# Revision 3.9  2005/02/11 12:50:46  pascal_verdier
# DeviceInfo Object added (Start/Stop device dates).
#
# Revision 3.8  2004/12/07 09:30:30  pascal_verdier
# Exception classes inherited from DevFailed added.
#
# Revision 3.7  2004/11/05 11:59:21  pascal_verdier
# Attribute Info TANGO 5 compatibility.
#
# Revision 3.6  2004/05/14 14:21:34  pascal_verdier
# Add timeout at runtime.
# Some little bugs fixed.
#
# Revision 3.5  2004/03/12 13:15:23  pascal_verdier
# Using JacORB-2.1
#
# Revision 3.4  2004/03/08 11:35:40  pascal_verdier
# AttributeProxy and aliases management added.
# First revision for event management classes.
#
# Revision 3.3  2003/09/08 11:02:34  pascal_verdier
# *** empty log message ***
#
# Revision 3.2  2003/07/22 14:15:35  pascal_verdier
# DeviceData are now in-methods objects.
# Minor change for TACO-TANGO common database.
#
# Revision 3.1  2003/05/22 11:29:06  pascal_verdier
# *** empty log message ***
#
# Revision 3.0  2003/04/29 08:03:27  pascal_verdier
# Asynchronous calls added.
# Logging related methods.
# little bugs fixed.
#
# Revision 2.0  2003/01/09 14:00:37  verdier
# jacORB is now the ORB used.
#
# Revision 1.8  2002/06/26 09:02:17  verdier
# tested with atkpanel on a TACO device
#
# Revision 1.7  2002/04/09 12:21:51  verdier
# IDL 2 implemented.
#
# Revision 1.6  2002/01/09 12:18:15  verdier
# TACO signals can be read as TANGO attribute.
#
# Revision 1.5  2001/12/10 14:19:42  verdier
# TACO JNI Interface added.
# URL syntax used for connection.
# Connection on device without database added.
#
# Revision 1.4  2001/07/04 14:06:05  verdier
# Attribute management added.
#
# Revision 1.3  2001/04/02 08:32:05  verdier
# TangoApi package has users...
#
#
# copyleft :    European Synchrotron Radiation Facility
#               BP 220, Grenoble 38043
#               FRANCE
#
#-======================================================================

PACKAGE    =  fr/esrf/TangoApi
TANGO_HOME=	/segfs/tango
BUILD_TANGO_DIR=$(TANGO_HOME)/javaapi/build-tangorb

# -----------------------------------------------------------------
#
#		The compiler flags
#
#------------------------------------------------------------------


BIN_DIR   = $(TANGO_HOME)/javaapi/bin
JAVAFLAGS = -deprecation -d $(BIN_DIR)
JAVAC     = javac  $(JAVAFLAGS) -g


#------------------------------------------------------------------
all:	 $(PACKAGE)

$(PACKAGE):
	$(JAVAC)  *.java

eventsclass:	
	@cd events; make
groupclass:	
	@cd Group; make

helperclass:	
	@cd helpers; make


clean:
	rm  $(BIN_DIR)/$(PACKAGE)/*.class

jar:
	@echo
	@echo "-------------------------------------------------------"
	@echo "To build jar file (TangORB-X.Y.jar) change directory to"
	@echo $(BUILD_TANGO_DIR)
	@echo
	@echo "Set Tango and Api revision number in Makefile, and"
	@echo "	Type  'make full'  under linux."
	@echo "-------------------------------------------------------"
	@echo

doc:
	@cd $(BUILD_TANGO_DIR); make doc

