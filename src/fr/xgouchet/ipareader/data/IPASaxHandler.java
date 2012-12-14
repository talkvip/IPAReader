package fr.xgouchet.ipareader.data;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class IPASaxHandler extends DefaultHandler {

	public static final int STATE_WAIT = 0;
	public static final int STATE_READ_KEY = 1;
	public static final int STATE_READ_STRING = 2;
	public static final int STATE_READ_DATE = 3;

	public IPASaxHandler() {
		mIPAInfo = new IPAInfo();
		mState = STATE_WAIT;
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(final String uri, final String localName,
			final String qName, final Attributes attributes)
			throws SAXException {
		if ("key".equals(qName)) {
			mState = STATE_READ_KEY;
			mKey = "";
		} else if ("string".equals(qName)) {
			mState = STATE_READ_STRING;
		} else if ("date".equals(qName)) {
			mState = STATE_READ_DATE;
		} else if ("true".equals(qName)) {
			setIpaBool(true);
		} else if ("true".equals(qName)) {
			setIpaBool(false);
		}
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	public void characters(final char[] chars, final int start, final int length)
			throws SAXException {

		final String text = new String(chars, start, length);
		switch (mState) {
		case STATE_READ_KEY:
			mKey += text;
			break;
		case STATE_READ_STRING:
			setIpaInfo(text);
			break;
		case STATE_READ_DATE:
			setIpaDate(ISO8601.toTimeMilliseconds(text));
		case STATE_WAIT:
		default:
			// System.out.println("Ignoring text : ");
			// System.out.println(text);
			break;
		}
	}

	private void setIpaInfo(final String info) {
		switch (mKey) {
		case "Name":
			mIPAInfo.setProvisioning(info);
			break;
		case "application-identifier":
			mIPAInfo.setAppId(info);
			break;
		case "ProvisionedDevices":
			mIPAInfo.addDevice(info);
			break;
		default:
			// System.out.println(mKey + " => " + info);
			break;
		}
	}

	private void setIpaDate(final long date) {
		switch (mKey) {
		case "CreationDate":
			mIPAInfo.setCreation(date);
			break;
		case "ExpirationDate":
			mIPAInfo.setExpiration(date);
			break;
		default:
			// System.out.println(mKey + " => " + date);
			break;
		}
	}

	private void setIpaBool(final boolean bool) {
		if ("ProvisionsAllDevices".equals(mKey)) {
			mIPAInfo.setAllDevices(bool);
		}
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void endElement(final String uri, final String localName,
			final String qName) throws SAXException {
		mState = STATE_WAIT;
	}

	public IPAInfo getIPAInfo() {
		return mIPAInfo;
	}

	private final IPAInfo mIPAInfo;
	private int mState;
	private String mKey;
}
