package org.tango.client.database.cache;

import org.tango.client.database.ITangoDB;

public interface ICachableDatabase extends ITangoDB {

    boolean isCacheAvailable();

    String getVersion();

}
