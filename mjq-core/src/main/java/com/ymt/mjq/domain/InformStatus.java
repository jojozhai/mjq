/**
 * 
 */
package com.ymt.mjq.domain;

/**
 * @author zhailiang
 *
 */
public enum InformStatus {
	
	/**
	 * 未受理
	 */
	WAITING,
	/**
	 * 已受理
	 */
	WORKING,
	/**
	 * 已处理
	 */
	WORKED,
	/**
	 * 已确认处理
	 */
	FINISH,
	/**
	 * 拒绝受理
	 */
	DENY
}
