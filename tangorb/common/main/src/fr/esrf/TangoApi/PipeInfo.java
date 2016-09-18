//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author: pascal_verdier $
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,
//						European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
// This file is part of Tango.
//
// Tango is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Tango is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
// $Revision: 25296 $
//
//-======================================================================


package fr.esrf.TangoApi;


import fr.esrf.Tango.DispLevel;
import fr.esrf.Tango.PipeConfig;
import fr.esrf.Tango.PipeWriteType;

/**
 * This class is able to define a information for a Tango pipe.
 *
 * @author verdier
 */

public class PipeInfo {
    private String name = "";
    private String description = "";
    private String label = "";
    private DispLevel level;
    private PipeWriteType writeType;
    private String[] extensions = new String[0];


    //==========================================================
    //==========================================================
    public PipeInfo(PipeConfig pipeConfig) {
        this.name        = pipeConfig.name;
        this.description = pipeConfig.description;
        this.label       = pipeConfig.label;
        this.level       = pipeConfig.level;
        this.writeType   = pipeConfig.writable;
    }
    //==========================================================
    //==========================================================
    public PipeInfo(String name, String description,
                    String label, DispLevel level, PipeWriteType writeType) {
        this.name        = name;
        this.description = description;
        this.label       = label;
        this.level       = level;
        this.writeType   = writeType;
    }
    //==========================================================
    /**
     * @return pipe name
     */
    //==========================================================
    public String getName() {
        return name;
    }
    //==========================================================
    /**
     * @return pipe description
     */
    //==========================================================
    public String getDescription() {
        return description;
    }
    //==========================================================
    /**
     * @return pipe label
     */
    //==========================================================
    public String getLabel() {
        return label;
    }
    //==========================================================
    /**
     * @return pipe display level (DispLevel.OPERATOR or DispLevel.EXPERT)
     */
    //==========================================================
    public DispLevel getLevel() {
        return level;
    }
    //==========================================================
    /**
     * @return true if writable
     */
    //==========================================================
    public boolean isWritable() {
        return writeType==PipeWriteType.PIPE_READ_WRITE;
    }
    //==========================================================
    /**
     * @return true if writable
     */
    //==========================================================
    public PipeWriteType getWriteType() {
        return writeType;
    }
    //==========================================================
    /**
     * Set pipe name
     * @param name    pipe name
     */
    //==========================================================
    public void setName(String name) {
        this.name = name;
    }
    //==========================================================
    /**
     * Set pipe description
     * @param description pipe description
     */
    //==========================================================
    public void setDescription(String description) {
        this.description = description;
    }
    //==========================================================
    /**
     * Set pipe label
     * @param label pipe label
     */
    //==========================================================
    public void setLabel(String label) {
        this.label = label;
    }
    //==========================================================
    /**
     * Set pipe display level (DispLevel.Operator or DispLevel.EXPERT)
     * @param level pipe display level
     */
    //==========================================================
    public void setLevel(DispLevel level) {
        this.level = level;
    }
    //==========================================================
    /**
     * Set the pipe write type
     * @param writeType PipeWriteType.PIPE_READ or PipeWriteType.PIPE_READ_WRITE
     */
    //==========================================================
    public void setWriteType(PipeWriteType writeType) {
        this.writeType = writeType;
    }
    //==========================================================
    /**
     * Set the pipe writable or readable
     * @param b set pipe writable if true, readable otherwise.
     */
    //==========================================================
    public void setWritable(boolean b) {
        if (b)
            writeType = PipeWriteType.PIPE_READ_WRITE;
        else
            writeType = PipeWriteType.PIPE_READ;
    }
    //==========================================================
    /**
     * @return the IDL PipeConfig object
     */

    //==========================================================
    public PipeConfig getPipeConfig() {
        return new PipeConfig(name, description, label, level,
                writeType,  extensions);

    }
    //==========================================================
    //==========================================================
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pipe Name:     ").append(name).append("\n");
        sb.append("Description:   ").append(description).append('\n');
        sb.append("Label:         ").append(label).append('\n');
        sb.append("Display Level: ").append((level==DispLevel.EXPERT)? "EXPERT":"OPERATOR").append('\n');
        sb.append("Writable:      ").append(isWritable()).append('\n');
        return sb.toString().trim();
    }
    //==========================================================
    //==========================================================
}
