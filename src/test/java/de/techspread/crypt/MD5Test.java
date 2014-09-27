package de.techspread.crypt;

import java.lang.reflect.Constructor;
import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Test;

import de.techspread.crypt.MD5;

/** Tests for the MD5 utility class. */
public class MD5Test {
	
	/**
	 * Testing the private constructor.
	 * @throws Exception Error accessing the private constructor
	 */
	@Test
	public final void privateConstructorTest() throws Exception {
		Constructor<?> con = MD5.class.getDeclaredConstructors()[0];
		con.setAccessible(true);
		Assert.assertNotNull(con.newInstance());
	}

	/** Testing an MD5 hash which gets 31 chars long. */
	@Test
	public final void shortHashTest() {
		String hash;
		try {
			hash = MD5.hash("test");
			Assert.assertEquals(hash, "098f6bcd4621d373cade4e832627b4f6");
		} catch (NoSuchAlgorithmException e) {
			Assert.fail(e.getMessage());
		}	
	}
	
	/** Testing MD5 hashing. */
	@Test
	public final void hashTest() {
		String hash;
		try {
			hash = MD5.hash("test1");
			Assert.assertEquals(hash, "5a105e8b9d40e1329780d62ea2265d8a");
		} catch (NoSuchAlgorithmException e) {
			Assert.fail(e.getMessage());
		}	
	} 
	
}
