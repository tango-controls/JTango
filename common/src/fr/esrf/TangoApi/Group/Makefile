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
# Revision 1.1  2007/07/23 12:40:22  ounsy
# We add the group class into javacommon
#
# Revision 1.1  2005/08/10 09:18:41  pascal_verdier
# Initial Revision.
#
#
# copyright:    European Synchrotron Radiation Facility
#               BP 220, Grenoble 38043
#               FRANCE
#
#-======================================================================


PACKAGE    =  fr/esrf/TangoApi/Group
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
