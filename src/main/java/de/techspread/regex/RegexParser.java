package de.techspread.regex;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Utility class for regular expression parsing. */
public final class RegexParser {
	
	/** Private constructor (utility class). */
	private RegexParser() { }
	
	/**
	 * Parse a text with regular expressions.
	 * @param input Text
	 * @param regex Regular Expression
	 * @return Matches
	 */
	public static ArrayList<String[]> find(final String input, final String regex) {
		ArrayList<String[]> matches = new ArrayList<String[]>();
		Matcher matcher = Pattern.compile(regex, Pattern.DOTALL).matcher(input);
		while (matcher.find()) {
			int count = matcher.groupCount();
			String[] temp = new String[count];
			for (int i = 0; i < count; i++) {
				temp[i] = matcher.group(i + 1);
			}
			matches.add(temp);
		}
		return matches;
	}
}