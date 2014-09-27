package de.techspread.crypt;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** MD5 Hashing. */
public final class MD5 {
	
	/** Private constructor (utility class). */
	private MD5() { }

	/**
	 * Creates an MD5 hash from a String.
	 * @param input String which should be hashed
	 * @return MD5 Hash
	 * @throws NoSuchAlgorithmException Algorithm not found
	 */
	public static String hash(final String input) throws NoSuchAlgorithmException {
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(input.getBytes(), 0, input.length());		
		final int radix = 16;
		String hash = new BigInteger(1, m.digest()).toString(radix);		
		final int tooShortMd5 = 31;
		if (hash.length() == tooShortMd5) {
			hash = "0" + hash;
		}
		return hash;
	}
}