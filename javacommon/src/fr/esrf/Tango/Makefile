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
# Revision 1.3  2007/08/23 08:32:59  ounsy
# updated change from api/java
#
# Revision 1.1  2005/08/10 09:11:10  pascal_verdier
# Initial Revision
#
#
# copyright :   European Synchrotron Radiation Facility
#               BP 220, Grenoble 38043
#               FRANCE
#
#-======================================================================


PACKAGE    =  fr/esrf/Tango
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


$(PACKAGE):
	$(JAVAC)  *.java

clean:
	rm  $(BIN_DIR)/$(PACKAGE)/*.class
