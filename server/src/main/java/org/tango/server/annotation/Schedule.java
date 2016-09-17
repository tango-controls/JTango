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
package org.tango.server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.TimeZone;

/**
 * WARNING: Experimental feature. Schedule a timer for automatic creation with a timeout schedule based on a cron-like
 * time expression. <br>
 * <br>
 * 
 * 
 * An example of a complete cron-expression is the string "0 0 12 ? * WED" - which means
 * "every Wednesday at 12:00:00 pm".<br>
 * 
 * Individual sub-expressions can contain ranges and/or lists. For example, the day of week field in the previous (which
 * reads "WED") example could be replaced with "MON-FRI", "MON,WED,FRI", or even "MON-WED,SAT".<br>
 * 
 * Wild-cards (the '' character) can be used to say "every" possible value of this field. Therefore the '' character in
 * the "Month" field of the previous example simply means "every month". A '*' in the Day-Of-Week field would therefore
 * obviously mean "every day of the week".<br>
 * 
 * All of the fields have a set of valid values that can be specified. These values should be fairly obvious - such as
 * the numbers 0 to 59 for seconds and minutes, and the values 0 to 23 for hours. Day-of-Month can be any value 1-31,
 * but you need to be careful about how many days are in a given month! Months can be specified as values between 0 and
 * 11, or by using the strings JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV and DEC. Days-of-Week can be
 * specified as values between 1 and 7 (1 = Sunday) or by using the strings SUN, MON, TUE, WED, THU, FRI and SAT.<br>
 * 
 * The '/' character can be used to specify increments to values. For example, if you put '0/15' in the Minutes field,
 * it means 'every 15th minute of the hour, starting at minute zero'. If you used '3/20' in the Minutes field, it would
 * mean 'every 20th minute of the hour, starting at minute three' - or in other words it is the same as specifying
 * '3,23,43' in the Minutes field. Note the subtlety that "/35" does *not mean "every 35 minutes" - it mean
 * "every 35th minute of the hour, starting at minute zero" - or in other words the same as specifying '0,35'.<br>
 * 
 * The '?' character is allowed for the day-of-month and day-of-week fields. It is used to specify "no specific value".
 * This is useful when you need to specify something in one of the two fields, but not the other. See the examples below
 * (and CronTrigger JavaDoc) for clarification.<br>
 * 
 * The 'L' character is allowed for the day-of-month and day-of-week fields. This character is short-hand for "last",
 * but it has different meaning in each of the two fields. For example, the value "L" in the day-of-month field means
 * "the last day of the month" - day 31 for January, day 28 for February on non-leap years. If used in the day-of-week
 * field by itself, it simply means "7" or "SAT". But if used in the day-of-week field after another value, it means
 * "the last xxx day of the month" - for example "6L" or "FRIL" both mean "the last friday of the month". You can also
 * specify an offset from the last day of the month, such as "L-3" which would mean the third-to-last day of the
 * calendar month. When using the 'L' option, it is important not to specify lists, or ranges of values, as you'll get
 * confusing/unexpected results.<br>
 * 
 * The 'W' is used to specify the weekday (Monday-Friday) nearest the given day. As an example, if you were to specify
 * "15W" as the value for the day-of-month field, the meaning is: "the nearest weekday to the 15th of the month".<br>
 * 
 * The '#' is used to specify "the nth" XXX weekday of the month. For example, the value of "6#3" or "FRI#3" in the
 * day-of-week field means "the third Friday of the month".<br>
 * 
 * Here are a few more examples of expressions and their meanings - you can find even more in the JavaDoc for
 * org.quartz.CronExpression<br>
 * <br>
 * 
 * Example Cron Expressions<br>
 * 
 * CronTrigger Example 1 - an expression to create a trigger that simply fires every 5 minutes<br>
 * 
 * "0 0/5 * * * ?"<br>
 * 
 * CronTrigger Example 2 - an expression to create a trigger that fires every 5 minutes, at 10 seconds after the minute
 * (i.e. 10:00:10 am, 10:05:10 am, etc.).<br>
 * 
 * "10 0/5 * * * ?"<br>
 * 
 * CronTrigger Example 3 - an expression to create a trigger that fires at 10:30, 11:30, 12:30, and 13:30, on every
 * Wednesday and Friday.<br>
 * 
 * "0 30 10-13 ? * WED,FRI"<br>
 * 
 * CronTrigger Example 4 - an expression to create a trigger that fires every half hour between the hours of 8 am and 10
 * am on the 5th and 20th of every month. Note that the trigger will NOT fire at 10:00 am, just at 8:00, 8:30, 9:00 and
 * 9:30<br>
 * 
 * "0 0/30 8-9 5,20 * ?"<br>
 * 
 * Note that some scheduling requirements are too complicated to express with a single trigger - such as
 * "every 5 minutes between 9:00 am and 10:00 am, and every 20 minutes between 1:00 pm and 10:00 pm". The solution in
 * this scenario is to simply create two triggers, and register both of them to run the same job.<br>
 * 
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Schedule {
    /**
     * The cron expression default value. This value may also be configured as a device property called methodName+
     * "Schedule"
     * 
     * @return the cron expression
     */
    String cronExpression() default "0/20 * * * * ?";

    /**
     * @see TimeZone
     * @return the time zone
     */
    String timezone() default "";

    /**
     * Name of the device propery (boolean) to activate/desactive the scheduler
     * 
     * @return the activation property name
     */
    String activationProperty();

}
