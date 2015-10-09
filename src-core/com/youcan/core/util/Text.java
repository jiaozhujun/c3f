package com.youcan.core.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class Text {
	private static final Pattern PATTERN_EMAIL = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$");
	private static final Pattern PATTERN_MDN = Pattern.compile("^1[358][0-9]{9}$");
	/**
	 * 
	 * ^[\u2E80-\u9FFF]+$
	 * 匹配所有东亚区的语言
	 * ^[\u4E00-\u9FFF]+$
	 * 匹配简体和繁体
	 * ^[\u4E00-\u9FA5]+$
	 * 匹配简体
	 */
	private static final Pattern PATTERN_CHINESE = Pattern.compile("^[\u4E00-\u9FFF]+$");

	/**
	 * 验证是否是邮箱登录
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
		if (email != null) {
			return PATTERN_EMAIL.matcher(email).matches();
		} else {
			return false;
		}
	}

	/**
	 * 验证手机号（11位）
	 * @param mdn
	 * @return
	 */
	public static boolean isMdn(String mdn){
		if (mdn != null) {
			return PATTERN_MDN.matcher(mdn).matches();
		} else {
			return false;
		}
	}
	
	public static boolean isChinese(String chinese) {
		if (chinese != null) {
			return PATTERN_CHINESE.matcher(chinese).matches();
		} else {
			return false;
		}
	}

    /**
     * 截取字符串，并使用replace替代超出部分的字符串
     * @param src
     * @param max
     * @param keep
     * @param replace
     * @return
     */
    public static String strClip(String src, int max, int keep, String replace) {
    	if (src.length() <= max) {
    		return src;
    	}
		return src.substring(0, keep).concat(replace);
    }

    /**
	 * 
	 * 将字节数组转换为字符串
	 * 
	 * @param bytes
	 *            需要转换的字节数组
	 * 
	 * @return 转换后的字符串
	 * 
	 */
	public static String byte2String(byte[] bytes) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			DataInputStream dis = new DataInputStream(bais);
			String s = dis.readUTF();
			// 关闭流
			dis.close();
			bais.close();
			return s;
		} catch (Exception e) {
			return null;
		}
	}

    /**
     * 输入密码的字符形式，返回字节数组形式。
     * 如输入字符串：AD67EA2F3BE6E5AD
     * 返回字节数组：{173,103,234,47,59,230,229,173}
     */
    public static byte[] getBytesFromStr(String str) {
    	if (str == null || str.length() % 2 != 0) {
    		return null;
    	}
		int bl = str.length()/2;
		byte[] bRet = new byte[bl];
		for (int i = 0; i < bl; i++) {
			bRet[i] = (byte)(16 * Character.digit(str.charAt(2*i), 16) + Character.digit(str.charAt(2*i + 1), 16));
		}
		return bRet;
    }

    /**
	 * 
	 * 将字符串转换为字节数组
	 * 
	 * @param s
	 *            需要转换的字符串
	 * 
	 * @return 转换后生成的字节数组
	 * 
	 */
	public static byte[] string2Byte(String s) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream bos = new DataOutputStream(baos);
			bos.writeUTF(s);
			byte[] bytes = baos.toByteArray();
			// 关闭流
			bos.close();
			baos.close();
			return bytes;
		} catch (Exception e) {
			return null;
		}
	}

	public static int bytes2int(byte[] b) {
		int mask = 0xff;
		int temp = 0;
		int res = 0;
		for (int i = 0; i < 4; i++) {
			res <<= 8;
			temp = b[i] & mask;
			res |= temp;
		}
		return res;
	}

	public static byte[] int2bytes(int num) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			b[i] = (byte) (num >>> (24 - i * 8));
		}
		return b;
	}

	public static byte[] long2bytes(long num) {
		byte[] b = new byte[8];
		for (int i = 0; i < 8; i++) {
			b[i] = (byte) (num >>> (56 - i * 8));
		}
		return b;
	}

	public static long bytes2long(byte[] b) {
		int mask = 0xff;
		int temp = 0;
		long res = 0;
		for (int i = 0; i < 8; i++) {
			res <<= 8;
			temp = b[i] & mask;
			res |= temp;
		}
		return res;
	}

	public static final String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static final String MD5(String s, String charset) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes(charset);
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static final double calculate(String expression) {
		// 中缀 => 后缀表达式
		//String expression = "(1.9 + (20 + 41) / (25*11) - 3 ) * 2"; // 中缀
		String postExpression = ""; // 后缀
		char[] Operators = new char[expression.length()];
		int Top = -1;
		for (int i = 0; i < expression.length(); i++) {
			char C = expression.charAt(i);
			switch (C) {
			case ' ':
				break;
			case '+': // 操作符
			case '-':
				while (Top >= 0) {// 栈不为空时
					char c = Operators[Top--]; // pop Operator
					if (c == '(') {
						Operators[++Top] = c; // push Operator
						break;
					}
					postExpression = postExpression + c;
				}
				Operators[++Top] = C; // push Operator
				postExpression += " ";
				break;
			case '*': // 操作符
			case '/':
				while (Top >= 0) {// 栈不为空时
					char c = Operators[Top--]; // pop Operator
					if (c == '(') {
						Operators[++Top] = c; // push Operator
						break;
					}
					if (c == '+' || c == '-') {
						Operators[++Top] = c; // push Operator
						break;
					}
					postExpression = postExpression + c;
				}
				Operators[++Top] = C; // push Operator
				postExpression += " ";
				break;
			case '(': // 操作符
				Operators[++Top] = C;
				postExpression += " ";
				break;
			case ')': // 操作符
				while (Top >= 0) {// 栈不为空时
					char c = Operators[Top--]; // pop Operator
					if (c == '(') {
						break;
					}
					postExpression = postExpression + c;
				}
				postExpression += " ";
				break;
			default: // 操作数
				postExpression = postExpression + C;
				break;
			}
		}
		while (Top >= 0) {
			postExpression = postExpression + Operators[Top--]; // pop Operator
		}

		// 后缀表达式计算
		double[] Operands = new double[postExpression.length()];
		double x, y, v;
		Top = -1;
		String Operand = "";
		for (int i = 0; i < postExpression.length(); i++) {
			char c = postExpression.charAt(i);
			if ((c >= '0' && c <= '9') || c == '.') {
				Operand += c;
			}

			if ((c == ' ' || i == postExpression.length() - 1) && Operand != "") // Update
			{
				Operands[++Top] = java.lang.Double.parseDouble(Operand); // push
																			// Operands
				Operand = "";
			}

			if (c == '+' || c == '-' || c == '*' || c == '/') {
				if ((Operand != "")) {
					Operands[++Top] = java.lang.Double.parseDouble(Operand); // push
																				// Operands
					Operand = "";
				}
				y = Operands[Top--]; // pop 双目运算符的第二操作数 (后进先出)注意操作数顺序对除法的影响
				x = Operands[Top--]; // pop 双目运算符的第一操作数
				switch (c) {
				case '+':
					v = x + y;
					break;
				case '-':
					v = x - y;
					break;
				case '*':
					v = x * y;
					break;
				case '/':
					v = x / y; // 第一操作数 / 第二操作数 注意操作数顺序对除法的影响
					break;
				default:
					v = 0;
					break;
				}
				Operands[++Top] = v; // push 中间结果再次入栈
			}
		}
		v = Operands[Top--]; // pop 最终结果
		return v;
	}
	
	public static final boolean convert2Html(String sourceUrl, String destHtmlFile, String charset) {
		URL url;
		try {
			url = new URL(sourceUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;
		}
		HttpURLConnection httpUrlConnection;
		try {
			httpUrlConnection = (HttpURLConnection)url.openConnection();
			httpUrlConnection.connect();
			if (httpUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				if ("".equals(charset)) {
					charset = "UTF-8";
				}
				BufferedReader br = new BufferedReader(
						new InputStreamReader(httpUrlConnection.getInputStream(), charset));
				String inputLine;
				OutputStreamWriter osr = new OutputStreamWriter(
						new FileOutputStream(destHtmlFile), charset);
				while ((inputLine = br.readLine()) != null) {
					osr.write(inputLine.trim());
				}
				br.close();
				osr.close();
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static final int toInt(String string, int defaultValue) {
		if (string==null||"".equals(string)) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException err) {
			return defaultValue;
		}
	}

	public static final long toLong(String string, long defaultValue) {
		if (string==null||"".equals(string)) {
			return defaultValue;
		}
		try {
			return Long.parseLong(string);
		} catch (NumberFormatException err) {
			return defaultValue;
		}
	}

	public static final boolean toBoolean(String string, boolean defaultValue) {
		if (string==null||"".equals(string)) {
			return defaultValue;
		}
		try {
			return Boolean.parseBoolean(string);
		} catch (NumberFormatException err) {
			return defaultValue;
		}
	}
	
	public static final double toDouble(String string, double defaultValue) {
		if (string==null||"".equals(string)) {
			return defaultValue;
		}
		try {
			return Double.parseDouble(string);
		} catch (NumberFormatException err) {
			return defaultValue;
		}
	}

	public static final float toFloat(String string, float defaultValue) {
		if (string==null||"".equals(string)) {
			return defaultValue;
		}
		try {
			return Float.parseFloat(string);
		} catch (NumberFormatException err) {
			return defaultValue;
		}
	}
	
	public static final String toString(String string, String defaultValue) {
		if (string == null || "".equals(string)) {
			return defaultValue;
		}
		return string;
	}
	
	public static final String checkType(String str) {
		String type = "String";
		if (str.matches("^-?\\d+[lL]?$")) {
			char c = str.charAt(str.length() - 1);
			if (c == 'l' || c == 'L') {
				type = "long";
			} else {
				long tmp = toLong(str, 0);
				if (tmp > Integer.MAX_VALUE || tmp < Integer.MIN_VALUE) {
					type = "long";
				} else {
					type = "int";
				}
			}
		} else if (str.matches("^-?\\d+\\.?\\d+[fF]$")) {
			type = "float";
		} else if (str.matches("^-?\\d+\\.\\d+[dD]?$")) {
			type = "double";
		} else if (str.matches("^(true)|(false)$")) {
			type = "boolean";
		} else {
			type = "String";
		}
		return type;
	}
	
	public static final String getFileLength(long length) {
		int c = (64 - Long.numberOfLeadingZeros(length)) / 10;
		switch(c) {
			case 0 : {
				return length + "B";
			}
			case 1 : {
				return (length >>> 10) + "KB";
			}
			case 2 : {
				return (length >>> 20) + "MB";
			}
			case 3 : {
				return (length >>> 30) + "GB";
			}
			default : {
				return (length >>> 40) + "TB";
			}
		}
	}

	public static final String getFileLength(long length, int r) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(r);
		int c = (64 - Long.numberOfLeadingZeros(length)) / 10;
		switch(c) {
			case 0 : {
				return length + "B";
			}
			case 1 : {
				return df.format(length / 1024D) + "KB";
			}
			case 2 : {
				return df.format((length >>> 10) / 1024D) + "MB";
			}
			case 3 : {
				return df.format((length >>> 20) / 1024D) + "GB";
			}
			default : {
				return df.format((length >>> 30) / 1024D) + "TB";
			}
		}
	}

	public static String streamToString(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len;
		while ((len = is.read()) != -1) {
			baos.write(len);
		}
		return baos.toString();
	}

	public static final char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String toHexString(byte[] b) {
		return toHexString(b, 0, b.length);
	}
	
	public static String toHexString(byte[] b, int offset, int length) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = offset; i < length; i++) {
			sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
			sb.append(hexChar[b[i] & 0x0f]);
		}
		return sb.toString();
	}
	
	public static boolean compareBytes(byte[] b1, byte[] b2) {
		int length;
		if((length=b1.length)==b2.length) {
			for (int i=0;i<length;i++) {
				if(b1[i]!=b2[i]) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public static String escapeSingleQuote(String src) {
		if (src == null) {
			src = "";
		}
		src = src.replaceAll("\\\\", "\\\\\\\\");
		return src.replaceAll("'", "\\\\'");
	}
	
	public static String escapeDoubleQuote(String src) {
		if (src == null) {
			src = "";
		}
		return src.replaceAll("'", "\\\\'");
	}
}