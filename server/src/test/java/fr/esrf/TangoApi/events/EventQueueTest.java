package fr.esrf.TangoApi.events;

import fr.esrf.Tango.AttDataReady;
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.*;
import fr.esrf.TangoDs.TangoConst;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tango.server.testserver.NoDBDeviceManager;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EventQueueTest extends NoDBDeviceManager {

    private static final int TEST_MAX_SIZE = 10;
    private final EventQueue testEventQueue = new EventQueue(TEST_MAX_SIZE);

    private final List<EventData> eventDataList;

    private static final String DEFAULT_PIPE_NAME = "testPipe";
    private static final String DEFAULT_PIPE_BLOB_NAME = "testBlob";

    public EventQueueTest() throws DevFailed {
        eventDataList = Arrays.asList(
                createEventData("TEST_EVENT_1",
                        "SHORT_CHANGE_EVENT",
                        TangoConst.CHANGE_EVENT,
                        getAttribute("shortScalar", 10)),
                createEventData("TEST_EVENT_2",
                        "LONG_CHANGE_EVENT",
                        TangoConst.CHANGE_EVENT,
                        getAttribute("longScalar", 20)),
                createEventData("TEST_EVENT_3",
                        "LONG_PERIODIC_EVENT",
                        TangoConst.PERIODIC_EVENT,
                        getAttribute("intScalar", 30))
        );
    }

    @Before
    public void setup() {
        eventDataList.forEach(testEventQueue::insert_event);
    }

    @Test
    public void testGetEventFromQueue() throws DevFailed {
        int size = testEventQueue.size();
        for (int i = 0; i < size; i++) {
            EventData eventData = testEventQueue.getNextEvent();
            doTestEvent(eventData, i);
        }

        Assert.assertThrows(DevFailed.class, testEventQueue::getNextEvent);
    }

    @Test
    public void testGetEventByType() throws DevFailed {
        EventData eventData = testEventQueue.getNextEvent(TangoConst.PERIODIC_EVENT);
        doTestEvent(eventData, 2);

        eventData = testEventQueue.getNextEvent(TangoConst.CHANGE_EVENT);
        doTestEvent(eventData, 1);

        eventData = testEventQueue.getNextEvent(TangoConst.CHANGE_EVENT);
        doTestEvent(eventData, 0);

        Assert.assertThrows(DevFailed.class, () -> testEventQueue.getNextEvent(TangoConst.CHANGE_EVENT));
    }

    @Test
    public void testGetEvents() {
        EventData[] events = testEventQueue.getEvents();

        int counter = 0;
        for (EventData event : events) {
            doTestEvent(event, counter);
            counter++;
        }
    }

    @Test
    public void testGetEventsByType() {
        EventData[] events = testEventQueue.getEvents(TangoConst.PERIODIC_EVENT);
        Assert.assertEquals(1, events.length);

        events = testEventQueue.getEvents(TangoConst.CHANGE_EVENT);
        Assert.assertEquals(2, events.length);

        Assert.assertTrue(testEventQueue.is_empty());
    }

    @Test
    public void testGetLastEvent() throws DevFailed {
        int size = testEventQueue.size();
        for (int i = 0; i < size; i++) {
            long time = testEventQueue.getLastEventDate();
            Date date = new Date(time);
            Assert.assertNotNull(date);
            testEventQueue.getNextEvent();
        }

        Assert.assertThrows(DevFailed.class, testEventQueue::getLastEventDate);
    }

    private void doTestEvent(EventData event, int index) {
        Assert.assertNotNull(event);
        Assert.assertNotNull(event.device);

        EventData baseEvent = eventDataList.get(index);
        Assert.assertEquals(baseEvent.event, event.event);
        Assert.assertEquals(baseEvent.name, event.name);
        Assert.assertEquals(baseEvent.attr_config.name, event.attr_config.name);

        Assert.assertTrue(event.isAttrValue());
        Assert.assertTrue(event.isAttrConfig());
        Assert.assertTrue(event.isAttrDataReady());
    }

    private EventData createEventData(String name, String event, int eventType,
                                      DeviceAttribute attribute) throws DevFailed {
        DeviceProxy deviceProxy = getDeviceProxy();
        return new EventData(
                deviceProxy,
                name,
                event,
                eventType,
                EventData.ZMQ_EVENT,
                attribute,
                getPipeBlob(),
                deviceProxy.get_attribute_info_ex(attribute.getName()),
                new AttDataReady(),
                getDeviceInterface(),
                new DevError[]{}
        );
    }

    private DeviceProxy getDeviceProxy() throws DevFailed {
        return new DeviceProxy(deviceName);
    }

    private DeviceInterface getDeviceInterface() throws DevFailed {
        return new DeviceInterface(deviceName);
    }

    private DeviceAttribute getAttribute(String name, int value) {
        return new DeviceAttribute(name, value);
    }

    private DevicePipe getPipeBlob() {
        return new DevicePipe(DEFAULT_PIPE_NAME, new PipeBlob(DEFAULT_PIPE_BLOB_NAME));
    }
}
