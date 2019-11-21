package de.acagamics.client.simulation;

import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * A java process embedded into a class conforming to the {@link #java.lang.Runnable}
 * interface.
 * 
 * @author Tim Benedict Jagla {@literal <tim@acagamics.de>}
 */
public final class RunnableProcess implements Runnable {
	
	private static final Logger LOG = Logger.getLogger(RunnableProcess.class);
	
	/** The process builder with the given run configuration. */
	private final ProcessBuilder builder;
	/** The process about to be created by this {@link #RunnableProcess}. */
	private Process process;
	
	/**
	 * Constructor
	 * @param builder the process builder with the given run configuration
	 */
	public RunnableProcess(ProcessBuilder builder) {
		this.builder = builder;
	}
	
	/**
	 * Basically the main loop of the runnable process that will be run until finished.
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// initialization
		LOG.info("[" + Thread.currentThread().getName() + "] Process Start [" + String.join(" ", builder.command()) + "]");
		try {
			process = builder.start();
		} catch (IOException e) {
			LOG.error("Unable to create the process.", e);
		}
		// let the process do the work
		int rc = -1;
		try {
			rc = process.waitFor();
		} catch (InterruptedException e) {
		    LOG.error("Exception!", e);
			process.destroy();
		}
		LOG.debug("[" + Thread.currentThread().getName() + "] Process Termination [Exit Code: " + rc + "]");
	}

}
