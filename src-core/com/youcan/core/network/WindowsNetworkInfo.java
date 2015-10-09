package com.youcan.core.network;

import java.io.*;
import java.text.*;
import java.util.regex.*;

import com.youcan.core.network.NetworkInfo;

public class WindowsNetworkInfo extends NetworkInfo {
	public static final String IPCONFIG_COMMAND = "ipconfig /all";

	@Override
	public String parseMacAddress() throws ParseException {
		return parseMacAddress(getLocalHost());
	}

	@Override
	public String parseMacAddress(String ipAddr) throws ParseException {
		// run command
		String ipConfigResponse = null;
		try {
			ipConfigResponse = runConsoleCommand(IPCONFIG_COMMAND);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParseException(e.getMessage(), 0);
		}

		// get localhost address
		String localHost = getLocalHost();
		String ipAddrVista = localHost.concat("(首选)");
		java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(
				ipConfigResponse, "\n");
		String lastMacAddress = null;
		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim();

			// see if line contains IP address, this means stop if we've already
			// seen a MAC address
			if (line.endsWith(ipAddr) || line.endsWith(ipAddrVista) && lastMacAddress != null) {
				return lastMacAddress;
			}

			// see if line might contain a MAC address
			int macAddressPosition = line.indexOf(":");
			if (macAddressPosition <= 0) {
				continue;
			}

			// trim the line and see if it matches the pattern
			String macAddressCandidate = line.substring(macAddressPosition + 1)
					.trim();
			if (isMacAddress(macAddressCandidate)) {
				lastMacAddress = macAddressCandidate;
				continue;
			}
		}

		throw new ParseException("cannot get MAC address for "
				+ ipAddr + " from [" + ipConfigResponse + "]", 0);
	}

	private static boolean isMacAddress(String macAddressCandidate) {
		Pattern macPattern = Pattern
				.compile("[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}");
		Matcher m = macPattern.matcher(macAddressCandidate);
		return m.matches();
	}
}
