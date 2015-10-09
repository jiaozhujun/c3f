package com.youcan.base.user;

public class UserDao {
	public final static short USERTYPE_NONE = 0;
	public final static short USERTYPE_MOBILE = 1;
	public final static short USERTYPE_WEB = 2;
	public final static short LOGIN_SUCCESS = 1;
	public final static short LOGIN_FAILED_PWD = 2;
	public final static short LOGIN_FAILED_DEVICEID = 3;
	public final static short LOGIN_FAILED_USERNAME = 4;

	public final static short DeviceType_MOC = 1;
	public final static short DeviceType_MWB = 2;
	public final static short DeviceType_PCW = 3;
	public final static short DeviceType_PCC = 4;
	/*
	 * 
	 * Field Type Collation Null Key Default Extra Privileges Comment
	 * -------------- ------------------- --------------- ------ ------
	 * -------------- -------------- -------------------------------
	 * --------------------- id int(20) unsigned (NULL) NO PRI (NULL)
	 * auto_increment select,insert,update,references 用户ID userName varchar(50)
	 * utf8_general_ci NO null select,insert,update,references 用户名 userGroup
	 * tinyint(3) (NULL) YES 0 select,insert,update,references 用户组 email
	 * varchar(100) utf8_general_ci NO null@null.null
	 * select,insert,update,references 电子邮件 mdn varchar(15) utf8_general_ci NO
	 * 00000000000 select,insert,update,references 手机号码 imei varchar(20)
	 * utf8_general_ci NO null select,insert,update,references 手机imei password
	 * varchar(32) utf8_general_ci NO null select,insert,update,references 用户密码
	 * userType tinyint(3) unsigned (NULL) NO 0 select,insert,update,references
	 * 用户类型 regTime int(10) unsigned (NULL) NO 0 select,insert,update,references
	 * 注册时间 loginCount int(10) unsigned (NULL) NO 0
	 * select,insert,update,references 登录次数 lastLoginTime int(10) unsigned
	 * (NULL) NO 0 select,insert,update,references 最近登录时间 lastLoginPlace
	 * varchar(50) utf8_general_ci NO null select,insert,update,references
	 * 最近登录地方 loginFailed tinyint(3) unsigned (NULL) NO 0
	 * select,insert,update,references 登录失败次数 userKey varchar(32)
	 * utf8_general_ci NO userkey select,insert,update,references 用户登录sessionid
	 * state tinyint(3) unsigned (NULL) NO 0 select,insert,update,references 状态
	 */
	private long id; // 用户ID
	private String userName; // 用户名
	private short userGroup; // 用户组
	private String email; // 电子邮件
	private String mdn; // 手机号码
	private String deviceId; // 设备ID
	private int deviceType; // 设备类型
	private short userType; // 用户类型
	private long regTime; // 注册时间
	private int loginCount; // 登录次数
	private long lastLoginTime; // 最近登录时间
	private String lastLoginPlace; // 最近登录IP
	private String userKey; // 用户登录sessionid
	private short state; // 用户状态
	private String pwd; // 密码
	private int score; // 积分
	private int role; // 角色
	private String userLabel; // 用户昵称或真实姓名
	private short usbKey; // usbKey

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public short getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(short userGroup) {
		this.userGroup = userGroup;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMdn() {
		return mdn;
	}

	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public short getUserType() {
		return userType;
	}

	public void setUserType(short userType) {
		this.userType = userType;
	}

	public long getRegTime() {
		return regTime;
	}

	public void setRegTime(long regTime) {
		this.regTime = regTime;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginPlace() {
		return lastLoginPlace;
	}

	public void setLastLoginPlace(String lastLoginPlace) {
		this.lastLoginPlace = lastLoginPlace;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getUserLabel() {
		return userLabel;
	}

	public void setUserLabel(String userLabel) {
		this.userLabel = userLabel;
	}

	public short getUsbKey() {
		return usbKey;
	}

	public void setUsbKey(short usbKey) {
		this.usbKey = usbKey;
	}

}
