package org.tango.server.events;

import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.TangoApi.events.ZmqEventConsumer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 10/25/18
 */
public class EventManagerTest {
    private EventManager instance;

    @Before
    public void before() throws Exception {
        instance = new EventManager();
//        instance.initialize();
    }

    @After
    public void after(){
        instance.close();
    }

    @Test
    public void simple() throws Exception {
        Iterable<String> ips = instance.getIp4Addresses();

        for(String ip : ips){
            System.out.println(ip);
        }
    }

    //@Test requires TANGO_HOST
    public void pubsub() throws Exception {
        DevVarLongStringArray connection = instance.subscribe("x/y/z");

        for(String endpoint : connection.svalue){
            System.out.println(endpoint);
        }

    }
}