/**
 * 
 */
package com.ymt.mjq.web.weixin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ymt.mjq.dto.LocationInfo;
import com.ymt.mjq.service.LocationService;

/**
 * @author zhailiang
 * @since 2016年5月5日
 */
@RestController
@Profile("weixin")
public class LocationWeixinController {
	
	@Autowired
	private LocationService locationService;

	@RequestMapping(value = "/location", method = RequestMethod.GET)
	public List<LocationInfo> query(String type) {
		return locationService.findAll(type);
	}
	
}
