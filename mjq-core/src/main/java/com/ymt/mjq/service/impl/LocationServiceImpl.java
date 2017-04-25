/**
 * 
 */
package com.ymt.mjq.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ymt.mjq.domain.Location;
import com.ymt.mjq.dto.LocationInfo;
import com.ymt.mjq.repository.LocationRepository;
import com.ymt.mjq.repository.spec.LocationSpec;
import com.ymt.mjq.service.LocationService;
import com.ymt.pz365.data.jpa.support.QueryResultConverter;

/**
 * @author zhailiang
 *
 */
@Service("locationService")
@Transactional
public class LocationServiceImpl implements LocationService {

	@Autowired
    private LocationRepository locationRepository;
    
    @Override
    public Page<LocationInfo> query(LocationInfo locationInfo, Pageable pageable) {
        Page<Location> pageData = locationRepository.findAll(new LocationSpec(locationInfo), pageable);
        return QueryResultConverter.convert(pageData, LocationInfo.class, pageable);
    }

    @Override
    public LocationInfo create(LocationInfo locationInfo) {
        Location location = new Location();
        BeanUtils.copyProperties(locationInfo, location);
        locationInfo.setId(locationRepository.save(location).getId());
        return locationInfo;
    }

    @Override
    public LocationInfo getInfo(Long id) {
        Location location = locationRepository.findOne(id);
        LocationInfo info = new LocationInfo();
        BeanUtils.copyProperties(location, info);
        return info;
    }

    @Override
    public LocationInfo update(LocationInfo locationInfo) {
        Location location = locationRepository.findOne(locationInfo.getId());
        BeanUtils.copyProperties(locationInfo, location);
        locationRepository.save(location);
        return locationInfo;
    }

    @Override
    public void delete(Long id) {
        locationRepository.delete(id);       
    }

	@Override
	public List<LocationInfo> findAll(String type) {
		List<Location> locations = locationRepository.findByType(type);
		return QueryResultConverter.convert(locations, LocationInfo.class);
	}
}
