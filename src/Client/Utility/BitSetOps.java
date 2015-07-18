package Client.Utility;

import java.util.BitSet;

/**
 * Static helper functions to make dynamic bitset operations a little bit more
 * convenient. The requirements where to low to create a custom dynamic bitset
 * class and to high to do it from within the
 * {@link #Client.Utility.StaticCombinationIterator<T>}, thats why the
 * {@link #BitSetOps()} came into existence.
 * 
 * This helper functions assume the least significant bit at index 0 instead of
 * the usual other way round representation of a bitset.
 * 
 * @author Tim Benedict Jagla {@literal <tim@acagamics.de>}
 */
public class BitSetOps {

    public static final BitSet ONE;    
    static {
    	ONE = new BitSet(1);
        ONE.set(0);
    }
    
    /**
     * Compare two bitsets with each other.
     * @param lhs Reference bitset.
     * @param rhs Bitset to compare the reference to.
     * @return a number as an indicator for the result of the comparison
     * <ul>
     * <li>-1 lhs smaller rhs</li>
     * <li>+1 lhs larger rhs</li>
     * <li>&plusmn;0 lhs equal rhs</li>
     * </ul>
     */
	public static int compare(BitSet lhs, BitSet rhs) {
	    if (lhs.equals(rhs)) return 0;
	    BitSet xor = (BitSet)lhs.clone();
	    xor.xor(rhs);
	    int difference = xor.length() - 1; // retrieves most significant (last) difference
	    return rhs.get(difference) ? -1 : 1;
	}
	
	/**
	 * Returns a bitset as a binary string in the usual order (from most significant to the least significant bit).
	 * @param set Bitset to return as a binary string.
	 * @return Binary string in the usual order (from most significant to the least significant bit).
	 */
	public static String toBinaryString(BitSet set) {
		StringBuilder sb = new StringBuilder();
		for (int i = set.length() - 1; i >= 0 ; --i) {
			int value = set.get(i) ? 1 : 0;
			sb.append(value);
		}
		return sb.toString();
	}

	/**
	 * Increments the value of a bitset by 1, comparable to the ++ operator in front of an integer.
	 * @param set The bitset to increment by 1.
	 * @return The bitset increment by 1.
	 */
	public static BitSet increment(BitSet set) {
		int index = 0;
		boolean carry = true;
	    while (carry) {
	    	carry = set.get(index);
	    	set.flip(index);
	    	++index;
	    }
	    return set;
	}
	
	/**
	 * Check two bitsets for equality.
     * @param lhs Reference bitset.
     * @param rhs Bitset to compare the reference to.
	 * @return true if lhs equals rhs.
	 */
	public static boolean eq(BitSet lhs, BitSet rhs) {
		return lhs.equals(rhs);
	}
	
	/**
	 * Check if the reference bitset is greater than the other bitset.
     * @param lhs Reference bitset.
     * @param rhs Bitset to compare the reference to.
	 * @return true if lhs greater than rhs.
	 */
	public static boolean gr(BitSet lhs, BitSet rhs) {
		final int comparison = compare(lhs, rhs);
		return (comparison == 1);
	}
	
	/**
	 * Check if the reference bitset is lesser than the other bitset.
     * @param lhs Reference bitset.
     * @param rhs Bitset to compare the reference to.
	 * @return true if lhs less than rhs.
	 */
	public static boolean le(BitSet lhs, BitSet rhs) {
		final int comparison = compare(lhs, rhs);
		return (comparison == -1);
	}
	
	/**
	 * Check if the reference bitset is greater or equal to the other bitset.
     * @param lhs Reference bitset.
     * @param rhs Bitset to compare the reference to.
	 * @return true if lhs greater or equal rhs.
	 */
	public static boolean greq(BitSet lhs, BitSet rhs) {
		final int comparison = compare(lhs, rhs);
		return (comparison == 1) || (comparison == 0);
	}
	
	/**
	 * Check if the reference bitset is less or equal to the other bitset.
     * @param lhs Reference bitset.
     * @param rhs Bitset to compare the reference to.
	 * @return true if lhs less or equal rhs.
	 */
	public static boolean leeq(BitSet lhs, BitSet rhs) {
		final int comparison = compare(lhs, rhs);
		return (comparison == -1) || (comparison == 0);
	}
	
}
