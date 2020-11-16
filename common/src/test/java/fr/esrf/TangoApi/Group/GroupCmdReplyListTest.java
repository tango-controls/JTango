package fr.esrf.TangoApi.Group;

import fr.esrf.Tango.DevFailed;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class GroupCmdReplyListTest extends CollectionTestHelper<GroupCmdReplyList, GroupCmdReply> {

    public GroupCmdReplyListTest() {
        super(GroupCmdReplyList.class);
    }

    @Before
    public void setup() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        setup("testDevice1", "testCommand1", "testDevice2", "testCommand2");
    }

    @Test
    public void readGroupCmdReplyTest() {
        doTestRead(2);
        Assert.assertEquals(2, collection.size());
    }

    @Test
    public void addGroupCmdReplyTest() {
        GroupCmdReply groupCmdReply = doTestAdd("testDevice3", "newAtt");
        Assert.assertThrows(DevFailed.class, groupCmdReply::get_data);
    }

    @Test
    public void resetGroupCmdReplyTest() {
        collection.reset();
        Assert.assertEquals(0, collection.size());
        Assert.assertFalse(collection.has_failed);
    }

    @Override
    protected GroupCmdReply create(String devName, String commandName) {
        return new GroupCmdReply(devName, commandName, new DevFailed());
    }

}
