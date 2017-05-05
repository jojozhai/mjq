/**
 * 
 */
package com.ymt.mjq.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ymt.mirage.user.domain.User;
import com.ymt.pz365.data.jpa.domain.DomainImpl;

/**
 * @author zhailiang
 *
 */
@Entity
public class Inform extends DomainImpl {
	
	/**
	 * 爆料人
	 */
	@ManyToOne
	private User user;
	/**
	 * 爆料类型
	 */
	private String type;
	/**
	 * 爆料内容
	 */
	@Column(length=1000)
	private String content;
	/**
	 * 是否匿名
	 */
	private boolean anonymity;
	/**
	 * 图片链接
	 */
	@ElementCollection
	private List<String> images;
	/**
	 * 整改图片
	 */
	@ElementCollection
	private List<String> images2;
	/**
	 * 经度
	 */
	private String longitude;
	/**
	 * 纬度
	 */
	private String latitude;
	/**
	 * 位置描述
	 */
	private String location;
	/**
	 * 联系电话
	 */
	private String phone;
	/**
	 * 奖励积分
	 */
	private int bonus;
	/**
	 * 奖励时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date bonusTime;
	/**
	 * 处理状态
	 */
	@Enumerated(EnumType.STRING)
	private InformStatus status;
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
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
	/**
	 * @return the anonymity
	 */
	public boolean isAnonymity() {
		return anonymity;
	}
	/**
	 * @param anonymity the anonymity to set
	 */
	public void setAnonymity(boolean anonymity) {
		this.anonymity = anonymity;
	}
	/**
	 * @return the images
	 */
	public List<String> getImages() {
		return images;
	}
	/**
	 * @param images the images to set
	 */
	public void setImages(List<String> images) {
		this.images = images;
	}
	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the bonus
	 */
	public int getBonus() {
		return bonus;
	}
	/**
	 * @param bonus the bonus to set
	 */
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	/**
	 * @return the bonusTime
	 */
	public Date getBonusTime() {
		return bonusTime;
	}
	/**
	 * @param bonusTime the bonusTime to set
	 */
	public void setBonusTime(Date bonusTime) {
		this.bonusTime = bonusTime;
	}
	/**
	 * @return the status
	 */
	public InformStatus getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(InformStatus status) {
		this.status = status;
	}
	/**
	 * @return the images2
	 */
	public List<String> getImages2() {
		return images2;
	}
	/**
	 * @param images2 the images2 to set
	 */
	public void setImages2(List<String> images2) {
		this.images2 = images2;
	}
	
}
