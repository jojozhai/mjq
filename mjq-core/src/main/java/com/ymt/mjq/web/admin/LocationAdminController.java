/**
 * 
 */
package com.ymt.mjq.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
@Profile("admin")
public class LocationAdminController {
	
	@Autowired
	private LocationService locationService;

	@RequestMapping(value = "/location", method = RequestMethod.POST)
	public LocationInfo create(@RequestBody LocationInfo locationInfo) {
		return locationService.create(locationInfo);
	}

	@RequestMapping(value = "/location", method = RequestMethod.GET)
	public Page<LocationInfo> query(LocationInfo locationInfo, Pageable pageable) {
		return locationService.query(locationInfo, pageable);
	}
	
	@RequestMapping(value = "/location/{id}", method = RequestMethod.GET)
	public LocationInfo getInfo(@PathVariable Long id) {
		return locationService.getInfo(id);
	}

	@RequestMapping(value = "/location/{id}", method = RequestMethod.PUT)
	public LocationInfo update(@RequestBody LocationInfo locationInfo) {
		return locationService.update(locationInfo);
	}

	@RequestMapping(value = "/location/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		locationService.delete(id);
	}
	
}
