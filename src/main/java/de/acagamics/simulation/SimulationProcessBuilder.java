package de.acagamics.simulation;

import java.io.File;
import java.lang.ProcessBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.acagamics.simulation.context.GameContext;

/**
 * Creates runnable processes from a collection of simulation contexts.
 * 
 * @author Tim Benedict Jagla {@literal <tim@acagamics.de>}
 */
public final class SimulationProcessBuilder implements Iterator<Runnable> {

	private static final String PATH_JAVA_BINARY = "java";
	
	private String pathOutput;
	private String filepathExecutableJar;
	
	private List<String> argsProcessBuilder;
	private Iterator<GameContext> contextIterator;
	
	/**
	 * 
	 * @param filepathExecutableJar filepath to the executable jar
	 * @param contextIterator iterator over {@link GameContext}.
	 */
	public SimulationProcessBuilder(String filepathExecutableJar, Iterator<GameContext> contextIterator) {
		setFilepathExecutableJar(filepathExecutableJar);
		this.contextIterator = contextIterator;
	}

	/**
	 * Generate command line arguments as a list of strings from a
	 * {@link GameContext}.
	 * @param context the context to generate process arguments from.
	 * @return the command line arguments in list of strings
	 */
	public List<String> nextProcessBuilderArgs(GameContext context) {
		argsProcessBuilder = new ArrayList<String>();
		argsProcessBuilder.add(PATH_JAVA_BINARY);
		argsProcessBuilder.add("-jar");
		argsProcessBuilder.add(filepathExecutableJar);
		argsProcessBuilder.addAll(context.arguments());
		return argsProcessBuilder;
	}
	
	/**
	 * Setter for the filepath of the executable jar. It also extracts a output
	 * path for files relative to the executable.
	 * @param filepathExecutableJar filepath to the executable jar
	 */
	public void setFilepathExecutableJar(String filepathExecutableJar) {
		this.filepathExecutableJar = filepathExecutableJar;
		this.pathOutput = new File(new File(filepathExecutableJar).getParent(), "out").toString();
	}

	/**
	 * Returns whether the iterator has a next {@link GameContext}.
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return this.contextIterator.hasNext();
	}

	/**
	 * Returns the next {@link GameContext}.
	 * @see java.util.Iterator#next()
	 */
	@Override
	public Runnable next() {
		GameContext next = this.contextIterator.next();
		List<String> argsProcessBuilder = nextProcessBuilderArgs(next);
		ProcessBuilder builder = new ProcessBuilder(argsProcessBuilder).inheritIO();
		
		// redirect process output stream
		File output = new File(this.pathOutput, "stdout-" + next.toFilename() + ".log");
		output.getParentFile().mkdirs();
		builder.redirectOutput(output);
		
		return new RunnableProcess(builder);
	}

}
