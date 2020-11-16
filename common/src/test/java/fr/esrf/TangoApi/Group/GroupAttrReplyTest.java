package fr.esrf.TangoApi.Group;

import fr.esrf.Tango.DevFailed;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class GroupAttrReplyTest extends CollectionTestHelper<GroupAttrReplyList, GroupAttrReply> {

    public GroupAttrReplyTest() {
        super(GroupAttrReplyList.class);
    }

    @Before
    public void setup() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        setup("testDevice1", "testAttr1", "testDevice2", "testAttr2");
    }

    @Test
    public void readGroupAttReplyTest() {
        doTestRead(2);
        Assert.assertEquals(2, collection.size());
    }

    @Test
    public void addGroupAttReplyTest() {
        GroupAttrReply groupAttrReply = doTestAdd("testDevice3", "newAtt");
        Assert.assertThrows(DevFailed.class, groupAttrReply::get_data);

        Assert.assertEquals(3, collection.size());
    }

    @Test
    public void resetGroupAttReplyTest() {
        collection.reset();
        Assert.assertEquals(0, collection.size());
        Assert.assertFalse(collection.has_failed);
    }

    @Override
    protected GroupAttrReply create(String devName, String attrName) {
        return new GroupAttrReply(devName, attrName, new DevFailed());
    }
}
