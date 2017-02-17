package com.google.users.polymorpheus.experimental.arttree.util;

import com.amd.aparapi.Config;
import com.amd.aparapi.Kernel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public abstract class KernelWithFinalizer extends Kernel {

    private static final Logger logger = Logger.getLogger(Config.getLoggerName());
	private static final ExecutorService disposer = Executors.newCachedThreadPool();

    private void parallelDispose() {
        logger.info(String.format("Disposing of kernel %s", this));
        disposer.execute(this::dispose);
    }

    @Override
    protected void finalize() throws Throwable {
        parallelDispose();
        super.finalize();
    }
}
