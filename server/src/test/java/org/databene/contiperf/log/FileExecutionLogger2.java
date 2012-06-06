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
package org.databene.contiperf.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.databene.contiperf.ExecutionLogger;
import org.databene.contiperf.util.ContiPerfUtil;

public class FileExecutionLogger2 implements ExecutionLogger {
    private static final DateFormat formatFile = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
    private static final DateFormat format = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss");
    private static String dateFile = formatFile.format(new Date());
    private static String date = format.format(new Date());

    private static String FILENAME = "TestPerf" + "_" + dateFile + ".log";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private static boolean firstCall = true;
    static AtomicLong invocationCount = new AtomicLong();

    public FileExecutionLogger2(final String type, final int nbrTest) {

	if (firstCall) {
	    createSummaryFile();
	    firstCall = false;
	    logCondition(type, nbrTest);
	}

    }

    public void logInvocation(final String id, final int latency, final long startTime) {
	invocationCount.incrementAndGet();
	System.out.println(id + ',' + latency + ',' + startTime);
    }

    public void logSummary(final String id, final long elapsedTime, final long invocationCount, final long startTime) {
	OutputStream out = null;
	final String message = id + LINE_SEPARATOR + "\t invocationCount = " + invocationCount + LINE_SEPARATOR
		+ "\t elapsedTime = " + elapsedTime + LINE_SEPARATOR + "\t average = " + (float) elapsedTime
		/ (float) invocationCount + LINE_SEPARATOR + "\t startTime = " + startTime + LINE_SEPARATOR
		+ LINE_SEPARATOR;
	try {
	    out = new FileOutputStream(FILENAME, true);
	    out.write(message.getBytes());
	} catch (final IOException e) {
	    e.printStackTrace();
	} finally {
	    ContiPerfUtil.close(out);
	}
    }

    public long invocationCount() {
	return invocationCount.get();
    }

    // private helpers
    // -------------------------------------------------------------------------------------------------
    /*
     * type = local ou distant nbrTest = nombre de test
     */
    private void logCondition(final String type, final int nbrTest) {
	OutputStream out = null;
	final String message = "Test de performance du " + date + LINE_SEPARATOR + "Type = " + type + LINE_SEPARATOR
		+ "Invocation = " + nbrTest + LINE_SEPARATOR + LINE_SEPARATOR;
	try {
	    out = new FileOutputStream(FILENAME, true);
	    out.write(message.getBytes());
	} catch (final IOException e) {
	    e.printStackTrace();
	} finally {
	    ContiPerfUtil.close(out);
	}
    }

    private void createSummaryFile() {
	final File file = new File(".", FILENAME);
	try {
	    ensureDirectoryExists(file.getParentFile());
	    if (file.exists()) {
		file.delete();
	    }
	} catch (final FileNotFoundException e) {
	    System.out.println("Unable to create directory: " + file.getAbsolutePath());
	}
    }

    private void ensureDirectoryExists(final File dir) throws FileNotFoundException {
	final File parent = dir.getParentFile();
	if (!dir.exists()) {
	    if (parent == null) {
		throw new FileNotFoundException();
	    }
	    ensureDirectoryExists(parent);
	    dir.mkdir();
	}
    }

}
