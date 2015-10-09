package com.youcan.core.system;

import java.io.IOException;
import java.io.InputStream;

import com.youcan.core.CliStream;


public class MachineMan {
	public MachineMan() {
		//do nothing
	}

	public int shutdown() {
		return linuxInit(0);
	}

	public int reboot() {
		return linuxInit(6);
	}

	@SuppressWarnings("static-method")
	private int linuxInit(int runlevel) {
		Process process = null;
		int exitValue = 0;
		try {
			process = Runtime.getRuntime().exec("init " + runlevel);
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

	@SuppressWarnings("static-access")
	public static int doWaitFor(Process p) {
		int exitValue = -1; // returned to caller when p is finished
		try {
			InputStream in = p.getInputStream();
			InputStream err = p.getErrorStream();
			boolean finished = false; // Set to true when p is finished
			while (!finished) {
				try {
					while (in.available() > 0) {
						// Print the output of our system call
						System.out.println("in:");
						Character c = new Character((char) in.read());
						System.out.print(c);
					}
					while (err.available() > 0) {
						System.out.println("err:");
						// Print the output of our system call
						Character c = new Character((char) err.read());
						System.out.print(c);
					}
					System.out.println("after");

					// Ask the process for its exitValue. If the process
					// is not finished, an IllegalThreadStateException
					// is thrown. If it is finished, we fall through and
					// the variable finished is set to true.
					exitValue = p.exitValue();
					finished = true;
				} catch (IllegalThreadStateException e) {
					// Process is not finished yet;
					// Sleep a little to save on CPU cycles
					Thread.currentThread().sleep(500);
				}
			}
		} catch (Exception e) {
			// unexpected exception! print it out for debugging...
			System.err.println("doWaitFor(): unexpected exception - "
					+ e.getMessage());
		}

		// return completion status to caller
		return exitValue;
	}
}
