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
	
	@ManyToOne
	private User user;
	private String type;
	@Column(length=1000)
	private String content;
	private boolean anonymity;
	@ElementCollection
	private List<String> images;
	private String longitude;
	private String latitude;
	private String location;
	private String phone;
	private int bonus;
	@Temporal(TemporalType.TIMESTAMP)
	private Date bonusTime;
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
	
}
