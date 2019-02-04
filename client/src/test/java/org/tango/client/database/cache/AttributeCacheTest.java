package org.tango.client.database.cache;

import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class AttributeCacheTest {

    @Test
    public void testToString() {

        AttributeCache attributeCache = new AttributeCache("cache");
        attributeCache.addProperty("testpropName", new String[]{"1", "2"});
      assertThat(attributeCache.toString(), equalTo("cache = [testpropname = [1, 2],]"));

    }
}