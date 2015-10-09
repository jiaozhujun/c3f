package com.youcan.core.system;

import java.io.IOException;

import com.youcan.core.CliStream;
import com.youcan.core.g;

public class TomcatMan {
	private String catalinaHome = null;

	public TomcatMan() {
		catalinaHome = System.getProperty("catalina.home");
		if ("".equals(catalinaHome)) {
			catalinaHome = System.getProperty("user.dir");
		} else {
			catalinaHome += "/bin";
		}
	}

	public int shutdown() {
		Process process = null;
		int exitValue = 0;
		try {
			process = Runtime.getRuntime().exec(
					catalinaHome
							+ (g.os == g.OS_WINDOWS ? "/shutdown.bat"
									: "/shutdown.sh"));
			new Thread(new CliStream(process.getInputStream())).start();
			new Thread(new CliStream(process.getErrorStream())).start();
			exitValue = process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
			exitValue = -1;
		} catch (InterruptedException e) {
			e.printStackTrace();
			exitValue = -1;
		}
		return exitValue;
	}
}
