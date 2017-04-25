/**
 * 
 */
package com.ymt.mjq.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;

import com.ymt.pz365.data.jpa.domain.DomainImpl;

/**
 * @author zhailiang
 *
 */
@Entity
public class Article extends DomainImpl {
	
	private String type;
	private String title;
	@Lob
	private String content;
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	

}
