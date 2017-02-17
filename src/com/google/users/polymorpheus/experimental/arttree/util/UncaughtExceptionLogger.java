package com.google.users.polymorpheus.experimental.arttree.util;

import com.amd.aparapi.Config;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UncaughtExceptionLogger implements UncaughtExceptionHandler {

    private static final Logger logger = Logger.getLogger(Config.getLoggerName());
	@Override
	public void uncaughtException(Thread thread, Throwable thrown) {
		logger.log(Level.SEVERE, thread.getName(), thrown);
	}

}
