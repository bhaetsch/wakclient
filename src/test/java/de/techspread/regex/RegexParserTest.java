package de.techspread.regex;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import de.techspread.regex.RegexParser;

/** Tests for the regex parser. */
public class RegexParserTest {

	/**
	 * Testing the private constructor.
	 * @throws Exception Error accessing the private constructor
	 */
	@Test
	public final void privateConstructorTest() throws Exception {
	   Constructor<?> con = RegexParser.class.getDeclaredConstructors()[0];
	   con.setAccessible(true);
	   Assert.assertNotNull(con.newInstance());
	}
	
	/** Finding a single href. */
	@Test
	public final void findSingleHrefTest() {
		String input = "<a href=\"http://www.techspread.de\">techspread</a>";
		String regex = "href=\"(.*?)\"";
		ArrayList<String[]> matches = RegexParser.find(input, regex);
		String actual = matches.get(0)[0];
		Assert.assertEquals(actual, "http://www.techspread.de");
	}
	
	/** Finding multiple hrefs. */
	@Test
	public final void findMultipleHrefsTest() {
	    
		String input = "<a href=\"http://www.techspread.de\">techspread</a>" 
					+ "<a href=\"http://www.techspread.de\">techspread</a>" 
					+ "<a href=\"http://www.techspread.de\">techspread</a>" 
					+ "<a href=\"http://www.techspread.de\">techspread</a>" 
					+ "<a href=\"http://www.techspread.de\">techspread</a>";
		
		String regex = "href=\"(.*?)\"";
		
		ArrayList<String[]> matches = RegexParser.find(input, regex);
		Assert.assertEquals(matches.size(), 5);
		for (String[] match : matches) {
		    Assert.assertEquals(match.length, 1);
		    Assert.assertEquals(match[0], "http://www.techspread.de");
		}
		
	}
	
}
