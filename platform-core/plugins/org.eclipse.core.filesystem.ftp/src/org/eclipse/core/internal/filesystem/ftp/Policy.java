/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.core.internal.filesystem.ftp;

import java.util.Date;
import org.eclipse.core.runtime.*;
import org.osgi.framework.Bundle;

public class Policy {

	//general debug flag for the plugin
	public static boolean DEBUG = false;

	public static final String PI_FTP_FILE_SYSTEM = "org.eclipse.core.filesystem.ftp"; //$NON-NLS-1$

	public static void checkCanceled(IProgressMonitor monitor) {
		if (monitor.isCanceled())
			throw new OperationCanceledException();
	}

	/**
	 * Print a debug message to the console. 
	 * Pre-pend the message with the current date and the name of the current thread.
	 */
	public static void debug(String message) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(new Date(System.currentTimeMillis()));
		buffer.append(" - ["); //$NON-NLS-1$
		buffer.append(Thread.currentThread().getName());
		buffer.append("] "); //$NON-NLS-1$
		buffer.append(message);
		System.out.println(buffer.toString());
	}

	public static void error(int code) throws CoreException {
		error(code, null, null);
	}

	public static void error(int code, String message) throws CoreException {
		error(code, message, null);
	}

	public static void error(int code, String message, Throwable exception) throws CoreException {
		int severity = code == 0 ? 0 : 1 << (code % 100 / 33);
		throw new CoreException(new Status(severity, PI_FTP_FILE_SYSTEM, code, message, exception));
	}

	public static void error(int code, Throwable exception) throws CoreException {
		error(code, null, exception);
	}

	public static void log(int severity, String message) {
		log(severity, message, null);
	}

	public static void log(int severity, String message, Throwable t) {
		final Bundle bundle = Platform.getBundle(PI_FTP_FILE_SYSTEM); //$NON-NLS-1$
		if (bundle == null)
			return;
		Platform.getLog(bundle).log(new Status(severity, PI_FTP_FILE_SYSTEM, 1, message, t));
	}

	public static IProgressMonitor monitorFor(IProgressMonitor monitor) {
		return monitor == null ? new NullProgressMonitor() : monitor;
	}

	public static IProgressMonitor subMonitorFor(IProgressMonitor monitor, int ticks) {
		if (monitor == null)
			return new NullProgressMonitor();
		if (monitor instanceof NullProgressMonitor)
			return monitor;
		return SubMonitor.convert(monitor, ticks);
	}

	public static IProgressMonitor subMonitorFor(IProgressMonitor monitor, int ticks, int style) {
		if (monitor == null)
			return new NullProgressMonitor();
		if (monitor instanceof NullProgressMonitor)
			return monitor;
		return SubMonitor.convert(monitor, ticks, style);
	}
}
