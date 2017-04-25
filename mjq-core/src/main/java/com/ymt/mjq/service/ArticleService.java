/*
 * 项目名称：bwk-core
 * 类名称: ArticleService
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

import com.ymt.mjq.dto.ArticleInfo;

/**
 *
 *
 * @author zhailiang@pz365.com
 * @version 1.0.0
 */
public interface ArticleService {
    
    Page<ArticleInfo> query(ArticleInfo articleInfo, Pageable pageable);
    
    ArticleInfo create(ArticleInfo articleInfo);

    ArticleInfo getInfo(Long id);
    
    ArticleInfo update(ArticleInfo articleInfo);

    void delete(Long id);

}
