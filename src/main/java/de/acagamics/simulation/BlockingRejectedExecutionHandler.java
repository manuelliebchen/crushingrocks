package de.acagamics.simulation;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A custom implementation of a {@link #java.util.concurrent.RejectedExecutionHandler}
 * to block task submitting threads from adding more tasks.
 * 
 * @author Tim Benedict Jagla {@literal <tim@acagamics.de>}
 */
public final class BlockingRejectedExecutionHandler implements RejectedExecutionHandler {

	private static final Logger LOG = LogManager.getLogger(BlockingRejectedExecutionHandler.class.getName());
	
	/**
	 * Blocks the rejected thread until another position in the queue is empty.
	 * @see java.util.concurrent.RejectedExecutionHandler#rejectedExecution(Runnable, ThreadPoolExecutor)
	 */
	@Override
	public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
		try {
			LOG.debug(runnable.toString() + "'s execution will be blocked.");
			executor.getQueue().put(runnable); // call to put() blocks the calling thread
		} catch (InterruptedException ignore) {
			LOG.error("InterruptedException of BlockingQueue.put(Runnable) is not relevant.");
		}
	}

}
