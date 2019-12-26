package de.acagamics.simulation;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;

/**
 * This class deals with the logic behind the parallelization of runnable processes and conducts the calculations.
 * It does in no way care about order, command line arguments, statistics or other process related stuff. 
 * @author Tim Benedict Jagla {@literal <tim@acagamics.de>}
 */
public final class SimulationProcessRunner implements Runnable {
	
	private static final Logger LOG = LogManager.getLogger(SimulationProcessRunner.class);
	/** Keep threads alive for {@value #THREAD_KEEP_ALIVE_TIME} milliseconds. */
	private static final long THREAD_KEEP_ALIVE_TIME = 1000L;
	/** Timeout of {@value #PROCESS_EXECUTOR_TIMEOUT} minutes, which equals two days of calculation time, until a forced termination. */
	private static final long PROCESS_EXECUTOR_TIMEOUT = 2880L;
	/** Timeout of {@value #MONITOR_EXECUTOR_TIMEOUT} minutes until a forced termination. */
	private static final long MONITOR_EXECUTOR_TIMEOUT = 1L;
	
	/** Monitor to keep track of the progress. */
	private final ThreadPoolExecutorMonitor monitor;
	/** Service with a single thread to run the monitor. */
	private final ExecutorService executorMonitor;
	/** Service with a fixed thread pool size, see {@link #SimulationProcessRunner}, to execute processes. */
	private final ExecutorService executor;
	/** Factory of a series of runnable processes to be executed by this runner. */
	private final SimulationProcessBuilder builder;
	
	/**
	 * Create a custom thread pool executor with fixed pool size, a queue and a blocking rejection handler.
	 * @param nThreads number of threads in the pool
	 * @param nQueue size of the blocking queue
	 * @return a custom thread pool executor
	 */
	private static ThreadPoolExecutor createThreadPoolExecutor(final int nThreads, final int nQueue) {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(nThreads, nThreads,
	    		THREAD_KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS,
	    		new ArrayBlockingQueue<Runnable>(nQueue, true),
	    		new BlockingRejectedExecutionHandler());
		return executor;
	}
	
	/**
	 * Constructor
	 * @param builder creates a series of runnable processes to be executed by this runner.
	 * @param nParallel number of processes to be run in parallel.
	 */
	public SimulationProcessRunner(final SimulationProcessBuilder builder, final int nParallel) {
		final ThreadPoolExecutor threadPoolExecutor = createThreadPoolExecutor(nParallel, nParallel);
		this.executorMonitor = Executors.newSingleThreadExecutor();
		this.executor = threadPoolExecutor;
		this.monitor = new ThreadPoolExecutorMonitor(threadPoolExecutor);
		this.builder = builder;
	}
	
	/**
	 * Constructor that defaults to the number of available processors for nParallel.
	 * @param builder creates a series of runnable processes to be executed by this runner.
	 */
	public SimulationProcessRunner(final SimulationProcessBuilder builder) {
		this(builder, Math.max(1, Runtime.getRuntime().availableProcessors() - 1));
	}
	
	/** 
	 * Basically the main loop of the process runner that will be run until finished. 
	 */
	@Override
	public void run() {
		// run
		this.executorMonitor.execute(monitor);
		while (builder.hasNext()) {
			executor.execute(builder.next());
		}
		// shutdown
		safeExecutorShutdown(this.executor, PROCESS_EXECUTOR_TIMEOUT);
		monitor.shutdown();
		safeExecutorShutdown(this.executorMonitor, MONITOR_EXECUTOR_TIMEOUT);
	}
	
	/**
	 * Safely shuts down an executor service with a timeout (in minutes) before termination.
	 * @param executor executor service to be shut down.
	 * @param timeout the timeout before forced termination of the executor in minutes.
	 */
	private void safeExecutorShutdown(ExecutorService executor, final long timeout) {
		executor.shutdown();
		try {
			executor.awaitTermination(timeout, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
		    LOG.error("Exception!", e);
		}
	}
	
}
