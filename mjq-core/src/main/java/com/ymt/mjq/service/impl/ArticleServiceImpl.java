/**
 * 
 */
package com.ymt.mjq.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ymt.mjq.domain.Article;
import com.ymt.mjq.dto.ArticleInfo;
import com.ymt.mjq.repository.ArticleRepository;
import com.ymt.mjq.repository.spec.ArticleSpec;
import com.ymt.mjq.service.ArticleService;
import com.ymt.pz365.data.jpa.support.QueryResultConverter;

/**
 * @author zhailiang
 *
 */
@Service("articleService")
@Transactional
public class ArticleServiceImpl implements ArticleService {

	@Autowired
    private ArticleRepository articleRepository;
    
    @Override
    public Page<ArticleInfo> query(ArticleInfo articleInfo, Pageable pageable) {
        Page<Article> pageData = articleRepository.findAll(new ArticleSpec(articleInfo), pageable);
        return QueryResultConverter.convert(pageData, ArticleInfo.class, pageable);
    }

    @Override
    public ArticleInfo create(ArticleInfo articleInfo) {
        Article article = new Article();
        BeanUtils.copyProperties(articleInfo, article);
        articleInfo.setId(articleRepository.save(article).getId());
        return articleInfo;
    }

    @Override
    public ArticleInfo getInfo(Long id) {
        Article article = articleRepository.findOne(id);
        ArticleInfo info = new ArticleInfo();
        BeanUtils.copyProperties(article, info);
        return info;
    }

    @Override
    public ArticleInfo update(ArticleInfo articleInfo) {
        Article article = articleRepository.findOne(articleInfo.getId());
        BeanUtils.copyProperties(articleInfo, article);
        articleRepository.save(article);
        return articleInfo;
    }

    @Override
    public void delete(Long id) {
        articleRepository.delete(id);       
    }

}
