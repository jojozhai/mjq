/*
 * 项目名称：bwk-core
 * 类名称: FeedbackService
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

import com.ymt.mjq.dto.FeedbackInfo;

/**
 *
 *
 * @author zhailiang@pz365.com
 * @version 1.0.0
 */
public interface FeedbackService {
    
    Page<FeedbackInfo> query(FeedbackInfo feedbackInfo, Pageable pageable);
    
    FeedbackInfo create(FeedbackInfo feedbackInfo);

    FeedbackInfo getInfo(Long id);
    
    FeedbackInfo update(FeedbackInfo feedbackInfo);

    void delete(Long id);

}
