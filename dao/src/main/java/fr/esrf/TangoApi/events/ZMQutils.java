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
// $Revision:  $
//
//-======================================================================


package fr.esrf.TangoApi.events;


/** 
 *	This class is a set of ZMQ low level utilities
 *
 * @author  verdier
 */

import fr.esrf.Tango.*;
import fr.esrf.TangoApi.*;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;
import org.jacorb.orb.CDRInputStream;
import org.zeromq.ZMQ;
import org.zeromq.ZMQException;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class  ZMQutils {

    //  ZMQ commands
    public static final int   ZMQ_END                    = 0;
    public static final int   ZMQ_CONNECT_HEARTBEAT      = 1;
    public static final int   ZMQ_DISCONNECT_HEARTBEAT   = 2;
    public static final int   ZMQ_CONNECT_EVENT          = 3;
    public static final int   ZMQ_DISCONNECT_EVENT       = 4;
    public static final int   ZMQ_CONNECT_MCAST_EVENT    = 5;
    public static final int   ZMQ_DELAY_EVENT            = 6;
    public static final int   ZMQ_RELEASE_EVENT          = 7;
    private static final String[]   commandNames = {
            "ZMQ_END",
            "ZMQ_CONNECT_HEARTBEAT",
            "ZMQ_DISCONNECT_HEARTBEAT",
            "ZMQ_CONNECT_EVENT",
            "ZMQ_DISCONNECT_EVENT",
            "ZMQ_CONNECT_MCAST_EVENT",
            "ZMQ_DELAY_EVENT",
            "ZMQ_RELEASE_EVENT",
    };

    private static ZMQ.Context     context = ZMQ.context(1);
	private static ZMQutils instance = null;
    private static double  zmqVersion = -1.0;

    private static final int HWM_DEFAULT = 1000;
    public static final String SUBSCRIBE_COMMAND = "ZmqEventSubscriptionChange";
    public static final String SUBSCRIBE_COMMAND_NOT_FOUND =
                                    "Command " + SUBSCRIBE_COMMAND + " not found";
	//===============================================================
	//===============================================================
    static ZMQutils getInstance() {
        if (instance==null) {
            instance = new ZMQutils();
        }
        return instance;
    }
	//===============================================================
	//===============================================================
	private ZMQutils() {
    }
	//===============================================================
	//===============================================================
    public static double getZmqVersion() {
        if (zmqVersion<0.0) {   //  Not already checked.
            zmqVersion = 0.0;
            try {
                String  strVersion = org.zeromq.ZMQ.getVersionString();
                StringTokenizer stk = new StringTokenizer(strVersion, ".");
                ArrayList<String>   list = new ArrayList<String>();
                while (stk.hasMoreTokens())
                    list.add(stk.nextToken());

                strVersion = list.get(0) + "." + list.get(1);
                if (list.size()>2)
                    strVersion += list.get(2);
                try {
                    zmqVersion = Double.parseDouble(strVersion);
                }
                catch (NumberFormatException e) {
                    System.err.println("ZMQutils.getZmqVersion(): " + e);
                }
            }
            catch (Exception e) { /*System.err.println(e);*/  }
            catch (Error e)     { /*System.err.println(e);*/  }
        }
        return zmqVersion;
    }
	//===============================================================
    /**
     *
     * @return the ZMQ context object.
     */
	//===============================================================
    static ZMQ.Context getContext() {
        return context;
    }
	//===============================================================
	//===============================================================


    //  Private methods
	//===============================================================
    /**
     * Build the buffer to be send
     *
     * @param command       command name
     * @param forceConnect  force connection if true
     * @param stringList    device, attribute, event....
     * @return the buffer built
     */
	//===============================================================
    private static byte[] buildTheBuffer(int command, boolean forceConnect, ArrayList<String> stringList) {
        return buildTheBuffer(command, forceConnect, stringList, null);
    }
	//===============================================================
    /**
     * Build the buffer to be send
     *
     * @param command       command name
     * @param forceConnect  force connection if true
     * @param stringList    device, attribute, event....
     * @param intList       Sub HWM, Rate, IVL
     * @return the buffer built
     */
	//===============================================================
    private static byte[] buildTheBuffer(int command, boolean forceConnect,
                                         ArrayList<String> stringList, ArrayList<Integer> intList) {

        //  Check size to allocate
        int size = 2; //    for Command + forceConnect
        if (stringList.size()>0) {
            for (String s : stringList) {
                size += s.length()+1;   //  +1 for '\0' separator
            }
            if (intList!=null && intList.size()>0) {
                size += intList.size()*4;
            }
        }
        byte[]  buffer = new byte[size];

        //  Then fill it with command first
        int idx = 0;
        buffer[idx++] = (byte)command;
        buffer[idx++] = (byte) ((forceConnect)? '1':'0');
        for (String s : stringList) {
            //  And with string bytes
            byte[]  bytes = s.getBytes();
            for (byte b : bytes) {
                buffer[idx++] = b;
            }
            //  Separated by '\0' as C string
            buffer[idx++] = 0;
        }

        //  Then add integers if any
        if (intList!=null && intList.size()>0) {
            for (int value : intList) {
                byte[] bytes = codeInteger(value);
                for (byte b : bytes)
                    buffer[idx++] = b;
            }
        }
        //dump(buffer);
        return buffer;
    }
	//===============================================================
    /**
     *
     * @param bytes  input byte buffer
     * @param start  index to start string construction
     * @return  a string built with byte buffer
     * @throws DevFailed in case of start index is equal or more than bytes.length
     */
	//===============================================================
    private static String getString(byte[] bytes, int start) throws DevFailed {
        //  Get '\0' size (coded as C string) if any
        int end = -1;
        for (int i=start ; i<bytes.length && end<0 ; i++)
            if (bytes[i]==0)
                end = i;
        if (end<0)  //  Not found
            end = bytes.length-1;
        int length = end - start;
        if (length<=0)
            Except.throw_wrong_syntax_exception("API_BadSyntax",
                    "Bad syntax in control buffer (String not found)",
                    "ZMQutils.getString()");
        byte[]  b = new byte[length];

        System.arraycopy(bytes, start, b, 0, length);
        return new String(b);
    }
	//===============================================================
    /**
     * build a byte buffer from an integer
     * @param value the specifid integer value
     * @return the byte buffer built.
     */
	//===============================================================
    private static byte[] codeInteger(int value) {
        byte[] b = new byte[4];
        for (int i=0 ; i<4 ;i++) {
             b[i] = (byte) (value >> i*8);
        }
        return b;
     }
	//===============================================================
    /**
     * Code an integer from byte buffer
     * @param bytes input buffer
     * @return the coded integer.
     */
	//===============================================================
    private static int decodeInteger(byte[] bytes) {
        int value = 0;
        for (int i=0 ; i<4 ;i++) {
            int   x  = (bytes[i] << i*8) & (0xFF << i*8);
            value += x;
        }
        return value;
    }
	//===============================================================
    /**
     * Code an integer from byte buffer
     * @param buffer    input buffer
     * @param start     index to start integer construction
     * @return the coded integer.
     */
	//===============================================================
    private static int getInteger(byte[] buffer, int start) {
        //  Build a temporary byte array containing only the integer
        byte[]  bytes = new byte[4];
        System.arraycopy(buffer, start, bytes, 0, 4);
        return decodeInteger(bytes);
    }
	//===============================================================
	//===============================================================




	//  Public methods
	//===============================================================
    /**
     * Send a data buffer on control socket
     * @param buffer    the specified buffer
     * @throws DevFailed in case of internal communication problem.
     */
	//===============================================================
    static void sendToZmqControlSocket(byte[] buffer) throws DevFailed {
        ZMQ.Socket  controlSocket = context.socket(ZMQ.REQ);
        try {
            controlSocket.connect("inproc://control");
        }
        catch(ZMQException e) {
            if (e.toString().contains("Connection refused")) {
                //  Retry after a while
                try { Thread.sleep(10); } catch (InterruptedException e1) { /* */ }
                controlSocket.connect("inproc://control");
            }
        }
        controlSocket.send(buffer, 0);
        byte[]  resp = controlSocket.recv(0);
        controlSocket.close();
        if (resp.length>0) {
            Except.throw_exception("API_InternalCommunicationError", new String(resp));
        }
        ApiUtil.printTrace("---> Message sent");
    }
	//===============================================================
    /**
     *
     * @param tangoHost     specified tango host
     * @param deviceName    specified device name
     * @param attributeName specified attribute name
     * @param eventName     specified event name
     * @return the full attribute name with tango host and event name
     * @throws DevFailed in case of TANGO_HOST not defined
     */
	//===============================================================
    static String getFullAttributeName(String tangoHost, String deviceName, String attributeName,
                                       int idl, String eventName) throws DevFailed {
        //  If full name, replace Tango Host
        if (deviceName.startsWith("tango://" )) {
            int start = deviceName.indexOf('/', "tango://".length()+1);
            deviceName = deviceName.substring(start+1);
        }

        if (idl>=5) {
            //  Special case (for interface change and pipe do not add idl)
            if (eventName.equals(TangoConst.eventNames[TangoConst.INTERFACE_CHANGE]))
                return ("tango://" + tangoHost + "/" + deviceName + "."+ eventName).toLowerCase();
            else
            if (eventName.equals(TangoConst.eventNames[TangoConst.PIPE_EVENT]))
                return ("tango://" + tangoHost + "/" + deviceName +
                        "/" + attributeName + "."+ eventName).toLowerCase();
            else
                return ("tango://" + tangoHost + "/" + deviceName +
                    "/" + attributeName + ".idl"+ idl + "_" + eventName).toLowerCase();
        }
        else
            return ("tango://" + tangoHost + "/" + deviceName +
                    "/" + attributeName + "."+ eventName).toLowerCase();
    }
	//===============================================================
    /**
     *
     * @param tangoHost    specified tango host
     * @param deviceName    specified device name
     * @return the full heartbeat name with tango host
     * @throws DevFailed in case of TANGO_HOST not defined
     */
	//===============================================================
    static String getFullHeartBeatName(String tangoHost, String deviceName) throws DevFailed {
        return ("tango://" + tangoHost + "/" + deviceName + ".heartbeat").toLowerCase();
    }
	//===============================================================
    /**
     * Request to control to disconnect event
     * @param tgHost        specified device tango_host
     * @param deviceName    specified device
     * @param attributeName specified attribute
     * @param idl           device idl version
     * @param eventName     specified event
     * @throws DevFailed    in case of internal communication problem.
     */
	//===============================================================
    static void  disConnectEvent(String tgHost, String deviceName,
                                 String attributeName, int idl, String eventName) throws DevFailed{
        String[]    tangoHosts = ApiUtil.get_db_obj(tgHost).getPossibleTangoHosts();
        if (tangoHosts!=null) {
            for (String tangoHost : tangoHosts) {
                byte[]  buffer = getBufferToDisConnectEvent(
                        tangoHost, deviceName, attributeName, idl, eventName);
                sendToZmqControlSocket(buffer);
            }
        }
    }
	//===============================================================
    /**
     * Build the buffer to request to control to disconnect event
     * @param tangoHost     specified tango host
     * @param deviceName    specified device
     * @param attributeName specified attribute
     * @param idl           device idl version
     * @param eventName     specified event
     * @return  the buffer buffer to disconnect event
     * @throws DevFailed    in case of internal communication problem.
     */
	//===============================================================
    private static byte[] getBufferToDisConnectEvent(String tangoHost, String deviceName,
                         String attributeName, int idl, String eventName) throws DevFailed{
        byte[]  buffer = new byte[0];
        try {
            ArrayList<String>   stringList = new ArrayList<String>();
            stringList.add(getFullAttributeName(tangoHost,
                                deviceName, attributeName, idl, eventName));
            buffer = buildTheBuffer((byte)ZMQ_DISCONNECT_EVENT, false, stringList);
        }
        catch (Exception e) {
            e.printStackTrace();
            Except.throw_exception("API_ConversionFailed", e.toString());
        }
        return buffer;
    }
	//===============================================================
    /**
     * Request to control to connect event
     * @param tgHost        specified device tango_host
     * @param deviceName    specified device
     * @param attributeName specified attribute
     * @param lsa           the subscription parameters
     * @param eventName     specified event
     * @param forceConnect   Force reconnection if true
     * @throws DevFailed    in case of internal communication problem.
     */
	//===============================================================
    static void connectEvent(String tgHost, String deviceName, String attributeName,
                   DevVarLongStringArray lsa, String eventName,  boolean forceConnect) throws DevFailed {
        String[]    tangoHosts = ApiUtil.get_db_obj(tgHost).getPossibleTangoHosts();
        if (tangoHosts!=null) {
            int n = 0;
            for (String tangoHost : tangoHosts) {
                boolean reallyForce = (n++ == 0) && forceConnect; //  Force only first one
                byte[] buffer = ZMQutils.getBufferToConnectEvent(tangoHost,
                    deviceName, attributeName, lsa, eventName, reallyForce);
                sendToZmqControlSocket(buffer);
            }
        }
        else
            Except.throw_exception("Api_NoTangoHost", "No TANGO_HOST defined");
    }
	//===============================================================
    /**
     * Build a buffer to request to control to connect event
     * @param tangoHost     specified tango host
     * @param deviceName    specified device
     * @param attributeName specified attribute
     * @param lsa   the subscription parameters
     * @param eventName     specified event
     * @param forceConnect   Force reconnection if true
     * @return the buffer built to connect event
     * @throws DevFailed    in case of internal communication problem.
     */
	//===============================================================
    private static byte[] getBufferToConnectEvent(String tangoHost, String deviceName,
                                String attributeName, DevVarLongStringArray lsa,
                                String eventName, boolean forceConnect) throws DevFailed {
        byte[]  buffer = new byte[0];
        try {
            ArrayList<String>   stringList = new ArrayList<String>();
            stringList.add(lsa.svalue[1]);                          //  EndPoint
            stringList.add(getFullAttributeName(tangoHost, deviceName,
                    attributeName, lsa.lvalue[1], eventName));    //  Event name
            ArrayList<Integer>  intList = new ArrayList<Integer>();
            intList.add(lsa.lvalue[0]);     //  Tango release
            intList.add(lsa.lvalue[1]);     //  IDL version
            intList.add(lsa.lvalue[2]);     //  Sub HWM
            intList.add(lsa.lvalue[3]);     //  Rate
            intList.add(lsa.lvalue[4]);     //  IVL
            buffer = buildTheBuffer((byte)ZMQ_CONNECT_EVENT, forceConnect, stringList, intList);
        }
        catch (DevFailed e) {
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            Except.throw_exception("API_ConversionFailed", e.toString());
        }
        return buffer;
    }
	//===============================================================
    /**
     * Request to control to connect heartbeat
     * @param tgHost    specified admin device tango_host
     * @param adminDeviceName    specified admin device
     * @param lsa   the subscription parameters
     * @param forceConnect   Force reconnection if true
     * @throws DevFailed    in case of internal communication problem.
     */
	//===============================================================
    static void connectHeartbeat(String tgHost, String adminDeviceName, DevVarLongStringArray lsa, boolean forceConnect) throws DevFailed{
        String[]    tangoHosts = ApiUtil.get_db_obj(tgHost).getPossibleTangoHosts();
        if (tangoHosts!=null) {
            int n = 0;
            for (String tangoHost : tangoHosts) {
                boolean reallyForce = (n++ == 0) && forceConnect; //  Force only first one
                //  Build the buffer to connect heartbeat and send it
                byte[]  buffer = getBufferToConnectHeartbeat(tangoHost,
                        adminDeviceName, lsa, reallyForce);
                sendToZmqControlSocket(buffer);
            }
        }
    }
	//===============================================================
    /**
     * RBuild buffer to request to control to connect heartbeat
     * @param tangoHost    specified tango host
     * @param adminDeviceName    specified admin device
     * @param lsa   the subscription parameters
     * @param forceConnect   Force reconnection if true
     * @return the buffer built to connect heartbeat
     * @throws DevFailed    in case of internal communication problem.
     */
	//===============================================================
    private static byte[] getBufferToConnectHeartbeat(String tangoHost,
                          String adminDeviceName, DevVarLongStringArray lsa, boolean forceConnect) throws DevFailed {
        byte[]  buffer = new byte[0];
        try {
            ArrayList<String>   stringList = new ArrayList<String>();
            stringList.add(lsa.svalue[0]);                          //  EndPoint
            stringList.add(getFullHeartBeatName(tangoHost, adminDeviceName));  //  Heartbeat name
            buffer = buildTheBuffer((byte)ZMQ_CONNECT_HEARTBEAT, forceConnect, stringList);
        }
        catch (DevFailed e) {
            throw e;
        }
        catch (Exception e) {
            Except.throw_exception("API_ConversionFailed", e.toString());
        }
        return buffer;
    }
	//===============================================================
    /**
     * RBuild buffer to request to control to disconnect heartbeat
     * @param tangoHost    specified tango host
     * @param deviceName    specified admin device
     * @return the buffer built to disconnect heartbeat
     * @throws DevFailed    in case of internal communication problem.
     */
	//===============================================================
    @SuppressWarnings({"UnusedDeclaration"})
    private static byte[] getBufferToDisconnectHeartbeat(String tangoHost,
                                                         String deviceName) throws DevFailed {
        byte[]  buffer = new byte[0];
        try {
            ArrayList<String>   stringList = new ArrayList<String>();
            stringList.add(getFullHeartBeatName(tangoHost, deviceName));   //  Heartbeat name
            buffer = buildTheBuffer((byte)ZMQ_DISCONNECT_HEARTBEAT, false, stringList);
        }
        catch (DevFailed e) {
            throw e;
        }
        catch (Exception e) {
            Except.throw_exception("API_ConversionFailed", e.toString());
        }
        return buffer;
    }
	//===============================================================
    /**
     * Get the event subscription info from admin device
     * @param adminDevice   specified admin device
     * @param deviceName    specified device
     * @param attributeName specified attribute
     * @param eventName     specified event
     * @return the event subscription info:
     *          svalue[0]:  heartbeat endpoint.
     *          svalue[1]:  event endpoint.
     *          lvalue[0]:  Tango lib release.
     *          lvalue[1]:  Device IDL version.
     *          lvalue[2]:  sub HWM.
     *          lvalue[3]:  rate (for multi cast).
     *          lvalue[4]:  ivl (for multi cast).
     * @throws DevFailed in case of admin device connection failed
     */
	//===============================================================
    static DevVarLongStringArray getEventSubscriptionInfoFromAdmDevice(DeviceProxy adminDevice,
            String deviceName, String attributeName, String eventName) throws DevFailed {

        //System.out.println(SUBSCRIBE_COMMAND + " for\n -" +
        //        deviceName + "\n - " + attributeName + "\n - " + eventName +
        //        "\n - idl: " + adminDevice.get_idl_version());
        DeviceData argIn = new DeviceData();

        String[] strArray = {
                deviceName,
                (attributeName==null)? "" : attributeName,
                "subscribe",
                eventName,
                Integer.toString(adminDevice.get_idl_version()),
        };
        argIn.insert(strArray);
        DeviceData argOut = adminDevice.command_inout(SUBSCRIBE_COMMAND, argIn);
        return argOut.extractLongStringArray();
    }
	//===============================================================
    /**
     * De Marshall data from a receive byte buffer
     * @param recData   receive data
     * @param littleIndian endianness to de marshall
     * @return the data after de marshaling
     * @throws DevFailed in case of de marshaling failed
     */
	//===============================================================
    static DevError[] deMarshallErrorList(byte[] recData, boolean littleIndian) throws DevFailed {
        try {
            //  Remove the 4 first bytes (added for c++ alignment)
            byte[]  buffer = new byte[recData.length-4];
            System.arraycopy(recData, 4, buffer, 0, recData.length - 4);
            CDRInputStream is = new CDRInputStream(ApiUtil.getOrb(), buffer, littleIndian);
            return DevErrorListHelper.read(is);
        }
        catch (Exception e) {
            Except.throw_exception("Api_ConversionFailed",
                    "An exception " + e + " has been catch");
            return null;    //  Cannot occur
        }
    }
	//===============================================================
    /**
     * De Marshall data from a receive byte buffer
     * @param recData   receive data
     * @param littleIndian endianness to de marshall
     * @return the data after de marshaling
     * @throws DevFailed in case of de marshaling failed
     */
	//===============================================================
    static ZmqCallInfo deMarshallZmqCallInfo(byte[] recData, boolean littleIndian) throws DevFailed {
        try {
            CDRInputStream is = new CDRInputStream(ApiUtil.getOrb(), recData, littleIndian);
            return ZmqCallInfoHelper.read(is);
        }
        catch (Exception e) {
            Except.throw_exception("Api_ConversionFailed",
                    "An exception \'" + e + "\' has been catch");
            return null;    //  Cannot occur
        }
    }
	//===============================================================
    /**
     * De Marshall data from a receive byte buffer
     * @param recData   receive data
     * @param littleIndian endianness to de marshall
     * @return the data after de marshaling
     * @throws DevFailed in case of de marshaling failed
     */
	//===============================================================
    static AttDataReady deMarshallAttDataReady(byte[] recData, boolean littleIndian) throws DevFailed {
        try {
            //  Remove the 4 first bytes (added for c++ alignment)
            byte[]  buffer = new byte[recData.length-4];
            System.arraycopy(recData, 4, buffer, 0, recData.length - 4);
            CDRInputStream is = new CDRInputStream(ApiUtil.getOrb(), buffer, littleIndian);
            return AttDataReadyHelper.read(is);
        }
        catch (Exception e) {
            Except.throw_exception("Api_ConversionFailed",
                    "An exception " + e + " has been catch");
            return null;    //  Cannot occur
        }
    }
	//===============================================================
    /**
     * De Marshall data from a receive byte buffer
     * @param recData   receive data
     * @param littleIndian endianness to de marshall
     * @return the data after de marshaling
     * @throws DevFailed in case of de marshaling failed
     */
	//===============================================================
    static DeviceInterface deMarshallAttInterfaceChange(byte[] recData, boolean littleIndian) throws DevFailed {
        try {
            //  Remove the 4 first bytes (added for c++ alignment)
            byte[]  buffer = new byte[recData.length-4];
            System.arraycopy(recData, 4, buffer, 0, recData.length - 4);
            CDRInputStream is = new CDRInputStream(ApiUtil.getOrb(), buffer, littleIndian);
            return new DeviceInterface(DevIntrChangeHelper.read(is));
        }
        catch (Exception e) {
            Except.throw_exception("Api_ConversionFailed",
                    "An exception " + e + " has been catch");
            return null;    //  Cannot occur
        }
    }
	//===============================================================
    /**
     * De Marshall data from a receive byte buffer
     * @param recData   receive data
     * @param littleIndian endianness to de marshall
     * @return the data after de marshaling
     * @throws DevFailed in case of de marshaling failed
     */
	//===============================================================
    static AttributeInfoEx deMarshallAttributeConfig(byte[] recData, boolean littleIndian, int idl) throws DevFailed{
        try {
            //  Remove the 4 first bytes (added for c++ alignment)
            byte[]  buffer = new byte[recData.length-4];
            System.arraycopy(recData, 4, buffer, 0, recData.length - 4);
            CDRInputStream is = new CDRInputStream(ApiUtil.getOrb(), buffer, littleIndian);
            if (idl>=5) {
                AttributeConfig_5 attributeConfig_5 = AttributeConfig_5Helper.read(is);
                return new AttributeInfoEx(attributeConfig_5);
            }
            else {
                AttributeConfig_3 attributeConfig_3 = AttributeConfig_3Helper.read(is);
                return new AttributeInfoEx(attributeConfig_3);
            }
        }
        catch (Exception e) {
            Except.throw_exception("Api_ConversionFailed",
                    "An exception " + e + " has been catch");
            return null;    //  Cannot occur
        }
    }
	//===============================================================
    /**
     * De Marshall data from a receive byte buffer
     * @param recData   receive data
     * @param littleIndian endianness to de marshall
     * @param idl   idl revision to convert to attribute value
     * @return the data after de marshaling
     * @throws DevFailed in case of de marshaling failed
     */
	//===============================================================
    static DeviceAttribute deMarshallAttribute(byte[] recData, boolean littleIndian, int idl) throws DevFailed {
        try {
            //  Remove the 4 first bytes (added for c++ alignment)
            byte[]  buffer = new byte[recData.length-4];
            //for (byte b : recData)
            //    System.out.println(b&0xff);

            //  Check device IDL
            if (idl<4) {
                Except.throw_exception("SeverTooOld",
                        "ZMQ events are not supported for IDL " + idl);
            }
            else if (idl<5) {
                System.arraycopy(recData, 4, buffer, 0, recData.length - 4);
                CDRInputStream is = new CDRInputStream(ApiUtil.getOrb(), buffer, littleIndian);

                AttributeValue_4    attributeValue_4 = AttributeValue_4Helper.read(is);
                return new DeviceAttribute(attributeValue_4);
            }
            else {
                System.arraycopy(recData, 4, buffer, 0, recData.length - 4);
                CDRInputStream is = new CDRInputStream(ApiUtil.getOrb(), buffer, littleIndian);

                AttributeValue_5    attributeValue_5 = AttributeValue_5Helper.read(is);
                return new DeviceAttribute(attributeValue_5);
            }
        }
        catch (DevFailed e) {
            throw e;
        }
        catch (Exception e) {
            Except.throw_exception("Api_ConversionFailed",
                    "An exception " + e + " has been catch");
        }
        return null;    //  Cannot occur
    }
	//===============================================================
    /**
     * De Marshall data from a receive byte buffer
     * @param recData   receive data
     * @param littleIndian endianness to de marshall
     * @param idl   idl revision to convert to attribute value
     * @return the data after de marshaling
     * @throws DevFailed in case of de marshaling failed
     */
	//===============================================================
    static DevicePipe deMarshallPipe(byte[] recData, boolean littleIndian, int idl) throws DevFailed {
        try {
            //  Remove the 8 first bytes (added for c++ alignment)
            byte[]  buffer = new byte[recData.length-8];
            /*
            int cnt = 0;
            for (byte b : recData) {
                System.out.print(String.format("ox%x", (b&0xff)) + "  ");
                if ((++cnt%0x10)==0)   System.out.println();
            }
            System.out.println();
            */
            //  Check device IDL
            if (idl<5) {
                Except.throw_exception("SeverTooOld",
                        "Pipe events are not supported for IDL " + idl);
            }
            else {
                System.arraycopy(recData, 8, buffer, 0, recData.length - 8);
                CDRInputStream inputStream = new CDRInputStream(ApiUtil.getOrb(), buffer, littleIndian);
                DevPipeData devPipeData = DevPipeDataHelper.read(inputStream);
                return new DevicePipe(devPipeData);
            }
        }
        catch (DevFailed e) {
            throw e;
        }
        catch (Exception e) {
            Except.throw_exception("Api_ConversionFailed",
                    "An exception " + e + " has been catch");
        }
        return null;    //  Cannot occur
    }
 	//===============================================================
    /**
     * return event type from full event name
     * @param eventName full event name
     * @return event type from full event name
     * @throws DevFailed if no event name found
     */
	//===============================================================
    static int getEventType(String eventName) throws DevFailed{
        int type = -1;
        int pos = eventName.lastIndexOf('.');
        if (pos>0) {
            String  strType = eventName.substring(pos+1);
            //  Since tango 9, idl revision has been added
            if (strType.startsWith("idl")) {
                strType = strType.substring(strType.indexOf('_')+1);
            }

            for (int i=0 ; type<0 && i<TangoConst.eventNames.length ; i++)
                if (strType.equals(TangoConst.eventNames[i]))
                    type = i;
        }
        if (type<0)
            Except.throw_exception("Api_BadParameterException",
                    "Cannot find event type for "+eventName);
        return type;
    }

    //===============================================================
    //===============================================================


    //  Non static methods.
	//===============================================================
    /**
     * Decode data receive from control socket
     * @param bytes input buffer
     * @return decoded data from buffer
     * @throws DevFailed in case of control command unknown
     */
	//===============================================================
    ControlStructure decodeControlBuffer(byte[] bytes) throws DevFailed {

        ControlStructure    controlStructure = new ControlStructure();

        int idx = 0;
        int sizeOfInt = 4;
        controlStructure.commandCode        = bytes[idx++];
        controlStructure.forceReconnection  = (bytes[idx++]!='0');

        switch (controlStructure.commandCode) {
            case ZMQ_END:
                break;  //  Nothing to decode

            case ZMQ_CONNECT_HEARTBEAT:
                //  Get endPoint size (coded as C string)
                controlStructure.endPoint = getString(bytes, idx++);
                idx += controlStructure.endPoint.length();
                controlStructure.eventName = getString(bytes, idx);
                zmqEventTrace(controlStructure.toString());
                break;

            case ZMQ_DISCONNECT_HEARTBEAT:
                controlStructure.eventName = getString(bytes, idx);
                break;

            case ZMQ_CONNECT_EVENT:
                controlStructure.endPoint = getString(bytes, idx++);
                idx += controlStructure.endPoint.length();
                controlStructure.eventName = getString(bytes, idx++);
                idx += controlStructure.eventName.length();
                controlStructure.tango = getInteger(bytes, idx);    idx +=sizeOfInt;
                controlStructure.idl   = getInteger(bytes, idx);    idx +=sizeOfInt;
                int hwm = getInteger(bytes, idx);                   idx +=sizeOfInt;
                controlStructure.hwm   = manageHwmValue(hwm);
                controlStructure.rate  = getInteger(bytes, idx);    idx +=sizeOfInt;
                controlStructure.ivl   = getInteger(bytes, idx);    idx +=sizeOfInt;
                zmqEventTrace(controlStructure.toString());
                break;

            case ZMQ_DISCONNECT_EVENT:
                controlStructure.eventName = getString(bytes, idx);
                break;

            case ZMQ_DELAY_EVENT:
            case ZMQ_RELEASE_EVENT:
            case ZMQ_CONNECT_MCAST_EVENT:
            default:
                Except.throw_exception("API_NotImplemented",
                        "Command " + controlStructure.commandCode + "  NOT yet implemented");
        }

        return controlStructure;
    }
	//===============================================================
	//===============================================================
    private int manageHwmValue(int ctrlValue) {

        //  Check if environment value is set
        String envValue = System.getenv("TANGO_EVENT_BUFFER_HWM");
        if (envValue!=null) {
            try {
                return Integer.parseInt(envValue);
            }
            catch (NumberFormatException e ) {
                System.err.println("TANGO_EVENT_BUFFER_HWM value " + e);
            }
        }
        //  Check if has been set by client
        if (ApiUtil.getEventBufferHWM()>0)
            return ApiUtil.getEventBufferHWM();

        //  Else return default value from input
        return ctrlValue;
    }
	//===============================================================
	//===============================================================
    static void zmqEventTrace(String s) {
        String  env = System.getenv("ZmqTrace");
        if (env!=null && env.equals("true"))
            System.out.println(s);
    }
	//===============================================================
	//===============================================================












 	//===============================================================
    /**
     *   A little class to define the data read from control socket
     */
	//===============================================================
    class ControlStructure {
        int commandCode = -1;
        String  endPoint;
        String  eventName;
        boolean forceReconnection = false;
        int     tango = 0;
        int     idl   = 0;
        int     hwm  = HWM_DEFAULT;
        int     rate = 0;
        int     ivl  = 0;
        //===========================================================
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Command: ").append(commandNames[commandCode]).append("\n");
            if (endPoint!=null)
                sb.append("endPoint: ").append(endPoint).append("\n");
            if (eventName!=null)
                sb.append("eventName: ").append(eventName).append("\n");
            sb.append("int: ").append(hwm).append("  ").append(rate).append("  ")
                    .append(ivl).append("\n");

            return sb.toString();
        }
        //===========================================================
    }
	//===============================================================
	//===============================================================







    //  Trace and dump methods
	//===============================================================
	//===============================================================
    @SuppressWarnings({"UnusedDeclaration"})
    public static void trace(DevVarLongStringArray lsa){
        System.out.println("Svalue");
        for (String s : lsa.svalue)
            System.out.println("	" + s);
        System.out.println("Lvalue");
        for (int i : lsa.lvalue)
            System.out.println("	" + i);
    }
	//===========================================================================
	//===========================================================================
    @SuppressWarnings({"UnusedDeclaration"})
	public static void dump(byte[] rec) {
		for (int i=0 ; i<rec.length ; i++) {

			String	s = String.format("%02x",(0xFF & rec[i]));
			System.out.print("0x" + s + " ");
			if ( ((i+1)%16)==0 )
				System.out.println();
		}
		System.out.println();
	}
	//===============================================================
	//===============================================================
}
