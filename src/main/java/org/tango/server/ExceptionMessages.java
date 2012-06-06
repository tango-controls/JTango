/**
 * Copyright (C) :     2012
 *
 * 	Synchrotron Soleil
 * 	L'Orme des merisiers
 * 	Saint Aubin
 * 	BP48
 * 	91192 GIF-SUR-YVETTE CEDEX
 *
 * This file is part of Tango.
 *
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server;

import fr.esrf.Tango.DevFailed;

/**
 * Messages for {@link DevFailed}
 * 
 * @author ABEILLE
 * 
 */
public final class ExceptionMessages {
    private ExceptionMessages() {

    }

    public static final String ATTR_OPT_PROP = "API_AttrOptProp";
    public static final String ATTR_NOT_POLLED = "API_AttrNotPolled";
    public static final String ATTR_NOT_FOUND = "API_AttrNotFound";
    public static final String CMD_NOT_POLLED = "API_CmdNotPolled";
    public static final String COMMAND_NOT_FOUND = "API_CommandNotFound";
    public static final String DEVICE_NOT_FOUND = "API_DeviceNotFound";
    public static final String POLL_OBJ_NOT_FOUND = "API_PollObjNotFound";
    public static final String NOT_SUPPORTED = "API_NotSupported";
    public static final String CANNOT_OPEN_FILE = "API_CannotOpenFile";
    public static final String ATTR_NOT_WRITABLE = "API_AttrNotWritable";
    public static final String ATTR_VALUE_NOT_SET = "API_AttrValueNotSet";
    public static final String CLASS_NOT_FOUND = "API_ClassNotFound";
    public static final String BLACK_BOX_ARG = "API_BlackBoxArgument";
    public static final String BLACK_BOX_EMPTY = "API_BlackBoxEmpty";
    public static final String WATTR_OUTSIDE_LIMIT = "API_WAttrOutsideLimit";
    public static final String ATTR_NOT_ALLOWED = "API_AttrNotAllowed";
    public static final String ATTR_INCORRECT_DATA_NUMBER = "API_AttrIncorrectDataNumber";
    public static final String DEVICE_UNLOCKABLE = "API_DeviceUnlockable";
    public static final String DEVICE_LOCKED = "API_DeviceLocked";
    public static final String DEVICE_UNLOCKED = "API_DeviceUnlocked";
    public static final String WRONG_NR_ARGS = "API_WrongNumberOfArgs";
    public static final String INCOMPATIBLE_DATA_TYPE = "API_IncompatibleAttrDataType";
    public static final String COMMAND_NOT_ALLOWED = "API_CommandNotAllowed";
    public static final String DB_ACCESS = "API_DatabaseAccess";
}
