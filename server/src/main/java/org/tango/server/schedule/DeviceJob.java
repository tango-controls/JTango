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
package org.tango.server.schedule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public final class DeviceJob implements Job {

    private Object businessObject;
    private Method method;

    public DeviceJob() {

    }

    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException {
	try {
	    method.invoke(businessObject);
	} catch (final IllegalArgumentException e) {
	    throw new JobExecutionException(e);
	} catch (final IllegalAccessException e) {
	    throw new JobExecutionException(e);
	} catch (final InvocationTargetException e) {
	    throw new JobExecutionException(e);
	}
    }

    public void setDevice(final Object businessObject) {
	this.businessObject = businessObject;
    }

    public void setMethod(final Method method) {
	this.method = method;
    }

}
