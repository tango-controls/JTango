package fr.esrf.TangoApi.Group;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.DeviceData;
import fr.esrf.TangoApi.DeviceProxy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tango.server.testserver.NoDBDeviceManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class GroupTest extends NoDBDeviceManager {

    private Group group;

    private static final String name = "testDevicesGroup";
    private static final String NESTED_GROUP_TEST_OBJ_NAME = "nestedGroupTestObject";

    private static final String deviceOne = "1/1/1";
    private static final String deviceTwo = "2/2/2";
    private static final String deviceThree = "3/3/3";

    private static final List<String> devicesName = Arrays.asList(deviceOne, deviceTwo, deviceThree);

    private static final String GET_NAME_COMMAND_NAME = "getName";
    private static final String INT_COMMAND_NAME = "intCommand";
    private static final int INT_COMMAND_DEFAULT_VALUE = 23;
    private static final int INT_COMMAND_SECOND_VALUE = 532;
    private static final int INT_COMMAND_THREE_VALUE = 234;

    private static final int TIMEOUT_VALUE = 500;

    private static final String TEST_ATTRIBUTE_LONG_NAME = "longScalar";
    private static final long TEST_ATTRIBUTE_LONG_VALUE = 342L;

    private static final String TEST_ATTRIBUTE_INT_NAME = "intScalar";
    private static final int TEST_ATTRIBUTE_INT_VALUE = 423;

    private static final DeviceAttribute DEVICE_ATTRIBUTE_LONG = new DeviceAttribute(TEST_ATTRIBUTE_LONG_NAME,
            TEST_ATTRIBUTE_LONG_VALUE);

    private static final DeviceAttribute DEVICE_ATTRIBUTE_INT = new DeviceAttribute(TEST_ATTRIBUTE_INT_NAME,
            TEST_ATTRIBUTE_INT_VALUE);

    @BeforeClass
    public static void startDevice() throws IOException, DevFailed {
        startDevice(String.format("%s,%s,%s", deviceOne, deviceTwo, deviceThree));
    }

    @Before
    public void setup() {
        group = new Group(name);
    }

    @Test
    public void testAddAndFindDeviceInGroup() throws DevFailed {
        addAllDeviceToGroup();
        for (int i = 0; i < deviceFullNameList.size(); i++) {
            findAndTestGroupElement(group, i, false);
        }

        String[] deviceList = group.get_device_name_list(false);
        Assert.assertEquals(deviceFullNameList, Arrays.asList(deviceList));
    }

    @Test
    public void testAddNestedGroup() throws DevFailed {
        Group nestedGroupTestObject = createNestedGroupTestObject();

        Group nestedGroup = nestedGroupTestObject.get_group(name);
        Assert.assertEquals(group, nestedGroup);

        findAndTestGroupElement(nestedGroupTestObject, 0, true);
        findAndTestGroupElement(nestedGroupTestObject, 1, true);
        findAndTestGroupElement(nestedGroupTestObject, 2, false);

        doTestNestedGroupDeviceNameList(nestedGroupTestObject);
        doTestNestedGroupDeviceList(nestedGroupTestObject);
    }

    @Test
    public void testGetDeviceProxyFromGroup() throws DevFailed {
        addAllDeviceToGroup();
        Assert.assertTrue(group.ping(false));

        doTestGetDeviceProxy(group,
                group.get_size(false) + 1);

        cleanGroup();

        Group nestedGroupTestObject = createNestedGroupTestObject();
        doTestGetDeviceProxy(nestedGroupTestObject,
                nestedGroupTestObject.get_size(true) + 1);
    }

    @Test
    public void testRemoveAllElementFromGroup() throws DevFailed {
        Group nestedGroupTestObject = createNestedGroupTestObject();
        nestedGroupTestObject.remove("*", false);

        Assert.assertEquals(0, nestedGroupTestObject.get_size(false));

        group.remove(name, false);
        Assert.assertEquals(2, group.get_size(false));

        nestedGroupTestObject = createNestedGroupTestObject(false);
        nestedGroupTestObject.remove("*", true);

        Assert.assertEquals(0, nestedGroupTestObject.get_size(false));
        Assert.assertEquals(0, group.get_size(false));

        nestedGroupTestObject = createNestedGroupTestObject();
        nestedGroupTestObject.remove_all();
        Assert.assertEquals(0, nestedGroupTestObject.get_size(false));
        Assert.assertEquals(2, group.get_size(false));

        nestedGroupTestObject = createNestedGroupTestObject(false);
        nestedGroupTestObject.remove_all(true);
        Assert.assertEquals(0, nestedGroupTestObject.get_size(false));
        Assert.assertEquals(0, group.get_size(false));
    }

    @Test
    public void testDumpGroup() throws DevFailed {

        // Prepare system out stream for test
        final PrintStream stdout = System.out;
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        Group nestedGroupTestObject = createNestedGroupTestObject();
        nestedGroupTestObject.dump();
        group.dump();

        // TODO possible improvements: check that string in the out stream is what we expect
        Assert.assertNotNull(outputStreamCaptor.toString());

        // Restore default system out stream
        System.setOut(stdout);
    }

    @Test
    public void testExecuteCmd() throws DevFailed {
        addAllDeviceToGroup();
        GroupCmdReplyList replyList = group.command_inout(GET_NAME_COMMAND_NAME, false);
        doTestGetNameGroupCmdReply(replyList);

        DeviceData defaultDD = createIntDD(INT_COMMAND_DEFAULT_VALUE);
        replyList = group.command_inout(INT_COMMAND_NAME, defaultDD, false);
        doTestIntCommandGroupCmdReply(replyList);

        DeviceData[] dds = new DeviceData[]{
                defaultDD,
                createIntDD(INT_COMMAND_SECOND_VALUE),
                createIntDD(INT_COMMAND_THREE_VALUE)
        };

        replyList = group.command_inout(INT_COMMAND_NAME, dds, false);
        doTestIntDifferentDDGroupCmdReply(replyList);
    }

    @Test
    public void testExecuteCmdInNestedGroup() throws DevFailed {
        Group nestedGroupTestObject = createNestedGroupTestObject();
        DeviceData[] dds = new DeviceData[]{
                createIntDD(INT_COMMAND_DEFAULT_VALUE),
                createIntDD(INT_COMMAND_SECOND_VALUE),
                createIntDD(INT_COMMAND_THREE_VALUE)
        };

        GroupCmdReplyList replyList = nestedGroupTestObject.command_inout(INT_COMMAND_NAME, dds, true);
        doTestIntDifferentDDGroupCmdReply(replyList);
    }

    @Test
    public void testSetTimeout() throws DevFailed {
        addAllDeviceToGroup();
        group.set_timeout_millis(TIMEOUT_VALUE, false);
        for (int i = 1; i < group.get_size(false); i++) {
            DeviceProxy deviceProxy = group.get_device(i);
            Assert.assertEquals(TIMEOUT_VALUE, deviceProxy.get_timeout_millis());
        }
    }

    @Test
    public void testFailedExecuteCmd() throws DevFailed {
        addAllDeviceToGroup();
        GroupCmdReplyList replyList = group.command_inout("BAD_METHOD_NAME", false);

        Assert.assertTrue(replyList.has_failed());
        for (GroupCmdReply reply : replyList) {
            Assert.assertTrue(reply.has_failed());
        }
    }

    @Test
    public void testGroupAttributeReadWrite() throws DevFailed {
        addAllDeviceToGroup();

        GroupReplyList attrWriteReplies = group.write_attribute(DEVICE_ATTRIBUTE_LONG, false);
        doTestLongReplyList(attrWriteReplies);

        GroupAttrReplyList attrReadReplies = group.read_attribute(TEST_ATTRIBUTE_LONG_NAME, false);
        doTestLongGroupAttrReplyList(attrReadReplies);
    }

    @Test
    public void testGroupAttributeReadWriteAsync() throws DevFailed {
        addAllDeviceToGroup();

        // This will failed because we have 2 attr and 3 device in the group
        // first attr will be save to the first device
        // second to second device in the list e.t.c
        // maybe this should work that attributes in the list
        // will be write to the all devices in the group?
        Assert.assertThrows(DevFailed.class,
                () -> group.write_attribute_asynch(new DeviceAttribute[]{DEVICE_ATTRIBUTE_LONG, DEVICE_ATTRIBUTE_INT},
                        false)
        );
        group.remove(deviceFullNameList.get(2), false);

        group.write_attribute_asynch(new DeviceAttribute[]{DEVICE_ATTRIBUTE_LONG, DEVICE_ATTRIBUTE_INT}, false);

        GroupAttrReplyList attrReadReplies = group.read_attribute(TEST_ATTRIBUTE_LONG_NAME, false);
        doTestLongAttrReply(attrReadReplies.get(0), 0);

        attrReadReplies = group.read_attribute(TEST_ATTRIBUTE_INT_NAME, false);
        doTestIntAttrReply(attrReadReplies.get(1), 1);
    }

    @Test
    public void testGroupAttributeReadWriteAsyncNestedGroup() throws DevFailed {
        // test nested group!
        Group nestedGroupTestObject = createNestedGroupTestObject();
        nestedGroupTestObject.remove(deviceFullNameList.get(2), false);

        int rid = nestedGroupTestObject.write_attribute_asynch(new DeviceAttribute[]{DEVICE_ATTRIBUTE_LONG,
                        DEVICE_ATTRIBUTE_INT},
                true);
        nestedGroupTestObject.write_attribute_reply(rid, 0);

        GroupAttrReplyList attrReadReplies = nestedGroupTestObject.read_attribute(TEST_ATTRIBUTE_LONG_NAME, true);
        doTestLongAttrReply(attrReadReplies.get(0), 0);

        attrReadReplies = nestedGroupTestObject.read_attribute(TEST_ATTRIBUTE_INT_NAME, true);
        doTestIntAttrReply(attrReadReplies.get(1), 1);
    }

    private void addAllDeviceToGroup() throws DevFailed {
        for (String device : deviceFullNameList) {
            group.add(device);
        }
    }

    private void findAndTestGroupElement(Group group, int index, boolean fwd) {
        Assert.assertTrue(group.contains(deviceFullNameList.get(index), fwd));

        GroupElement groupElement = group.find(deviceFullNameList.get(index), fwd);
        doTestGroupElement(groupElement, deviceFullNameList.get(index), devicesName.get(index));
    }

    private Group createNestedGroupTestObject() throws DevFailed {
        return createNestedGroupTestObject(true);
    }

    private Group createNestedGroupTestObject(boolean initGroup) throws DevFailed {
        if (initGroup) {
            String[] devices = new String[]{deviceFullNameList.get(0), deviceFullNameList.get(1)};
            group.add(devices);
        }

        Group nestedGroupTestObject = new Group(NESTED_GROUP_TEST_OBJ_NAME);
        nestedGroupTestObject.add(group);
        nestedGroupTestObject.add(deviceFullNameList.get(2));
        return nestedGroupTestObject;
    }

    private void cleanGroup() {
        for (String deviceFullName : deviceFullNameList) {
            group.remove(deviceFullName, false);
        }
    }

    private void doTestGroupElement(GroupElement groupElement, String deviceFullTangoName,
                                    String deviceName) {
        Assert.assertNotNull(groupElement);
        Assert.assertEquals(deviceFullTangoName, groupElement.get_name());
        Assert.assertEquals(deviceName, groupElement.get_device_i(1).name());
    }

    private void doTestNestedGroupDeviceNameList(Group group) {
        String[] deviceList = group.get_device_name_list(true);
        Assert.assertEquals(deviceFullNameList, Arrays.asList(deviceList));

        deviceList = group.get_device_name_list(false);
        Assert.assertEquals(1, deviceList.length);
        Assert.assertEquals(deviceFullNameList.get(2), deviceList[0]);
    }

    private void doTestNestedGroupDeviceList(Group group) {
        String[] deviceList = group.get_device_list(true);
        Assert.assertEquals(deviceFullNameList, Arrays.asList(deviceList));

        deviceList = group.get_device_list(false);
        Assert.assertEquals(1, deviceList.length);
        Assert.assertEquals(deviceFullNameList.get(2), deviceList[0]);
    }

    private void doTestGetDeviceProxy(Group groupList, int groupSize) {
        for (int i = 1; i < groupSize; i++) {
            DeviceProxy deviceProxy = groupList.get_device(i);

            String deviceName = devicesName.get(i - 1);
            Assert.assertEquals(deviceName, deviceProxy.get_name());

            String deviceFullName = deviceFullNameList.get(i - 1);
            deviceProxy = groupList.get_device(deviceFullName);
            Assert.assertEquals(deviceName, deviceProxy.get_name());
        }
    }

    private void doTestGetNameGroupCmdReply(GroupCmdReplyList replyList) throws DevFailed {
        int counter = 0;
        for (GroupCmdReply reply : replyList) {
            Assert.assertFalse(reply.has_failed());
            Assert.assertFalse(reply.has_timeout());
            Assert.assertEquals(deviceFullNameList.get(counter), reply.dev_name());
            Assert.assertEquals(devicesName.get(counter), reply.get_data().extractString());

            counter++;
        }
    }

    private void doTestIntCommandGroupCmdReply(GroupCmdReplyList replyList) throws DevFailed {
        int counter = 0;
        for (GroupCmdReply reply : replyList) {
            doTestIntCommandCmdReply(reply, counter, INT_COMMAND_DEFAULT_VALUE);
            counter++;
        }
    }

    private void doTestIntDifferentDDGroupCmdReply(GroupCmdReplyList replyList) throws DevFailed {
        doTestIntCommandCmdReply(replyList.get(0), 0, INT_COMMAND_DEFAULT_VALUE);
        doTestIntCommandCmdReply(replyList.get(1), 1, INT_COMMAND_SECOND_VALUE);
        doTestIntCommandCmdReply(replyList.get(2), 2, INT_COMMAND_THREE_VALUE);
    }

    private void doTestIntCommandCmdReply(GroupCmdReply reply, int counter, int value) throws DevFailed {
        Assert.assertFalse(reply.has_failed());
        Assert.assertFalse(reply.has_timeout());
        Assert.assertEquals(deviceFullNameList.get(counter), reply.dev_name());
        Assert.assertEquals(value, reply.get_data().extractLong());
    }

    private void doTestLongGroupAttrReplyList(GroupAttrReplyList attrReadReplies) throws DevFailed {
        int counter = 0;
        for (GroupAttrReply reply : attrReadReplies) {
            doTestLongAttrReply(reply, counter);
            counter++;
        }
    }

    private void doTestLongAttrReply(GroupAttrReply attrReply, int counter) throws DevFailed {
        Assert.assertEquals(deviceFullNameList.get(counter), attrReply.dev_name());
        Assert.assertEquals(TEST_ATTRIBUTE_LONG_NAME, attrReply.obj_name());

        DeviceAttribute attribute = attrReply.get_data();
        long readValue = attribute.extractLong64();

        // Because maxAlarm is setup to 10 in JTangoTest
        Assert.assertEquals(readValue > 10 ? AttrQuality.ATTR_ALARM : AttrQuality.ATTR_VALID,
                attribute.getQuality());

        Assert.assertEquals(AttrDataFormat.SCALAR, attribute.getDataFormat());
        Assert.assertEquals(TEST_ATTRIBUTE_LONG_VALUE, readValue);
    }

    private void doTestIntAttrReply(GroupAttrReply attrReply, int counter) throws DevFailed {
        Assert.assertEquals(deviceFullNameList.get(counter), attrReply.dev_name());
        Assert.assertEquals(TEST_ATTRIBUTE_INT_NAME, attrReply.obj_name());

        DeviceAttribute attribute = attrReply.get_data();
        Assert.assertEquals(AttrQuality.ATTR_VALID, attribute.getQuality());
        Assert.assertEquals(AttrDataFormat.SCALAR, attribute.getDataFormat());
        Assert.assertEquals(TEST_ATTRIBUTE_INT_VALUE, attribute.extractLong());
    }

    private void doTestLongReplyList(GroupReplyList attrWriteReplies) {
        int counter = 0;
        for (GroupReply groupReply : attrWriteReplies) {
            Assert.assertFalse(groupReply.has_failed());

            Assert.assertEquals(deviceFullNameList.get(counter), groupReply.dev_name());
            Assert.assertEquals(TEST_ATTRIBUTE_LONG_NAME, groupReply.obj_name());

            counter++;
        }
    }

    private DeviceData createIntDD(int value) throws DevFailed {
        DeviceData data = new DeviceData();
        data.insert(value);
        return data;
    }
}
