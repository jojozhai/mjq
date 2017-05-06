/*
 * 项目名称：bwk-core
 * 类名称: AccidentService
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

import com.ymt.mjq.dto.AccidentInfo;

/**
 *
 *
 * @author zhailiang@pz365.com
 * @version 1.0.0
 */
public interface AccidentService {
    
    Page<AccidentInfo> query(AccidentInfo accidentInfo, Pageable pageable);
    
    AccidentInfo create(AccidentInfo accidentInfo);

    AccidentInfo getInfo(Long id);
    
    AccidentInfo update(AccidentInfo accidentInfo);

    void delete(Long id);

}
