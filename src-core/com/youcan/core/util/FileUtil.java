package com.youcan.core.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.io.XMLWriter;

import com.youcan.core.CallBack;
import com.youcan.core.CliStream;
import com.youcan.core.l;

public class FileUtil {
	private static final String DIGEST_TYPE = "MD5";
	private static final int BUF_SIZE = 8192;
	
	public FileUtil() {
		//do nothing
	}

	public static boolean writeXmlFile(String fileName, Document doc) {
		File file = new File(fileName);
		XMLWriter xw = null;
		boolean result;
		try {
			xw = new XMLWriter(new FileOutputStream(file));
			xw.write(doc);
			xw.close();
			result = true;
		} catch (IOException e) {
			result = false;
		}
		return result;
	}
	
	public static String getDigest(File file) {
		String digest = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(DIGEST_TYPE);
			FileInputStream fis = new FileInputStream(file);
			byte[] buf = new byte[BUF_SIZE];
			int len;
			while ((len = fis.read(buf)) != -1) {
				messageDigest.update(buf, 0, len);
			}
			fis.close();
			digest = Text.toHexString(messageDigest.digest());
			messageDigest.reset();
		} catch (IOException | NoSuchAlgorithmException e) {
			//do nothing
		}
		return digest;
	}
	
	/**
	 * 将输入流in写入到文件fileName，并返回文件摘要
	 * @param fileName 保存文件路径，如果文件存在，会先删除该文件
	 * @param in 输入流
	 * @return 文件摘要
	 */
	public static String writeFile(String fileName, InputStream in) {
		if (fileName == null || in == null) {
			return null;
		}
		String digest = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(DIGEST_TYPE);
			File file = new File(fileName);
			if (file.exists()) {
				file.delete();
			}
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buf = new byte[BUF_SIZE];
			int len;
			while ((len = in.read(buf)) != -1) {
				fos.write(buf, 0, len);
				messageDigest.update(buf, 0, len);
			}
			fos.flush();
			fos.close();
			in.close();
			digest = Text.toHexString(messageDigest.digest());
			messageDigest.reset();
		} catch (IOException | NoSuchAlgorithmException e) {
			//do nothing
		}
		return digest;
	}

	/**
	 * 将输入流in写入到文件fileName，并返回文件摘要，写入完成后不会关闭输入流in
	 * @param fileName 保存文件路径，如果文件存在，会先删除该文件
	 * @param in 输入流
	 * @param length 要写入的输入流的长度
	 * @return 文件摘要
	 */
	public static String writeFile(String fileName, InputStream in, int length) {
		if (fileName == null || in == null) {
			return null;
		}
		String digest = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(DIGEST_TYPE);
			File file = new File(fileName);
			if (file.exists()) {
				file.delete();
			}
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buf = new byte[BUF_SIZE];
			int len = 0, lenPlus = 0, writedLength = 0;
			if(length <= 0){
				//读到最后
				while((len = in.read(buf)) != -1){
					fos.write(buf, 0, len);
					messageDigest.update(buf, 0, len);
				}
			}else{
				while(writedLength < length){
					writedLength += buf.length;
					if(writedLength <= length){
						if((len = in.read(buf)) != -1){
							fos.write(buf, 0, len);
							messageDigest.update(buf, 0, len);
						}
					}else{
						len = buf.length - (writedLength - length);
						if((len = in.read(buf, 0, len)) != -1){
							fos.write(buf, 0, len);
							messageDigest.update(buf, 0, len);
						}
					}
					lenPlus += len;
					writedLength = lenPlus;
				}
			}
			fos.flush();
			fos.close();
			digest = Text.toHexString(messageDigest.digest());
			messageDigest.reset();
		} catch (IOException | NoSuchAlgorithmException e) {
			l.error("Exception:" + e.getMessage());
			e.printStackTrace();
		}
		return digest;
	}

	public static boolean writeFile(String fileName, StringBuilder fileContent) {
		return writeFile(fileName, fileContent, "utf-8");
	}

	public static boolean writeFile(String fileName, StringBuilder fileContent,
			String fileEncoding) {
		boolean result = false;
		File tgtFile = new File(fileName);
		try {
			PrintWriter pw = new PrintWriter(tgtFile, fileEncoding);
			pw.print(fileContent);
			pw.flush();
			pw.close();
			result = true;
		} catch (FileNotFoundException e) {
			if (makeDirs(fileName,true)) {
				return writeFile(fileName, fileContent, fileEncoding);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	public static String readStringFromFile(String fileName) {
		File file = new File(fileName);
        BufferedReader reader = null;
        StringBuilder out = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tmp = null;
            out = new StringBuilder();
            while ((tmp = reader.readLine()) != null) {
            	out.append(tmp);
            }
            reader.close();
        } catch (IOException e) {
            out = null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                	//do nothing
                }
            }
        }
        if (out != null) {
        	return out.toString();
        }
		return null;
	}
	
	public static boolean makeDirs(String fileName, boolean hasFile) {
		boolean result = false;
		if (hasFile) {
			int lastIndex = fileName.lastIndexOf('/');
			if (lastIndex > -1) {
				String filePath = fileName.substring(0, lastIndex);
				if (filePath != null && !filePath.equals("")) {
					result = new File(filePath).mkdirs();
				}
			}
		} else {
			result = new File(fileName).mkdirs();
		}
		return result;
	}
	
	public static boolean moveFile(String srcFile, String tgtFile) {
		try {
			File tgFile = new File(tgtFile);
			if (tgFile.exists()) {
				tgFile.delete();
			}
			FileUtils.moveFile(new File(srcFile), tgFile);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		// return new File(srcFile).renameTo(new File(tgtFile));
	}
	
	public static boolean moveFile(String srcFile, String tgtFile, boolean isNfs) {
		if (isNfs) {
			try {
				Process process = Runtime.getRuntime().exec(
						"/bin/mv -f " + srcFile + " " + tgtFile);
				new Thread(new CliStream(process.getInputStream())).start();
				new Thread(new CliStream(process.getErrorStream())).start();
				if (process.waitFor() == 0) {
					return true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return false;
		}
		return moveFile(srcFile, tgtFile);
	}
	
	public static boolean copyFile(File src, File dest) {
		boolean result = false;
		try {
			FileInputStream fis = new FileInputStream(src);
			FileChannel sourcefc = fis.getChannel();
			FileOutputStream fos = new FileOutputStream(dest);
			FileChannel targetfc = fos.getChannel();
			if (sourcefc.transferTo(0, sourcefc.size(), targetfc) == sourcefc.size()) {
				result = true;
			}
			targetfc.close();
			sourcefc.close();
			fis.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean mkdir(String dir) {
		try {
			Process process = Runtime.getRuntime().exec("/bin/mkdir -p " + dir);
			new Thread(new CliStream(process.getInputStream())).start();
			new Thread(new CliStream(process.getErrorStream())).start();
			if (process.waitFor() == 0) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deleteFile(String fileName) {
		try {
			Process process = Runtime.getRuntime().exec(
					"/bin/rm -f " + fileName);
			new Thread(new CliStream(process.getInputStream())).start();
			new Thread(new CliStream(process.getErrorStream())).start();
			if (process.waitFor() == 0) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 删除文件夹 linux
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFileDir(String fileName) {
		try {
			Process process = Runtime.getRuntime().exec(
					"/bin/rm -r " + fileName);
			new Thread(new CliStream(process.getInputStream())).start();
			new Thread(new CliStream(process.getErrorStream())).start();
			if (process.waitFor() == 0) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static long checkFileSize(String fileName, boolean isNfs) {
		if (isNfs) {
			// stat -c %s filename
			try {
				Process process = Runtime.getRuntime().exec(
						"/usr/bin/stat -c %s " + fileName);
				CliStream cliStream = new CliStream(process.getInputStream());
				CallBack callBack = new CallBack(5000);
				cliStream.setCallBack(callBack);
				cliStream.setSpliter("\n");
				new Thread(cliStream).start();
				callBack.waitBack();
				String filesize = callBack.getContent().trim();
				if ("".equals(filesize)) {
					return 0L;
				}
				return Long.parseLong(filesize);
			} catch (IOException e) {
				return 0L;
			} catch (NumberFormatException e) {
				return 0L;
			}
		}
		return new File(fileName).length();
	}

	public void getSubFile(String fileName, ArrayList<String> fileList) {
		File parentF = new File(fileName);
		if (!parentF.exists()) {
			return;
		}
		if (parentF.isFile()) {
			fileList.add(parentF.getAbsolutePath());
			return;
		}
		String[] subFiles = parentF.list();
		for (int i = 0; i < subFiles.length; i++) {
			getSubFile(parentF.getAbsolutePath() + "/" + subFiles[i], fileList);
		}
	}

	public void getSubFile(String fileName, FilenameFilter filter,
			ArrayList<String> fileList) {
		File parentF = new File(fileName);
		if (!parentF.exists()) {
			return;
		}
		if (parentF.isFile()) {
			fileList.add(parentF.getAbsolutePath());
			return;
		}
		String[] subFiles = parentF.list(filter);
		for (int i = 0; i < subFiles.length; i++) {
			getSubFile(parentF.getAbsolutePath() + "/" + subFiles[i], fileList);
		}
	}

	public ArrayList<String> getFileList(String rootPath, boolean iteration) {
		ArrayList<String> fileList = new ArrayList<String>();
		if (iteration) {
			getSubFile(rootPath, fileList);
		} else {
			// 不循环
			File parentF = new File(rootPath);
			if (parentF.exists() && parentF.isDirectory()) {
				String[] subFiles = parentF.list();
				for (int i = 0; i < subFiles.length; i++) {
					fileList.add(subFiles[i]);
				}
			}
		}
		return fileList;
	}

	public ArrayList<String> getFilterFileList(String rootPath,
			FilenameFilter filter, boolean iteration) {
		ArrayList<String> fileList = new ArrayList<String>();
		if (iteration) {
			getSubFile(rootPath, filter, fileList);
		} else {
			// 不循环
			File parentF = new File(rootPath);
			if (parentF.exists() && parentF.isDirectory()) {
				String[] subFiles = parentF.list(filter);
				for (int i = 0; i < subFiles.length; i++) {
					fileList.add(subFiles[i]);
				}
			}
		}
		return fileList;
	}

	public static File createFileFromBytes(byte[] b, String outputFile) {
		BufferedOutputStream stream = null;
		File file = null;
		try {
			file = new File(outputFile);
			FileOutputStream fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return file;
	}
}
