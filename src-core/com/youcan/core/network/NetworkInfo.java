package com.youcan.core.network;

import java.net.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

import com.youcan.core.network.LinuxNetworkInfo;
import com.youcan.core.network.NetworkInfo;
import com.youcan.core.network.WindowsNetworkInfo;
import com.youcan.core.CallBack;
import com.youcan.core.CliStream;

public abstract class NetworkInfo {
	private static final String LOCALHOST = "localhost";
	public static final String NSLOOKUP_CMD = "nslookup";

	public abstract String parseMacAddress() throws ParseException;
	public abstract String parseMacAddress(String ipAddr) throws ParseException;

	/** Not too sure of the ramifications here, but it just doesn't seem right */
	public String parseDomain() throws ParseException {
		return parseDomain(LOCALHOST);
	}

	public final static String getMacAddress() throws IOException {
		try {
			NetworkInfo info = getNetworkInfo();
			String mac = info.parseMacAddress();
			return mac;
		} catch (ParseException ex) {
			//ex.printStackTrace();
			throw new IOException(ex.getMessage());
		}
	}

	/** Universal entry for retrieving MAC Address */
	public final static String getMacAddress(String ipAddr) throws IOException {
		try {
			NetworkInfo info = getNetworkInfo();
			String mac = info.parseMacAddress(ipAddr);
			return mac;
		} catch (ParseException ex) {
			//ex.printStackTrace();
			throw new IOException(ex.getMessage());
		}
	}

	/** Universal entry for retrieving domain info */
	public final static String getNetworkDomain() throws IOException {
		try {
			NetworkInfo info = getNetworkInfo();
			String domain = info.parseDomain();
			return domain;
		} catch (ParseException ex) {
			ex.printStackTrace();
			throw new IOException(ex.getMessage());
		}
	}

	protected String parseDomain(String hostname) throws ParseException {
		// get the address of the host we are looking for - verification
		java.net.InetAddress addy = null;
		try {
			addy = java.net.InetAddress.getByName(hostname);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new ParseException(e.getMessage(), 0);
		}

		// back out to the hostname - just validating
		hostname = addy.getCanonicalHostName();
		String nslookupCommand = NSLOOKUP_CMD + " " + hostname;

		// run the lookup command
		String nslookupResponse = null;
		try {
			nslookupResponse = runConsoleCommand(nslookupCommand);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParseException(e.getMessage(), 0);
		}

		StringTokenizer tokeit = new StringTokenizer(nslookupResponse, "\n",
				false);
		while (tokeit.hasMoreTokens()) {
			String line = tokeit.nextToken();
			if (line.startsWith("Name:")) {
				line = line.substring(line.indexOf(":") + 1);
				line = line.trim();
				if (isDomain(line, hostname)) {
					line = line.substring(hostname.length() + 1);
					return line;
				}
			}
		}

		return "n.a.";
	}

	private static boolean isDomain(String domainCandidate, String hostname) {
		Pattern domainPattern = Pattern
				.compile("[\\w-]+\\.[\\w-]+\\.[\\w-]+\\.[\\w-]+");
		Matcher m = domainPattern.matcher(domainCandidate);
		return m.matches() && domainCandidate.startsWith(hostname);
	}

	@SuppressWarnings("static-method")
	protected String runConsoleCommand(String command) throws IOException {
		Process p = Runtime.getRuntime().exec(command);
		CliStream cliStream = new CliStream(p.getInputStream());
		if (System.getProperty("os.name").startsWith("Windows")) {
			cliStream.setCharset(System.getProperty("sun.jnu.encoding"));//"file.encoding"
		}
		cliStream.setSpliter(new StringBuffer("\n"));
		CallBack callBack = new CallBack(10000);
		cliStream.setCallBack(callBack);
		new Thread(cliStream).start();
		callBack.waitBack();
		return callBack.getContent();
	}

	/** Sort of like a factory... */
	private static NetworkInfo getNetworkInfo() throws IOException {
		String os = System.getProperty("os.name");

		if (os.startsWith("Windows")) {
			return new WindowsNetworkInfo();
		} else if (os.startsWith("Linux")) {
			return new LinuxNetworkInfo();
		} else {
			throw new IOException("unknown operating system: " + os);
		}
	}

	@SuppressWarnings("static-method")
	protected String getLocalHost() throws ParseException {
		try {
			return java.net.InetAddress.getLocalHost().getHostAddress();
		} catch (java.net.UnknownHostException e) {
			e.printStackTrace();
			throw new ParseException(e.getMessage(), 0);
		}
	}
}