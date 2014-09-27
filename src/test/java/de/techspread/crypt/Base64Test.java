package de.techspread.crypt;

import java.lang.reflect.Constructor;

import org.junit.Assert;
import org.junit.Test;

import de.techspread.crypt.Base64;

/** Tests for the Base64 utility class. */
public class Base64Test {
	
	/**
	 * Testing the private constructor.
	 * @throws Exception Error accessing the private constructor
	 */
	@Test
	public final void privateConstructorTest() throws Exception {
		Constructor<?> con = Base64.class.getDeclaredConstructors()[0];
		con.setAccessible(true);
		Assert.assertNotNull(con.newInstance());
	}
	
	/** Testing the Base64 encryption. */
	@Test
	public final void encodeTest() {
		String encoded = Base64.encode("test");
		Assert.assertEquals(encoded, "dGVzdA==");
	}
	
	/** Testing the Base64 decryption. */
	@Test
	public final void decodeTest() {
		String decoded = Base64.decode("dGVzdA==");
		Assert.assertEquals(decoded, "test");
	}
	
}
