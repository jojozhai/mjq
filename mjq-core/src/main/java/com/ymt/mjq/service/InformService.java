/*
 * 项目名称：bwk-core
 * 类名称: InformService
 * 创建时间: 2016年9月20日 上午9:07:39
 * 创建人: zhailiang@pz365.com
 *
 * 修改历史:
 * 
 * Copyright: 2016 www.pz365.com Inc. All rights reserved.
 * 
 */
package com.ymt.mjq.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ymt.mjq.dto.InformInfo;

/**
 *
 *
 * @author zhailiang@pz365.com
 * @version 1.0.0
 */
public interface InformService {
    
    Page<InformInfo> query(InformInfo informInfo, Pageable pageable);
    
    InformInfo create(InformInfo informInfo);

    InformInfo getInfo(Long id);
    
    void accept(Long id) throws Exception;
    
    InformInfo update(InformInfo informInfo);

    void delete(Long id);

	void bonus(Long id) throws Exception;

	void deny(Long id) throws Exception;
    
}
