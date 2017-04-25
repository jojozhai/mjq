/*
 * 项目名称：bwk-core
 * 类名称: LocationService
 * 创建时间: 2016年9月20日 上午9:07:39
 * 创建人: zhailiang@pz365.com
 *
 * 修改历史:
 * 
 * Copyright: 2016 www.pz365.com Inc. All rights reserved.
 * 
 */
package com.ymt.mjq.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ymt.mjq.dto.LocationInfo;

/**
 *
 *
 * @author zhailiang@pz365.com
 * @version 1.0.0
 */
public interface LocationService {
    
    Page<LocationInfo> query(LocationInfo locationInfo, Pageable pageable);
    
    LocationInfo create(LocationInfo locationInfo);

    LocationInfo getInfo(Long id);
    
    LocationInfo update(LocationInfo locationInfo);

    void delete(Long id);

	List<LocationInfo> findAll(String type);
    
}
