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
# Revision 1.1  2008/04/11 07:16:55  pascal_verdier
# *** empty log message ***
#
#
#
# copyright:    European Synchrotron Radiation Facility
#               BP 220, Grenoble 38043
#               FRANCE
#
#-======================================================================


PACKAGE    =  fr/esrf/TangoApi/events
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
