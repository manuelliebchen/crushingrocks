package de.acagamics.client.utility;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Solution for the all combinations of length k problem with a lazy iterator.
 * See various solutions to the problem on <a href="http://rosettacode.org/wiki/Combinations">rosettacode</a>.
 * 
 * @param <T> type of the values to combine into combinations.
 * 
 * @author Tim Benedict Jagla {@literal <tim@acagamics.de>}
 */
public class StaticCombinationIterator<T> implements Iterator<List<T>> {
	
	private static final Logger LOG = LogManager.getLogger(StaticCombinationIterator.class.getName());
	
	/** The list to choose combinations from. */
	private final List<T> list;
	/** Variable n as in "n over k". size of the list to choose combinations from. */
	private final int n;
	/** Variable k as in "n over k". size of each combination chosen from the list. */
	private final int k;
	/** integer value of the maximum possible bit mask */
	private final BitSet max;
	
	/** saves the state of the iterator (current position in search space) */
	private BitSet state;
	
	/** total number of possible combinations with the given n and k (binomial coefficient) */
	private final BigInteger binomial;
	
	/**
	 * Constructor
	 * @param list The list to choose combinations from.
	 * @param combinationSize The desired, fixed size of chosen combinations.
	 */
	public StaticCombinationIterator(final List<T> list, final int combinationSize) {
		this.list = list;
		this.n = list.size();
		this.k = combinationSize;
		this.binomial = binomial(this.n, this.k);
		try {
			this.binomial.intValueExact();
		} catch (ArithmeticException e) {
			LOG.warn("StaticCombinationIterator will return more elements than an int can index. Use a lazy interface and especially, rethink your algorithm.");
		}
		// max is basically equal to (1 << this.n)
		this.max = new BitSet(this.n);
		this.max.set(this.n);
		this.state = new BitSet(this.n);
	}
	
	/**
	 * Checks whether another possible combination exists. This method has side
	 * effects on #state, narrowing the search space for the next
	 * {@link #hasNext()} or the next {@link #next()} call as a minor method
	 * for optimization.
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		for (BitSet bits = state; BitSetOps.le(bits, max); BitSetOps.increment(bits)) {
			if (bits.cardinality() == this.k) {
				this.state = bits;
				return true;
			}
		}
		this.state = this.max;
		return false;
	}

	/**
	 * Returns the next possible combination.
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public List<T> next() throws NoSuchElementException {
		for (BitSet bits = state; BitSetOps.le(bits, this.max); BitSetOps.increment(bits)) {
			if (bits.cardinality() == this.k) {
				List<T> result = bitsToList(bits);
				this.state = BitSetOps.increment(bits);
				return result;
			}
		}
		throw new NoSuchElementException("No more combinations of size " + this.k + " left.");
	}
	
	/**
	 * Create the corresponding list for the bit mask.
	 * @param set bit mask to mask elements of the list.
	 * @return the corresponding list for the bit mask.
	 */
    public List<T> bitsToList(BitSet set) {
    	List<T> result = new ArrayList<T>(this.k);
    	int i = set.nextSetBit(0);
    	while (i != -1) {
    		result.add(list.get(i));
    		i = set.nextSetBit(i+1);
    	}
        return result;
    }
	
	/**
	 * Iterative solution to the {@link #n} over {@link #k} binomial coefficient.
	 * {@link http://stackoverflow.com/questions/2201113/combinatoric-n-choose-r-in-java-math#2929897}
	 * @param n From a set of size {@link #n}...
	 * @param k ...choose sets of size {@link #k}.
	 * @return number of possible solutions for {@link #n} over {@link #k}.
	 */
	static BigInteger binomial(final int n, final int k) {
	    BigInteger result = BigInteger.ONE;
	    for (int step = 0; step < k; ++step) {
	        result = result
	        		.multiply(BigInteger.valueOf(n - step))
	        		.divide(BigInteger.valueOf(step + 1));
	    }
	    return result;
	}
	
	/**
	 * Returns the total number of possible combinations given by the
	 * parameters in {@link #StaticCombinationIterator(List, int)}.
	 * @return The binomial of {@link #n} over {@link #k}, indicator for the
	 * total number of possible combinations.
	 */
	public BigInteger binomial() {
		return binomial;
	}
	
}
