/**
 * 
 */
package com.ymt.mjq.dto;

import java.util.Date;
import java.util.List;

import com.ymt.mjq.domain.InformStatus;

/**
 * @author zhailiang
 *
 */
public class InformInfo {

	private Long id;
	private Long userId;
	private String type;
	private String content;
	private Boolean anonymity;
	private List<String> images;
	private String longitude;
	private String latitude;
	private String location;
	private String phone;
	private boolean bonus;
	private Date bonusTime;
	private InformStatus status;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public Boolean getAnonymity() {
		return anonymity;
	}
	/**
	 * @param anonymity the anonymity to set
	 */
	public void setAnonymity(Boolean anonymity) {
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
	public boolean isBonus() {
		return bonus;
	}
	/**
	 * @param bonus the bonus to set
	 */
	public void setBonus(boolean bonus) {
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
}
