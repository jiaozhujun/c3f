package com.youcan.core.network;

import java.io.*;
import java.text.*;

import com.youcan.core.network.NetworkInfo;

public class LinuxNetworkInfo extends NetworkInfo {
	public static final String IPCONFIG_COMMAND = "ifconfig";

	@Override
	public String parseMacAddress() throws ParseException {
		return parseMacAddress(getLocalHost());
	}

	@Override
	public String parseMacAddress(String ipAddr) throws ParseException {
		String ipConfigResponse = null;
		try {
			ipConfigResponse = runConsoleCommand(IPCONFIG_COMMAND);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParseException(e.getMessage(), 0);
		}

		java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(
				ipConfigResponse, "\n");
		String lastMacAddress = null;

		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim();
			boolean containsLocalHost = line.indexOf(ipAddr) >= 0;

			// see if line contains IP address
			if (containsLocalHost && lastMacAddress != null) {
				return lastMacAddress;
			}

			// see if line contains MAC address
			int macAddressPosition = line.indexOf("HWaddr");
			if (macAddressPosition <= 0) {
				continue;
			}

			String macAddressCandidate = line.substring(macAddressPosition + 6)
					.trim();
			if (isMacAddress(macAddressCandidate)) {
				lastMacAddress = macAddressCandidate;
				continue;
			}
		}

		throw new ParseException("cannot get MAC address for "
				+ ipAddr + " from [" + ipConfigResponse + "]", 0);
	}

	@Override
	public String parseDomain(String hostname) throws ParseException {
		return "";
	}

	private final static boolean isMacAddress(String macAddressCandidate) {
		if (macAddressCandidate.length() != 17)
			return false;
		return true;
	}
}