package com.youcan.commons.dcpsClient;

public interface ResponseInterface {
	public static final int NO_RESULT = 0;
	public static final int UCAP_RETURN_OK = 100;
	public static final int UCAP_RETURN_TARGET0 = 101;
	
	public static final int UCAP_SOURCE_NOTAVAILABLE = 200;
	public static final int UCAP_SOURCE_NOTAVAILABLE_HTTP = 201;
	public static final int UCAP_SOURCE_NOTAVAILABLE_FILE = 202;
	public static final int UCAP_SOURCE_FORMAT_NOTSUPPORT = 203;
	public static final int UCAP_SOURCE_FORMAT_UNKNOWN = 204;
	public static final int UCAP_SOURCE_FILEBAD = 205;
	public static final int UCAP_SOURCE_FILETOOBIG = 206;
	public static final int UCAP_SOURCE_FETCH_TIMEOUT = 207;
	public static final int UCAP_SOURCE_URIERROR = 208;
	public static final int UCAP_SOURCE_NEEDTYPE = 209;
	public static final int UCAP_SOURCE_FETCH_FAILED = 210;
	public static final int UCAP_SOURCE_CHECK_FORMAT_ERROR = 211;

	public static final int UCAP_TARGET_TOOBIG_CANCELED = 300;
	public static final int UCAP_TARGET_TOOBIG_COMPLEATED = 301;
	public static final int UCAP_TARGET_FAILED = 302;
	public static final int UCAP_TARGET_SERVER_BUSY = 303;
	public static final int UCAP_TARGET_CONVERT_TIMEOUT = 304;
	public static final int UCAP_TARGET_CONVERT_DELAY = 305;
	public static final int UCAP_TARGET_DCPS_NOTAVAILABLE = 306;
	public static final int UCAP_TARGET_DCPS_UNKNOWNHOST = 307;
	public static final int UCAP_TARGET_DCPS_IOERROR = 308;
	public static final int UCAP_TARGET_DCPS_PROTOCALERROR = 309;
	
	public static final int UCAP_REQUEST_PARSE_ERROR = 400;
	public static final int UCAP_REQUEST_NULL = 401;
	public static final int UCAP_REQUEST_CHARSET_ERROR = 402;
	public static final int UCAP_REQUEST_IO_ERROR = 403;
	/*用于UCAP, 404-449*/
	public static final int UCAP_REQUEST_LACK_UA = 404;
	public static final int UCAP_REQUEST_INVALID_UA = 405;
	public static final int UCAP_REQUEST_LACK_CONTENT = 406;
	public static final int UCAP_REQUEST_INVALID_CONTENT = 407;
	public static final int UCAP_REQUEST_TOOMANY_CONTENT = 408;
	public static final int UCAP_REQUEST_LACK_SID = 409;
	public static final int UCAP_REQUEST_INVALID_SID = 410;
	public static final int UCAP_REQUEST_LACK_FILEMAXSIZE = 411;
	public static final int UCAP_REQUEST_INVALID_FILEMAXSIZE = 412;
	public static final int UCAP_REQUEST_LACK_TARGET = 413;
	public static final int UCAP_REQUEST_INVALID_TARGET = 414;
	public static final int UCAP_REQUEST_LACK_SOURCE = 415;
	public static final int UCAP_REQUEST_INVALID_SOURCE = 416;
	public static final int UCAP_REQUEST_LACK_TYPE = 417;
	public static final int UCAP_REQUEST_INVALID_TYPE = 418;
	public static final int UCAP_REQUEST_LACK_FORMAT = 419;
	public static final int UCAP_REQUEST_INVALID_FORMAT = 420;
	public static final int UCAP_REQUEST_PARAMS = 421;
	public static final int UCAP_REQUEST_NOTREQ = 422;
	public static final int UCAP_REQUEST_LACK_FILENAME = 423;
	public static final int UCAP_REQUEST_INVALID_FILENAME = 424;
	/*用于WCAP, 450-479*/
	public static final int UCAP_REQUEST_LACK_phyFileID = 450;
	public static final int UCAP_REQUEST_INVALID_phyFileID = 451;
	public static final int UCAP_REQUEST_LACK_nodeIndex = 452;
	public static final int UCAP_REQUEST_INVALID_nodeIndex = 453;
	public static final int UCAP_REQUEST_LACK_filePath = 454;
	public static final int UCAP_REQUEST_INVALID_filePath = 455;
	
	public static final int UCAP_ADAPT_UA_NOMATCHED = 500;
	public static final int UCAP_ADAPT_UA_CHECKERROR = 501;
	public static final int UCAP_ADAPT_UA_NONEEXCLUSIVE = 502;
	
	public static final int UCAP_UNKNOWN_ERROR = 900;

	public void doResponse(int resultCode, String resultMsg);
}
