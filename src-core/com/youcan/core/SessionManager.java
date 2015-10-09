package com.youcan.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.youcan.core.util.Text;

public class SessionManager extends TimerTask {
	private static SessionManager instance = null;
	private long expireTime = 0;
	private SessionClearListener listener;
	private static Object lock = new Object();
	private static final String ROOT_COOKIE_PATH = "/";

	/**
	 * 全局维护的一个SESSION MAP对象
	 */
	private HashMap<String, BaseSession> sessionMap;

	public SessionManager() {
		sessionMap = new HashMap<String, BaseSession>();
		setExpireTime(g.sessionExpiredTime);
		instance = this;
	}

	public static SessionManager getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					synchronized (lock) {
						instance = new SessionManager();
					}
				}
			}
		}
		return instance;
	}
	
	public static String randomSessionId() {
		return Text.MD5(UUID.randomUUID().toString());
	}

	public BaseSession getSession(String sessionId) {
		BaseSession session = sessionMap.get(sessionId);
		if (session != null) {
			session.access();
		}
		return session;
	}
	
	public BaseSession getSession(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies){
//				l.debug("cookie name[" + cookie.getName() +"]\ncookie value[" + cookie.getValue() + "]\ncookie path[" + cookie.getPath() + "]\ncookie domain[" + cookie.getDomain()+"]");
				if(g.sessionIdName.equals(cookie.getName())){
					return getSession(cookie.getValue());
				}
			}
		}
		return null;
	}
	
	public void sendCookie(HttpServletResponse response, BaseSession session){
		Cookie cookie = new Cookie(g.sessionIdName, session.getSessionId());
		cookie.setPath(ROOT_COOKIE_PATH);
		cookie.setMaxAge(g.sessionExpiredTime);
		response.addCookie(cookie);
	}
	
	public void removeSession(BaseSession session) {
		if (sessionMap.containsKey(session.getSessionId())) {
			sessionMap.remove(session.getSessionId());
		}
	}
	
	public void putSession(BaseSession session) {
		sessionMap.put(session.getSessionId(), session);
	}

	public synchronized void clearAllSession() {
		sessionMap.clear();
	}
	
	public synchronized void clearExpired() {
		if (sessionMap == null || sessionMap.isEmpty() || expireTime <=0) {
			return;
		}
		long current = System.currentTimeMillis();
		ArrayList<String> sessionToClear = new ArrayList<>();
		for (BaseSession session : sessionMap.values()) {
//			l.debug("当前session：" + session.getSessionId() + "__c time:" + (current - session.getMtime()) + "__expireTime:" + expireTime);
			if (current - session.getMtime() > expireTime) {
				sessionToClear.add(session.getSessionId());
			}
		}
		if(listener != null){
			listener.sessionCleared(sessionToClear.toArray(new String[sessionToClear.size()]));
		}
		for(String sessionId : sessionToClear){
			sessionMap.remove(sessionId);
		}
	}
	
	public Collection<BaseSession> getSessions() {
		return sessionMap.values();
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	public SessionClearListener getListener() {
		return listener;
	}

	public void setListener(SessionClearListener listener) {
		this.listener = listener;
	}

	@Override
	public void run() {
		l.debug("SessionManager clear ...");
		SessionManager.getInstance().clearExpired();
	}
}
