package fr.esrf.TangoApi.Group;

import fr.esrf.Tango.DevFailed;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class GroupReplyListTest extends CollectionTestHelper<GroupReplyList, GroupReply> {

    public GroupReplyListTest() {
        super(GroupReplyList.class);
    }

    @Before
    public void setup() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        setup("testDevice1", "testAttr1", "testDevice2", "testAttr2");
    }

    @Test
    public void readGroupReplyTest() {
        doTestRead(2);
        Assert.assertEquals(2, collection.size());
    }

    @Test
    public void addGroupReplyTest() {
        doTestAdd("testDevice3", "newCommand");
        Assert.assertEquals(3, collection.size());
    }

    @Test
    public void resetGroupReplyTest() {
        collection.reset();
        Assert.assertEquals(0, collection.size());
        Assert.assertFalse(collection.has_failed);
    }

    @Override
    protected GroupReply create(String devName, String objName) {
        return new GroupReply(devName, objName, new DevFailed());
    }
}
