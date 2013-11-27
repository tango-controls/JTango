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
package org.tango.server.cache;

import java.util.Locale;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.constructs.blocking.SelfPopulatingCache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CacheRefresher implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(CacheRefresher.class);
    private final SelfPopulatingCache cache;
    private final String name;

//    private final Profiler profiler;

    public CacheRefresher(final SelfPopulatingCache cache, final String name) {
        this.cache = cache;
        this.name = name.toLowerCase(Locale.ENGLISH);
//        profiler = new Profiler("tangoCache");
//        profiler.start(cache.getName());
    }

    @Override
    public void run() {
        logger.debug("refresh {}", cache.getName());

//        profiler.stop().print();
//        profiler.start(cache.getName());
        try {
            cache.get(name);
            cache.refresh();
        } catch (final CacheException e) {
            logger.error("error {}", e.getCause());
        }

    }
}
