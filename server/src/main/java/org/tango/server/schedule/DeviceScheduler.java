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

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.server.annotation.Schedule;
import org.tango.server.properties.PropertiesUtils;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

public final class DeviceScheduler {
    private static final String JOB_PARAM_DEVICE = "device";
    private static final String JOB_PARAM_METHOD = "method";
    private static final String SCHEDULE_PROP = "schedule";
    private final Logger logger = LoggerFactory.getLogger(DeviceScheduler.class);

    private final Set<Method> methodList;
    // private final String defaultCronExpression;
    // private final String timeZone;
    private final Object businessObject;
    // private final String activationProperty;
    private Scheduler quartz;
    private final String deviceName;
    private final String className;
    private static int poolSize = Math.min(10, Runtime.getRuntime().availableProcessors());

    public DeviceScheduler(final Object businessObject, final Set<Method> methodList, final String deviceName,
            final String className) {
        this.businessObject = businessObject;
        this.methodList = methodList;
        this.deviceName = deviceName;
        this.className = className;
        // defaultCronExpression = annot.cronExpression();
        // timeZone = annot.timezone();
        // activationProperty = annot.activationProperty();
    }

    public void triggerJob() throws DevFailed {

        for (final Method method : methodList) {
            final Schedule config = method.getAnnotation(Schedule.class);
            // get device property for activation
            final String[] activationProps = PropertiesUtils.getDeviceProperty(deviceName, className,
                    config.activationProperty());
            boolean active = false;
            if (activationProps.length > 0 && (activationProps[0].equalsIgnoreCase("true")
                    || activationProps[0].equalsIgnoreCase("1"))) {
                active = true;
            }
            // the job is started only if device property activationProperty is true
            if (active) {
                try {
                    if (quartz == null) {
                        // configure and start the scheduler
                        final StdSchedulerFactory fact = new StdSchedulerFactory();
                        final Properties props = new Properties();
                        logger.debug("setting pool size to  {}", poolSize);
                        props.put("org.quartz.threadPool.threadCount", Integer.toString(poolSize));
                        fact.initialize(props);
                        quartz = fact.getScheduler();
                        quartz.start();
                    }
                } catch (final SchedulerException e) {
                    throw DevFailedUtils.newDevFailed(e);
                }

                // get cron parameters from device property
                final String devicePropName = method.getName() + SCHEDULE_PROP;
                final String[] deviceProps = PropertiesUtils.getDeviceProperty(deviceName, className, devicePropName);
                String cronExpression = "";
                if (deviceProps.length > 0) {
                    cronExpression = deviceProps[0];
                }
                if (cronExpression.isEmpty()) {
                    cronExpression = config.cronExpression();
                }
                // final configure cron job
                CronTrigger trigger = null;
                try {
                    if (!config.timezone().isEmpty()) {
                        trigger = newTrigger().withSchedule(
                                cronSchedule(cronExpression).inTimeZone(TimeZone.getTimeZone(config.timezone())))
                                .build();
                    } else {
                        trigger = newTrigger().withSchedule(cronSchedule(cronExpression)).build();
                    }
                } catch (final RuntimeException e) {
                    throw DevFailedUtils.newDevFailed("unable to start device scheduler for " + method + " - error is: "
                            + e.getMessage());
                }

                try {
                    if (quartz == null) {
                        // configure and start the scheduler
                        final StdSchedulerFactory fact = new StdSchedulerFactory();
                        final Properties props = new Properties();
                        logger.debug("setting pool size to  {}", poolSize);
                        props.put("org.quartz.threadPool.threadCount", Integer.toString(poolSize));
                        fact.initialize(props);
                        quartz = fact.getScheduler();
                        quartz.start();
                    }
                    // start the job
                    final JobDetail job = newJob(DeviceJob.class).withIdentity(method.getName(), deviceName).build();
                    job.getJobDataMap().put(JOB_PARAM_DEVICE, businessObject);
                    job.getJobDataMap().put(JOB_PARAM_METHOD, method);
                    quartz.scheduleJob(job, trigger);
                    logger.debug("start job on {} started with {}", method, cronExpression);
                } catch (final SchedulerException e) {
                    throw DevFailedUtils.newDevFailed(e);
                }
            }
        }
    }

    public static void setThreadPoolSize(final int poolSize) {
        DeviceScheduler.poolSize = poolSize;
    }

    public void stop() throws DevFailed {
        try {
            if (quartz != null) {
                for (final Method method : methodList) {
                    logger.debug("delete job for {}", method.getName());
                    quartz.deleteJob(new JobKey(method.getName(), deviceName));
                }
            }
        } catch (final SchedulerException e) {
            throw DevFailedUtils.newDevFailed(e);
        }
    }
}
