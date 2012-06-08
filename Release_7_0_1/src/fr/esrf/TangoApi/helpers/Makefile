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
# Revision 1.1  2008/04/10 08:30:47  pascal_verdier
# *** empty log message ***
#
#
# copyright:    European Synchrotron Radiation Facility
#               BP 220, Grenoble 38043
#               FRANCE
#
#-======================================================================

PACKAGE    =  fr/esrf/TangoApi/helpers
TANGO_HOME=	/segfs/tango

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

clean:
	rm  $(BIN_DIR)/$(PACKAGE)/*.class

