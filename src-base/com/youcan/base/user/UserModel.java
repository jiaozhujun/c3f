package com.youcan.base.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.youcan.core.g;
import com.youcan.core.l;
import com.youcan.core.db.Db;
import com.youcan.core.util.Text;

public class UserModel {
	public UserModel() {
		//do nothing
	}

	public UserDao mobileUserReg(String deviceId) {
		UserDao userDao = new UserDao();
		userDao.setUserName(deviceId);
		userDao.setUserGroup(Short.parseShort(g.var("user.default.group")));
		userDao.setEmail("none");
		userDao.setMdn("none");
		userDao.setDeviceId(deviceId);
		userDao.setDeviceType(UserDao.DeviceType_MOC);
		userDao.setPwd(Text.MD5(deviceId));
		userDao.setUserType(UserDao.USERTYPE_MOBILE);
		userDao.setRegTime(System.currentTimeMillis() / 1000);
		userDao.setState(Short.parseShort(g.var("user.default.state")));
		userDao.setScore(Integer.parseInt(g.var("user.default.score")));
		return userReg(userDao);
	}

	public long checkUserByDeviceId(String deviceId) {
		Db db = null;
		StringBuffer sb = new StringBuffer();
		long uid = 0L;
		sb.append("SELECT id FROM user WHERE deviceid='").append(deviceId)
				.append("'");
		try {
			db = new Db();
			ResultSet rs = db.executeQuery(sb.toString());
			if (db.getNumRows() == 1 && rs.next()) {
				uid = rs.getLong(1);
			} else {
				uid = 0L;
			}
		} catch (SQLException e) {
			uid = 0L;
			l.error("Check 用户的DEVICEID  SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return uid;
	}

	public UserDao getUser(long uid) {
		Db db = null;
		UserDao userDao = null;
		try {
			db = new Db();
			StringBuffer sb = new StringBuffer();
			sb.append(
					"SELECT id,userName,userGroup,email,mdn,deviceid,deviceType,pwd,userType,regTime,state,score FROM user WHERE id=\"")
					.append(uid).append('"');
			ResultSet rs = db.executeQuery(sb.toString());
			if (db.getNumRows() == 1 && rs.next()) {
				userDao = new UserDao();
				userDao.setId(rs.getLong(1));
				userDao.setUserName(rs.getString(2));
				userDao.setUserGroup(rs.getShort(3));
				userDao.setEmail(rs.getString(4));
				userDao.setMdn(rs.getString(5));
				userDao.setDeviceId(rs.getString(6));
				userDao.setDeviceType(rs.getInt(7));
				userDao.setPwd(rs.getString(8));
				userDao.setUserType(rs.getShort(9));
				userDao.setRegTime(rs.getLong(10));
				userDao.setState(rs.getShort(11));
				userDao.setScore(rs.getInt(12));
			}
		} catch (SQLException e) {
			userDao = null;
			l.error("获取USER对象  SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return userDao;
	}

	public ArrayList<UserDao> listUser() {
		Db db = null;
		ArrayList<UserDao> userDaoList = null;
		try {
			db = new Db();
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id,userName,userGroup,email,mdn,deviceid,deviceType,pwd,userType,regTime,lastLoginTime,lastLoginPlace,userKey,state,score,userLabel FROM user");
			ResultSet rs = db.executeQuery(sb.toString());
			UserDao userDao = null;
			userDaoList = new ArrayList<UserDao>(db.getNumRows());
			while (rs.next()) {
				userDao = new UserDao();
				userDao.setId(rs.getLong(1));
				userDao.setUserName(rs.getString(2));
				userDao.setUserGroup(rs.getShort(3));
				userDao.setEmail(rs.getString(4));
				userDao.setMdn(rs.getString(5));
				userDao.setDeviceId(rs.getString(6));
				userDao.setDeviceType(rs.getInt(7));
				userDao.setPwd(rs.getString(8));
				userDao.setUserType(rs.getShort(9));
				userDao.setRegTime(rs.getLong(10));
				userDao.setLastLoginTime(rs.getLong(11));
				userDao.setLastLoginPlace(rs.getString(12));
				userDao.setUserKey(rs.getString(13));
				userDao.setState(rs.getShort(14));
				userDao.setScore(rs.getInt(15));
				userDao.setUserLabel(rs.getString(16));
				userDaoList.add(userDao);
			}
		} catch (SQLException e) {
			userDaoList = null;
			l.error("获取USER 集合 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return userDaoList;
	}

	public boolean deleteUser(long id) {
		Db db = null;
		boolean result = false;
		StringBuffer sql = new StringBuffer();
		try {
			db = new Db();
			sql.append("DELETE FROM user WHERE id=").append(id);
			if (db.executeUpdate(sql.toString()) == 1) {
				result = true;
			}
		} catch (SQLException e) {
			l.error("删除用户 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return result;
	}

	public boolean activeUser(UserDao u) {
		Db db = null;
		boolean result = false;
		StringBuffer sql = new StringBuffer();
		try {
			db = new Db();
			sql.append("UPDATE user SET state=").append(u.getState())
					.append(" WHERE id=").append(u.getId());
			if (db.executeUpdate(sql.toString()) == 1) {
				result = true;
			}
		} catch (SQLException e) {
			l.error("更改用户状态 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return result;
	}

	public UserDao mobileLogin(String sessionId, String deviceId,
			String userName, String loginPlace) {
		Db db = null;
		UserDao userDao = null;
		try {
			db = new Db();
			StringBuffer sb = new StringBuffer();
			long loginTime = System.currentTimeMillis() / 1000;
			sb.append(
					"SELECT id,userName,userGroup,email,mdn,deviceid,deviceType,pwd,userType,regTime,state,score FROM user WHERE userName=\"")
					.append(userName).append('"');
			ResultSet rs = db.executeQuery(sb.toString());
			if (db.getNumRows() == 1 && rs.next()) {
				userDao = new UserDao();
				userDao.setId(rs.getLong(1));
				userDao.setUserName(rs.getString(2));
				userDao.setUserGroup(rs.getShort(3));
				userDao.setEmail(rs.getString(4));
				userDao.setMdn(rs.getString(5));
				userDao.setDeviceId(rs.getString(6));
				userDao.setDeviceType(rs.getInt(7));
				userDao.setPwd(rs.getString(8));
				userDao.setUserType(rs.getShort(9));
				userDao.setRegTime(rs.getLong(10));
				userDao.setState(rs.getShort(11));
				userDao.setScore(rs.getInt(12));
				userDao.setUserKey(sessionId);
				if (userDao.getUserName().equals(userName)) {
					sb.delete(0, sb.length());
					sb.append(
							"UPDATE user SET loginCount=loginCount+1,lastLoginTime=\"")
							.append(loginTime).append("\",lastLoginPlace=\"")
							.append(loginPlace).append("\",userKey=\"")
							.append(userDao.getUserKey())
							.append("\" WHERE id=").append(userDao.getId());
					db.executeUpdate(sb.toString());

					sb.delete(0, sb.length());
					sb.append(
							"INSERT INTO loginlog (uid,sessionid,identifier,pwd,deviceType,loginPlace,loginTime,tryCount,result) VALUES (\"")
							.append(userDao.getId()).append("\",\"")
							.append(sessionId).append("\",\"").append(deviceId)
							.append("\",\"").append(userName).append("\",\"")
							.append(UserDao.DeviceType_MOC).append("\",\"")
							.append(loginPlace).append("\",\"")
							.append(loginTime).append("\",\"").append(1)
							.append("\",\"").append(UserDao.LOGIN_SUCCESS)
							.append("\")");
					db.execute(sb.toString());
				} else {
					sb.delete(0, sb.length());
					sb.append(
							"INSERT INTO loginlog (uid,sessionid,identifier,pwd,deviceType,loginPlace,loginTime,tryCount,result) VALUES (\"")
							.append(0).append("\",\"").append(sessionId)
							.append("\",\"").append(deviceId).append("\",\"")
							.append(userName).append("\",\"")
							.append(UserDao.DeviceType_MOC).append("\",\"")
							.append(loginPlace).append("\",\"")
							.append(loginTime).append("\",\"").append(1)
							.append("\",\"")
							.append(UserDao.LOGIN_FAILED_USERNAME)
							.append("\")");
					db.execute(sb.toString());
					userDao.setUserKey("");
				}
			} else {
				sb.delete(0, sb.length());
				sb.append(
						"INSERT INTO loginlog (uid,sessionid,identifier,pwd,deviceType,loginPlace,loginTime,tryCount,result) VALUES (\"")
						.append(0).append("\",\"").append(sessionId)
						.append("\",\"").append(deviceId).append("\",\"")
						.append(userName).append("\",\"")
						.append(UserDao.DeviceType_MOC).append("\",\"")
						.append(loginPlace).append("\",\"").append(loginTime)
						.append("\",\"").append(1).append("\",\"")
						.append(UserDao.LOGIN_FAILED_DEVICEID).append("\")");
				db.execute(sb.toString());
			}
		} catch (SQLException e) {
			l.error("用户登录 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return userDao;
	}

	public UserDao userReg(UserDao userDao) {
		Db db = null;
		StringBuffer sb = new StringBuffer();
		// TODO: 判断是否已经注册
		sb.append(
				"INSERT INTO user (userName,userGroup,email,mdn,deviceid,deviceType,pwd,userType,regTime,state,score) VALUES (\"")
				.append(userDao.getUserName()).append("\",\"")
				.append(userDao.getUserGroup()).append("\",\"")
				.append(userDao.getEmail()).append("\",\"")
				.append(userDao.getMdn()).append("\",\"")
				.append(userDao.getDeviceId()).append("\",\"")
				.append(userDao.getDeviceType()).append("\",\"")
				.append(userDao.getPwd()).append("\",\"")
				.append(userDao.getUserType()).append("\",\"")
				.append(userDao.getRegTime()).append("\",\"")
				.append(userDao.getState()).append("\",\"")
				.append(userDao.getScore()).append("\")");
		try {
			db = new Db();
			if (db.executeUpdate(sb.toString(), true) == 1) {
				int uid = db.getGeneratedKey();
				userDao.setId(uid);
				userDao.setUserName("fsty" + uid);
				sb.delete(0, sb.length());
				sb.append("UPDATE user set userName=\"")
						.append(userDao.getUserName()).append("\" WHERE id=")
						.append(uid);
				db.execute(sb.toString());
				return userDao;
			}
		} catch (SQLException e) {
			l.error("用户注册 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return null;
	}

	public UserDao userReg(UserDao userDao, String usergroup) {
		Db db = null;
		StringBuffer sb = new StringBuffer();
		// TODO: 判断是否已经注册
		sb.append(
				"INSERT INTO user (userName,userGroup,email,mdn,deviceid,deviceType,pwd,userType,regTime,state,score) VALUES (\"")
				.append(userDao.getUserName()).append("\",\"")
				.append(userDao.getUserGroup()).append("\",\"")
				.append(userDao.getEmail()).append("\",\"")
				.append(userDao.getMdn()).append("\",\"")
				.append(userDao.getDeviceId()).append("\",\"")
				.append(userDao.getDeviceType()).append("\",\"")
				.append(userDao.getPwd()).append("\",\"")
				.append(userDao.getUserType()).append("\",\"")
				.append(userDao.getRegTime()).append("\",\"")
				.append(userDao.getState()).append("\",\"")
				.append(userDao.getScore()).append("\")");
		try {
			db = new Db();
			if (db.executeUpdate(sb.toString(), true) == 1) {
				int uid = db.getGeneratedKey();
				userDao.setId(uid);
				userDao.setUserName(usergroup + uid);
				sb.delete(0, sb.length());
				sb.append("UPDATE user set userName=\"")
						.append(userDao.getUserName()).append("\" WHERE id=")
						.append(uid);
				db.execute(sb.toString());
				return userDao;
			}
		} catch (SQLException e) {
			l.error("用户注册(user, group) SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return userDao;
	}

	public UserDao getUser(String username, int userGroup) {
		Db db = null;
		UserDao userDao = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer(
					"select id,userName,userGroup,email,mdn,deviceid,deviceType,pwd,userType,regTime,state,score,userLabel from user where userName = '"
							+ username + "' and userGroup = " + userGroup);
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					userDao = new UserDao();
					userDao.setId(rs.getLong(1));
					userDao.setUserName(rs.getString(2));
					userDao.setUserGroup(rs.getShort(3));
					userDao.setEmail(rs.getString(4));
					userDao.setMdn(rs.getString(5));
					userDao.setDeviceId(rs.getString(6));
					userDao.setDeviceType(rs.getInt(7));
					userDao.setPwd(rs.getString(8));
					userDao.setUserType(rs.getShort(9));
					userDao.setRegTime(rs.getLong(10));
					userDao.setState(rs.getShort(11));
					userDao.setScore(rs.getInt(12));
					userDao.setUserLabel(rs.getString(13));
				}
			}
		} catch (SQLException e) {
			userDao = null;
			l.error("根据username， group 获取用户 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return userDao;
	}

	/**
	 * 根据用户名获取用户的名称
	 * 
	 * @param username
	 *            - 用户名 不为空
	 * @return
	 */
	public UserDao getUser(String username) {
		Db db = null;
		UserDao userDao = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer(
					"select id,userName,userGroup,email,mdn,deviceid,deviceType,pwd,userType,regTime,state,score,role,userLabel,usbKey from user where userName = '"
							+ username + "'");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					userDao = new UserDao();
					userDao.setId(rs.getLong(1));
					userDao.setUserName(rs.getString(2));
					userDao.setUserGroup(rs.getShort(3));
					userDao.setEmail(rs.getString(4));
					userDao.setMdn(rs.getString(5));
					userDao.setDeviceId(rs.getString(6));
					userDao.setDeviceType(rs.getInt(7));
					userDao.setPwd(rs.getString(8));
					userDao.setUserType(rs.getShort(9));
					userDao.setRegTime(rs.getLong(10));
					userDao.setState(rs.getShort(11));
					userDao.setScore(rs.getInt(12));
					userDao.setRole(rs.getInt(13));
					userDao.setUserLabel(rs.getString(14));
					userDao.setUsbKey(rs.getShort(15));
				}
			}
		} catch (SQLException e) {
			userDao = null;
			l.error("根据username 获取用户 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return userDao;
	}

	/**
	 * 用户注册
	 * 
	 * @param userName
	 *            - 用户名
	 * @param pwd
	 *            - 密码
	 * @param userGroup
	 *            - 用户组
	 * @param email
	 *            - 邮箱地址
	 * @param mdn
	 *            - 手机号
	 * @param deviceid
	 *            - 设备ID
	 * @param deviceType
	 *            - 设备类型
	 * @param regTime
	 *            - 注册时间
	 * @param state
	 *            - 状态
	 * @param score
	 *            - 分数
	 * @param role
	 *            - 角色
	 * @return -1 参数错误 0 失败 1 成功 500 系统异常
	 */
	public int userReg(String userName, String pwd, int userGroup,
			String email, String mdn, String deviceid, int deviceType,
			short userType, long regTime, int state, int score, int role) {
		Db db = null;
		if (userName == null || userName.equals("") || pwd == null
				|| pwd.equals("") || userGroup <= 0 || role <= 0)
			return -1;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append(
					"insert into user(userName,userGroup,email,mdn,deviceid,deviceType,pwd,userType,regTime,state,score,role) VALUES ('")
					.append(userName + "',").append(userGroup + ",'")
					.append(email + "','").append(mdn + "', '")
					.append(deviceid + "',").append(deviceType + ",")
					.append("'" + pwd + "', ").append(userType + ", ")
					.append(regTime + ",").append(state + ",")
					.append(score + ",").append(role + ")");
			return db.executeUpdate(sql.toString());
		} catch (SQLException e) {
			l.error("用户注册 SQL Exception ", e);
			return 500;
		} finally {
			Db.close(db);
		}
	}

	/**
	 * 用户注册
	 * 
	 * @param userName
	 *            - 用户名
	 * @param pwd
	 *            - 密码
	 * @param userGroup
	 *            - 用户组
	 * @param email
	 *            - 邮箱地址
	 * @param mdn
	 *            - 手机号
	 * @param deviceid
	 *            - 设备ID
	 * @param deviceType
	 *            - 设备类型
	 * @param regTime
	 *            - 注册时间
	 * @param state
	 *            - 状态
	 * @param score
	 *            - 分数
	 * @param role
	 *            - 角色
	 * @param userLabel
	 *            - 用户昵称
	 * @return -1 参数错误 0 失败 1 成功 500 系统异常
	 */
	public int userReg(String userName, String pwd, int userGroup,
			String email, String mdn, String deviceid, int deviceType,
			short userType, long regTime, int state, int score, int role,
			String userLabel, int usbKey) {
		Db db = null;
		if (userName == null || userName.equals("") || pwd == null
				|| pwd.equals("") || userGroup <= 0 || role <= 0
				|| userLabel == null || userLabel.equals(""))
			return -1;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append(
					"insert into user(userName,userGroup,email,mdn,deviceid,deviceType,pwd,userType,regTime,state,score,role,userLabel,usbKey) VALUES ('")
					.append(userName + "',").append(userGroup + ",'")
					.append(email + "','").append(mdn + "', '")
					.append(deviceid + "',").append(deviceType + ",")
					.append("'" + pwd + "', ").append(userType + ", ")
					.append(regTime + ",").append(state + ",")
					.append(score + ",").append(role + ", '")
					.append(userLabel + "', ").append(usbKey + ")");
			return db.executeUpdate(sql.toString());
		} catch (SQLException e) {
			l.error("用户注册 SQL Exception ", e);
			return 500;
		} finally {
			Db.close(db);
		}
	}

	//JIAO:userEdit(UserDao u)
	public int userEdit(int id, String userName, String pwd, int userGroup,
			String email, String mdn, String deviceid, int deviceType,
			short userType, long regTime, int state, int score, int role,
			String userLabel, int usbKey) {
		Db db = null;
		if (id <= 0
				|| ((userName == null || userName.equals(""))
						&& (pwd == null || pwd.equals("")) && userGroup <= 0
						&& role <= 0
						&& (userLabel == null || userLabel.equals(""))
						&& (email == null || email.equals(""))
						&& (mdn == null || mdn.equals(""))
						&& (deviceid == null || deviceid.equals(""))
						&& deviceType < 0 && userType < 0 && regTime < 0
						&& state <= 0 && score < 0 && role <= 0))
			return -1;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("update user set ");
			if (userName != null && !userName.equals(""))
				sql.append("userName = '" + userName + "', ");
			if (userGroup > 0)
				sql.append("userGroup = " + userGroup + ", ");
			if (email != null && !email.equals(""))
				sql.append("email = '" + email + "', ");
			if (mdn != null && !mdn.equals(""))
				sql.append("mdn = '" + mdn + "', ");
			if (deviceid != null && !deviceid.equals(""))
				sql.append("deviceid = '" + deviceid + "', ");
			if (deviceType > 0)
				sql.append("deviceType = " + deviceType + ", ");
			if (pwd != null && !pwd.equals(""))
				sql.append("pwd = '" + pwd + "', ");
			if (userType > 0)
				sql.append("userType = " + userType + ", ");
			if (regTime >= 0)
				sql.append("regTime = " + regTime + ", ");
			if (state > 0)
				sql.append("state = " + state + ", ");
			if (score >= 0)
				sql.append("score = " + score + ",");
			if (role > 0)
				sql.append("role = " + role + ", ");
			if (userLabel != null && !userLabel.equals(""))
				sql.append("userLabel = '" + userLabel + "', ");
			if (usbKey >= 0)
				sql.append("usbKey = " + usbKey + " ");
			sql.append("where id = " + id);
			return db.executeUpdate(sql.toString());
		} catch (SQLException e) {
			l.error("用户信息修改 SQL Exception ", e);
			return 500;
		} finally {
			Db.close(db);
		}
	}

	/**
	 * 更改密码
	 * 
	 * @param id
	 *            - 用户ID
	 * @param oldpwd
	 *            - 旧密码
	 * @param pwd
	 *            - 新密码
	 * @return 0 失败 1 成功 101 旧密码不正确
	 */
	public int setPassword(int id, String oldpwd, String pwd) {
		Db db = null;
		int result = 0;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(id) from user where id = " + id
					+ " and pwd = '" + oldpwd + "'");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					if (rs.getInt(1) == 1) {
						sql.delete(0, sql.length());
						sql.append("update user set ")
								.append("pwd = '" + pwd + "' ")
								.append("where ").append("id = " + id);
						int res = db.executeUpdate(sql.toString());
						result = res;
					} else
						result = 101;
				}
			} else
				result = 500;

		} catch (SQLException e) {
			l.error("更改用户密码 SQL Exception ", e);
			result = 500;
		} finally {
			Db.close(db);
		}
		return result;
	}

	/**
	 * 重置密码
	 * 
	 * @param id
	 *            - 用户ID
	 * @param passwd
	 *            - 密码
	 * @return
	 */
	public boolean resetPasswd(int id, String passwd) {
		Db db = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("update user set ").append("pwd = '" + passwd + "' ")
					.append("where ").append("id = " + id);
			int res = db.executeUpdate(sql.toString());
			if (res == 1)
				return true;
		} catch (SQLException e) {
			l.error("重置密码 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return false;
	}

	/**
	 * 更新登录失败个数
	 * 
	 * @param id
	 *            - 用户ID
	 * @return
	 */
	public boolean setLoginFailed(int id) {
		Db db = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("update user set ")
					.append("loginFailed = loginFailed + 1 ").append("where ")
					.append("id = " + id);
			int res = db.executeUpdate(sql.toString());
			if (res == 1)
				return true;

		} catch (SQLException e) {
			l.error("更新登录失败个数 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return false;
	}

	/**
	 * 更新用户登录IP
	 * 
	 * @param id
	 *            - 用户ID
	 * @param ip
	 *            - 用户登录IP
	 * @return
	 */
	public boolean setLoginPlace(int id, String ip) {
		Db db = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("update user set ").append("loginPlace = '" + ip + "' ")
					.append("where ").append("id = " + id);
			int res = db.executeUpdate(sql.toString());
			if (res == 1)
				return true;

		} catch (SQLException e) {
			l.error("更新用户登录IP SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return false;
	}

	/**
	 * 用户激活
	 * 
	 * @param id
	 *            - 用户ID
	 * @param state
	 *            - 激活状态
	 * @return
	 */
	public boolean setState(int id, int state) {
		Db db = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("update user set ").append("state = " + state + " ")
					.append("where ").append("id = " + id);
			int res = db.executeUpdate(sql.toString());
			if (res == 1)
				return true;

		} catch (SQLException e) {
			l.error("更新用户登录IP SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return false;
	}

	/**
	 * 更改用户登录方式
	 * 
	 * @param id
	 *            -- 用户ID
	 * @param usbKey
	 *            -- usbKey值
	 * @return
	 */
	public boolean setUsbKey(int id, int usbKey) {
		Db db = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("update user set ").append("usbKey = " + usbKey + " ")
					.append("where ").append("id = " + id);
			int res = db.executeUpdate(sql.toString());
			if (res == 1)
				return true;

		} catch (SQLException e) {
			l.error("更新用户登录IP SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return false;
	}

	/**
	 * 
	 * @param userGroup
	 * @return
	 */
	public List<UserDao> list(int userGroup) {
		Db db = null;
		List<UserDao> userDaoList = null;
		if (userGroup <= 0)
			return null;
		try {
			db = new Db();
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id,userName,userGroup,email,mdn,deviceid,deviceType,pwd,userType,regTime,lastLoginTime,lastLoginPlace,userKey,state,score,userLabel,usbKey FROM user where userGroup = "
					+ userGroup);
			ResultSet rs = db.executeQuery(sb.toString());
			UserDao userDao = null;
			userDaoList = new ArrayList<UserDao>(db.getNumRows());
			while (rs.next()) {
				userDao = new UserDao();
				userDao.setId(rs.getLong(1));
				userDao.setUserName(rs.getString(2));
				userDao.setUserGroup(rs.getShort(3));
				userDao.setEmail(rs.getString(4));
				userDao.setMdn(rs.getString(5));
				userDao.setDeviceId(rs.getString(6));
				userDao.setDeviceType(rs.getInt(7));
				userDao.setPwd(rs.getString(8));
				userDao.setUserType(rs.getShort(9));
				userDao.setRegTime(rs.getLong(10));
				userDao.setLastLoginTime(rs.getLong(11));
				userDao.setLastLoginPlace(rs.getString(12));
				userDao.setUserKey(rs.getString(13));
				userDao.setState(rs.getShort(14));
				userDao.setScore(rs.getInt(15));
				userDao.setUserLabel(rs.getString(16));
				userDao.setUsbKey(rs.getShort(17));
				userDaoList.add(userDao);
			}
		} catch (SQLException e) {
			userDaoList = null;
			l.error("获取USER 集合 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return userDaoList;
	}

	/**
	 * 
	 * @param role
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<UserDao> list(short role, int start, int limit) {
		Db db = null;
		List<UserDao> userDaoList = null;
		if (role <= 0)
			return null;
		try {
			db = new Db();
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id,userName,userGroup,email,mdn,deviceid,deviceType,pwd,userType,regTime,lastLoginTime,lastLoginPlace,userKey,state,score,role,userLabel,usbKey FROM user where role = "
					+ role + " limit " + (start - 1) * limit + ", " + limit);
			ResultSet rs = db.executeQuery(sb.toString());
			UserDao userDao = null;
			userDaoList = new ArrayList<UserDao>(db.getNumRows());
			while (rs.next()) {
				userDao = new UserDao();
				userDao.setId(rs.getLong(1));
				userDao.setUserName(rs.getString(2));
				userDao.setUserGroup(rs.getShort(3));
				userDao.setEmail(rs.getString(4));
				userDao.setMdn(rs.getString(5));
				userDao.setDeviceId(rs.getString(6));
				userDao.setDeviceType(rs.getInt(7));
				userDao.setPwd(rs.getString(8));
				userDao.setUserType(rs.getShort(9));
				userDao.setRegTime(rs.getLong(10));
				userDao.setLastLoginTime(rs.getLong(11));
				userDao.setLastLoginPlace(rs.getString(12));
				userDao.setUserKey(rs.getString(13));
				userDao.setState(rs.getShort(14));
				userDao.setScore(rs.getInt(15));
				userDao.setRole(rs.getInt(16));
				userDao.setUserLabel(rs.getString(17));
				userDao.setUsbKey(rs.getShort(18));
				userDaoList.add(userDao);
			}
		} catch (SQLException e) {
			userDaoList = null;
			l.error("获取USER 集合 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return userDaoList;
	}

	/**
	 * 
	 * @param role
	 * @return
	 */
	public int listCount(short role) {
		Db db = null;
		if (role <= 0)
			return -1;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from user where role = " + role);
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					return rs.getInt(1);
				}
			} else
				return -500;
		} catch (SQLException e) {
			l.error("获取USER 集合个数 SQL Exception ", e);
			return -500;
		} finally {
			Db.close(db);
		}
		return 0;
	}
}