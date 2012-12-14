package fr.xgouchet.ipareader.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Helper class for handling ISO 8601 strings of the following format:
 * "2008-03-01T13:00:00+01:00". It also supports parsing the "Z" timezone.
 * 
 * Written by user wrygiel on StackOverflow
 * (http://stackoverflow.com/questions/2201925
 * /converting-iso8601-compliant-string-to-java-util-date#10621553)
 */
public final class ISO8601 {

	/** Transform Calendar to ISO 8601 string. */
	public static String fromCalendar(final Calendar calendar) {
		final Date date = calendar.getTime();
		final String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ",
				Locale.US).format(date);
		return formatted.substring(0, 22) + ":" + formatted.substring(22);
	}

	/** Get current date and time formatted as ISO 8601 string. */
	public static String now() {
		return fromCalendar(GregorianCalendar.getInstance());
	}

	/** Transform ISO 8601 string to Calendar. */
	public static Calendar toCalendar(final String iso8601string)
			throws ParseException {
		String str;
		str = iso8601string.replace("Z", "+00:00");
		if (str.length() > 23) {
			str = str.substring(0, 22) + str.substring(23);
		} else {
			throw new ParseException("Invalid length", 0);
		}

		final Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ",
				Locale.US).parse(str);
		final Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/** Transform ISO 8601 string to long timestamp */
	public static long toTimeMilliseconds(final String iso8601string) {
		long timestamp;
		Calendar cal;
		try {
			cal = toCalendar(iso8601string);
		} catch (ParseException e) {
			cal = null;
		}

		if (cal == null) {
			timestamp = 0;
		} else {
			timestamp = cal.getTimeInMillis();
		}

		return timestamp;
	}

	private ISO8601() {
	}
}
