package fr.xgouchet.ipareader.data;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public final class IPAUtils {

	/**
	 * @param file
	 * @throws IOException
	 * @throws ZipException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static IPAInfo readIPA(final File ipa) throws ZipException,
			IOException, ParserConfigurationException, SAXException {

		sInfo = null;
		final ZipFile zip = new ZipFile(ipa);
		try {

			final Enumeration<? extends ZipEntry> entries = zip.entries();
			ZipEntry entry;

			while (entries.hasMoreElements()) {
				entry = entries.nextElement();
				if (entry.getName().contains("embedded.mobileprovision")) {
					readEmbeddedProfile(entry, zip);
					parseProfile();
					break;
				}
			}
		} finally {
			zip.close();
		}

		return sInfo;
	}

	private static void readEmbeddedProfile(final ZipEntry entry,
			final ZipFile zip) throws IOException {
		final InputStream input = zip.getInputStream(entry);
		final Reader reader = new InputStreamReader(input);
		final StringBuilder builder = new StringBuilder();
		final char[] buffer = new char[1024];
		int length;

		do {
			length = reader.read(buffer, 0, buffer.length);
			if (length > 0) {
				builder.append(buffer, 0, length);
			}
		} while (length > 0);

		final String profile = builder.toString();
		int start, end;
		start = profile.indexOf("<plist");
		end = profile.indexOf("</plist>");

		sProfile = profile.substring(start, end + 8);
		// System.out.println(sProfile);
		input.close();
		reader.close();
	}

	private static void parseProfile() throws ParserConfigurationException,
			SAXException, IOException {
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		final SAXParser parser = factory.newSAXParser();
		final InputStream input = new ByteArrayInputStream(sProfile.getBytes());

		final IPASaxHandler handler = new IPASaxHandler();
		parser.parse(input, handler);

		sInfo = handler.getIPAInfo();
	}

	private IPAUtils() {
	}

	private static String sProfile;
	private static IPAInfo sInfo;
}
