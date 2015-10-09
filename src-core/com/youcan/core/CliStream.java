package com.youcan.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import org.apache.log4j.Logger;

import com.youcan.core.CallBack;

public class CliStream implements Runnable {
	public static final int STATE_INIT = 0;
	public static final int STATE_BEGIN = 1;
	public static final int STATE_END = 2;
	protected InputStream inputStream;
	protected OutputStream redirect = null;
	protected CharSequence spliter = " ";
	private int startLine = -1, stopLine = -1;
	private String charset = null;
	private Logger log = null;
	private CallBack callBack = null;

	public CliStream(InputStream inputStream) {
		this(inputStream, null);
	}

	public CliStream(InputStream inputStream, Logger log) {
		this.inputStream = inputStream;
		this.log = log;
	}

	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(charset == null ? new InputStreamReader(inputStream) : new InputStreamReader(inputStream, charset));
			String line = null;
			if (redirect != null) {
				PrintWriter pw = new PrintWriter(redirect);
				while ((line = br.readLine()) != null) {
					pw.println(line);
				}
				pw.flush();
			} else {
				if (callBack != null) {
					StringBuilder output = new StringBuilder();
					if (startLine > -1) {
						int linenum = 0;
						while ((line = br.readLine()) != null) {
							if (lineInvolved(linenum++)) {
								output.append(line).append(spliter);
							}
						}
					} else {
						while ((line = br.readLine()) != null) {
							output.append(line).append(spliter);
						}
					}
					callBack.finished(output.toString());
				} else {
					if (log != null && log.isDebugEnabled()) {
						while ((line = br.readLine()) != null) {
							//记录DEBUG日志
							log.debug(line);
						}
					} else {
						while (br.readLine() != null) {
							//不做任何操作
						}
					}
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			if (callBack != null) {
				callBack.finished("ERROR");
			}
		}
	}

	public void setLineNums(String lineNums) {
		int splidIndex = lineNums.indexOf('-');
		if (splidIndex == 0) {
			//-n
			startLine = 0;
			stopLine = (lineNums.length() == 1) ? -1 : Integer.parseInt(lineNums.substring(1));
		} else if (splidIndex == -1) {
			//n
			startLine = stopLine = Integer.parseInt(lineNums);
		} else if (splidIndex == (lineNums.length()-1)) {
			//n-
			startLine = Integer.parseInt(lineNums.substring(0,splidIndex));
			stopLine = -1;
		} else {
			//m-n
			startLine = Integer.parseInt(lineNums.substring(0,splidIndex));
			stopLine = Integer.parseInt(lineNums.substring(splidIndex+1));
		}
	}
	
	public boolean lineInvolved(int lineNum) {
		return (stopLine == -1) ? (lineNum >= startLine) : (lineNum >= startLine && lineNum <= stopLine);
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setRedirect(OutputStream redirect) {
		this.redirect = redirect;
	}

	public void setSpliter(CharSequence spliter) {
		this.spliter = spliter;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public CallBack getCallBack() {
		return callBack;
	}

	public void setCallBack(CallBack callBack) {
		this.callBack = callBack;
	}
}