#+======================================================================
# $Source$
#
# Project:      Tango API
#
# Description:  Makefile to generate the JAVA Tango classes package
#
# $Author$
#
# $Version$
#
# $Log$
# Revision 1.2  2008/10/10 11:30:46  pascal_verdier
# Headers changed for LGPL conformity.
#
# Revision 1.1  2007/08/23 08:33:24  ounsy
# updated change from api/java
#
# Revision 3.10  2007/05/29 08:07:43  pascal_verdier
# Long64, ULong64, ULong, UShort and DevState attributes added.
#
# Revision 3.9  2005/08/10 09:12:26  pascal_verdier
# Initial Revision
#
#
# copyright:    European Synchrotron Radiation Facility
#               BP 220, Grenoble 38043
#               FRANCE
#
#-======================================================================


PACKAGE    =  fr/esrf/TangoDs
TANGO_HOME =  /segfs/tango

# -----------------------------------------------------------------
#
#		The compiler flags
#
#------------------------------------------------------------------

BIN_DIR   = $(TANGO_HOME)/javaapi/bin
JAVAFLAGS = -deprecation -d $(BIN_DIR)
JAVAC     = javac  $(JAVAFLAGS)

#-------------------------------------------------------------------

all:	 $(PACKAGE) 

trace:
	@echo $(CLASSPATH)

$(PACKAGE):
	$(JAVAC)  *.java

clean:
	rm  $(BIN_DIR)/$(PACKAGE)/*.class
