package com.youcan.core.util;

public class StateUtil {
	/**
	 * 判断是否具备某些状态元素
	 * @param state 状态
	 * @param stateFilter 状态过滤器
	 * @return true表示有这些状态，false表示不具备这些状态
	 */
	public static boolean hasState(int state, int stateFilter) {
		return (state & stateFilter) == stateFilter;
	}
	
	/**
	 * 添加一个或多个状态元素
	 * @param state
	 * @param stateFilter
	 * @return 更新后的状态
	 */
	public static int addState(int state, int stateFilter) {
		return state | stateFilter;
	}
	
	/**
	 * 移除一个或多个状态元素
	 * @param state
	 * @param stateFilter
	 * @return 更新后的状态
	 */
	public static int removeState(int state, int stateFilter) {
		return state & ~stateFilter;
	}
}
