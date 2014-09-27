package de.techspread.crypt;

/** Base64 Encryption and Decryption. */
public final class Base64 {

	/** Base64 Encryption from Apache Commons Codec. */
	private static org.apache.commons.codec.binary.Base64	base64	= new org.apache.commons.codec.binary.Base64();
	
	/** Private constructor (utility class). */
	private Base64() { }

	/**
	 * Encrypt a string with Base64.
	 * @param input String to encode
	 * @return Encrypted string
	 */
	public static String encode(final String input) {
		return new String(base64.encode(input.getBytes())).trim();
	}

	/**
	 * Decrypt a Base64 encoded String.
	 * @param input Base64 encrypted string
	 * @return Decrypted string
	 */
	public static String decode(final String input) {
		return new String(base64.decode(input.getBytes())).trim();
	}
}
