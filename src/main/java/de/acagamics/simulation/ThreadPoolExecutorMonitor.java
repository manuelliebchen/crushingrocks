package de.acagamics.simulation;

import java.util.concurrent.ThreadPoolExecutor;
import java.lang.Runnable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class monitors the intrinsics of a given {@link java.util.concurrent.ThreadPoolExecutor}
 * and logs them.
 * 
 * @author Tim Benedict Jagla {@literal <tim@acagamics.de>}
 */
public final class ThreadPoolExecutorMonitor implements Runnable {
	
	private static final Logger LOG = LogManager.getLogger(ThreadPoolExecutorMonitor.class.getName());
	/** The default log message interval of {@value #DEFAULT_INTERVAL} milliseconds, or one second. */
	private static final long DEFAULT_INTERVAL = 1000L;
	
	/** The {@link #java.util.concurrent.ThreadPoolExecutor} to be monitored. */
	private final ThreadPoolExecutor executor;
	/** The interval between two log outputs of the {@link #ThreadPoolExecutorMonitor}. */
	private final long interval;
	/** The breaking condition for the main loop of the {@link #ThreadPoolExecutorMonitor}. */
	private boolean active = true;

	/**
	 * Constructor
	 * @param executor the executor service to monitor.
	 * @param interval the monitor-message interval in milliseconds.
	 */
	public ThreadPoolExecutorMonitor(final ThreadPoolExecutor executor, final long interval) {
		this.executor = executor;
		this.interval = interval;
	}
	
	/**
	 * Constructor with a default monitor-message interval of once per second.
	 * @param executor the executor service to monitor.
	 */
	public ThreadPoolExecutorMonitor(final ThreadPoolExecutor executor) {
		this(executor, DEFAULT_INTERVAL);
	}
	
	/**
	 * Stops monitoring by exiting the main loop after a cooldown period.
	 * @param cooldown the cooldown period before shutdown in milliseconds.
	 */
    public void shutdown(final long cooldown) {
		try {
		    Thread.sleep(cooldown);
		} catch (InterruptedException e) {
		    LOG.error("Exception!", e);
		}
        this.active = false;
        LOG.info("Farewell!");
    }
    
	/**
	 * Stops monitoring by exiting the main loop after a default cooldown period
	 * of length {@link #interval}.
	 */
    public void shutdown() {
    	this.shutdown(this.interval);
    }
    
    /**
     * Runs the monitor, generating log output about the current status of the
     * thread pool executor.
     * @see java.lang.Runnable#run()
     */
	@Override
	public void run() {
		while (active) {
			LOG.info(String.format("Pool: [%1$02d/%2$02d] - Queue: [%3$02d/%4$02d] - Complete/Submit Task(s): [%5$02d/%6$02d]",
					this.executor.getPoolSize(),
			        this.executor.getCorePoolSize(),
			        this.executor.getQueue().size(),
			        this.executor.getQueue().size() + this.executor.getQueue().remainingCapacity(),
			        this.executor.getCompletedTaskCount(),
			        this.executor.getTaskCount()));
			try {
			    Thread.sleep(interval);
			} catch (InterruptedException e) {
				LOG.error("Exception!", e);
			}
		}
	}
}
