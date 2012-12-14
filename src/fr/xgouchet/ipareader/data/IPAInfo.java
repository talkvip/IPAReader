package fr.xgouchet.ipareader.data;

import java.util.LinkedList;
import java.util.List;

public class IPAInfo {

	public IPAInfo() {
		mDevices = new LinkedList<String>();
	}

	public String getProvisioning() {
		return mProvisioning;
	}

	public void setProvisioning(final String provisioning) {
		mProvisioning = provisioning;
	}

	public String getAppId() {
		return mAppId;
	}

	public void setAppId(final String appId) {
		mAppId = appId;
	}

	public long getCreation() {
		return mCreation;
	}

	public void setCreation(final long creation) {
		mCreation = creation;
	}

	public long getExpiration() {
		return mExpiration;
	}

	public void setExpiration(final long expiration) {
		mExpiration = expiration;
	}

	public String getDevices() {
		final StringBuilder builder = new StringBuilder();
		if (mAllDevices) {
			builder.append("* Provisions Any Device *");
		} else {
			for (String device : mDevices) {
				builder.append(device);
				builder.append("\n");
			}
		}
		return builder.toString();
	}

	public void addDevice(final String device) {
		mDevices.add(device);
	}

	public void setAllDevices(boolean allDevices) {
		mAllDevices = allDevices;
	}

	public boolean isAllDevices() {
		return mAllDevices;
	}

	private String mProvisioning, mAppId;
	private final List<String> mDevices;
	private long mCreation, mExpiration;
	private boolean mAllDevices;
}
