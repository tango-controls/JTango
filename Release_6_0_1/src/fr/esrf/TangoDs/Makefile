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
# Revision 3.10  2007/05/29 08:07:43  pascal_verdier
# Long64, ULong64, ULong, UShort and DevState attributes added.
#
# Revision 3.9  2005/08/10 09:12:26  pascal_verdier
# Initial Revision
#
#
# copyleft :    European Synchrotron Radiation Facility
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
JAVAC     = javac  $(JAVAFLAGS) -g

#-------------------------------------------------------------------

all:	 $(PACKAGE) 

trace:
	@echo $(CLASSPATH)

$(PACKAGE):
	$(JAVAC)  *.java

clean:
	rm  $(BIN_DIR)/$(PACKAGE)/*.class
