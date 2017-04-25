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

import com.ymt.mjq.dto.ArticleInfo;
import com.ymt.mjq.service.ArticleService;

/**
 * @author zhailiang
 * @since 2016年5月5日
 */
@RestController
@Profile("admin")
public class ArticleAdminController {
	
	@Autowired
	private ArticleService articleService;

	@RequestMapping(value = "/article", method = RequestMethod.POST)
	public ArticleInfo create(@RequestBody ArticleInfo articleInfo) {
		return articleService.create(articleInfo);
	}

	@RequestMapping(value = "/article", method = RequestMethod.GET)
	public Page<ArticleInfo> query(ArticleInfo articleInfo, Pageable pageable) {
		return articleService.query(articleInfo, pageable);
	}
	
	@RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
	public ArticleInfo getInfo(@PathVariable Long id) {
		return articleService.getInfo(id);
	}

	@RequestMapping(value = "/article/{id}", method = RequestMethod.PUT)
	public ArticleInfo update(@RequestBody ArticleInfo articleInfo) {
		return articleService.update(articleInfo);
	}

	@RequestMapping(value = "/article/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		articleService.delete(id);
	}
	
}
